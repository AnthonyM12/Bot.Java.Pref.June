package ru.vkbot;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
    public int test = 0;
    public String gotovo = " ";
    public int indexRowGroup;
    public int indexCellGroup;
    public StringBuilder otvet = new StringBuilder();
    public String[] ivt = {"ИВТ"};


    public String find (String group) throws IOException {
        File myFolder = new File("C:\\расписание");
        File[] files = myFolder.listFiles();
        if (useList(ivt,group)) {
            assert files != null;
            for (File file : files) {
                if (file.toString().contains("ИВТ") && SearchGroup(group, file.toString()) == 1) {
                    gotovo = printExcel(file.toString());
                    break;
                }
            }
        }

        /*
        assert files != null;
        for (File file : files) {
            if (SearchGroup(group, file.toString())==1){
             gotovo = printExcel(file.toString());
             break;
            }
        }
       */
        if (gotovo.equals(" ")){
            gotovo="не найдена";
        }
        return gotovo;
    }

    public static boolean useList(String[] arr, String group) {
        return Arrays.asList(arr).contains(group.substring(0,3));
    }
    public int SearchGroup(String group, String puti) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(puti);
        Workbook wb = new XSSFWorkbook(fileInputStream);
        for (Row row : wb.getSheetAt(0)) {
            for (Cell cell : row) {
                if (getCellText(cell).equals(group)){
                    indexRowGroup = cell.getRowIndex();
                    indexCellGroup = cell.getColumnIndex();
                    test = 1;
                    break;
                }
            }
        }
        return test;
    }

    public String printExcel(String puti) throws IOException{
        FileInputStream fis = new FileInputStream(puti);
        Workbook wb1 = new XSSFWorkbook(fis);
        int addNewPredmet=0;
        int indexRowPredmet = indexRowGroup + 2;
        int indexRowPredmetChet = indexRowGroup + 3;
        if(TimeTest.StudyWeek()!=-1) {
            wtf1(wb1, addNewPredmet, indexRowPredmet, indexRowPredmetChet);
        }
        else {
            otvet.append("Каникулы!");
        }
        return otvet.toString();
    }

    private void wtf1(Workbook wb1, int addNewPredmet, int indexRowPredmet, int indexRowPredmetChet) {
        for (int i = 0; i < 6; i++) {
            TimeTest.PrintDay(i, otvet);
            for (int i1 = 0; i1 < 6; i1++) {
                if (TimeTest.StudyWeek()==1) {
                    addNewPredmet = getAddNewPredmet1(wb1, addNewPredmet, indexRowPredmet, i1, indexRowPredmet + addNewPredmet);
                }
                if (TimeTest.StudyWeek()==2) {
                    addNewPredmet = getAddNewPredmet1(wb1, addNewPredmet, indexRowPredmetChet, i1, indexRowPredmet + addNewPredmet);
                }
            }
        }
    }

    private int getAddNewPredmet1(Workbook wb1, int addNewPredmet, int indexRowPredmet, int i1, int i2) {
        String predmet = getCellText(wb1.getSheetAt(0).getRow(indexRowPredmet + addNewPredmet).getCell(indexCellGroup)) + "\n";
        if (!predmet.equals("\n")) {
            if (!Character.isDigit(predmet.charAt(0))) {
                String type = getCellText(wb1.getSheetAt(0).getRow(indexRowPredmet + addNewPredmet).getCell(indexCellGroup + 1)) + "\n";
                String teacher = getCellText(wb1.getSheetAt(0).getRow(indexRowPredmet + addNewPredmet).getCell(indexCellGroup + 2)) + "\n";
                String location = getCellText(wb1.getSheetAt(0).getRow(indexRowPredmet + addNewPredmet).getCell(indexCellGroup + 3)) + "\n";
                TimeTest.PrintTime(i1, otvet);
                otvet.append(predmet);
                otvet.append(type);
                otvet.append(teacher);
                otvet.append(location);
                otvet.append("\n");
            }
            else {
                Pattern p1 = Pattern.compile("[1-9]{1,2}");
                Matcher m1 = p1.matcher(predmet);
                while (m1.find()){
                    String thisWeek = m1.group();
                    int thisWeekInt = Integer.parseInt(thisWeek);
                    if (thisWeekInt==TimeTest.SemesterWeek()){
                        String type = getCellText(wb1.getSheetAt(0).getRow(i2).getCell(indexCellGroup + 1)) + "\n";
                        String teacher = getCellText(wb1.getSheetAt(0).getRow(i2).getCell(indexCellGroup + 2)) + "\n";
                        String location = getCellText(wb1.getSheetAt(0).getRow(i2).getCell(indexCellGroup + 3)) + "\n";
                        TimeTest.PrintTime(i1, otvet);
                        otvet.append(predmet);
                        otvet.append(type);
                        otvet.append(teacher);
                        otvet.append(location);
                        otvet.append("\n");
                    }
                }
            }
        }
        addNewPredmet = addNewPredmet + 2;
        return addNewPredmet;
    }


    public String getCellText(Cell cell){
        String res="";
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                res = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    res = cell.getDateCellValue().toString() ;
                }
                else {
                    res = Double.toString(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                res = Boolean.toString(cell.getBooleanCellValue()) ;
                break;
            case Cell.CELL_TYPE_FORMULA:
                res = cell.getCellFormula();
                break;
            default:
                break;
        }
        return res;
    }
}
