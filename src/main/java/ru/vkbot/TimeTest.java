package ru.vkbot;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTest {
    public static int work;
    public static int semWeek;

    public static int StudyWeek(){
        Calendar myCalendar = new GregorianCalendar();
        if (myCalendar.get(Calendar.WEEK_OF_YEAR) > 35 & myCalendar.get(Calendar.WEEK_OF_YEAR) <= 52 || (myCalendar.get(Calendar.WEEK_OF_YEAR) >= 6 & myCalendar.get(Calendar.WEEK_OF_YEAR) < 23)){
            if (myCalendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0){
                work = 1;
            }
            else {
                work = 2;
            }
        }
        else {
            work = -1;
        }
        return work;
    }

    public static int SemesterWeek(){
        Calendar myCalendar = new GregorianCalendar();
        if (myCalendar.get(Calendar.WEEK_OF_YEAR)>=6&myCalendar.get(Calendar.WEEK_OF_YEAR)<23) {
             semWeek = myCalendar.get(Calendar.WEEK_OF_YEAR) - 5;
        }
        else if (myCalendar.get(Calendar.WEEK_OF_YEAR) > 35 & myCalendar.get(Calendar.WEEK_OF_YEAR) <= 52){
             semWeek = myCalendar.get(Calendar.WEEK_OF_YEAR) - 35;
        }
        return semWeek;
    }

    public static StringBuilder PrintDay(int k, StringBuilder otvet){
        switch (k){
            case 0:
                otvet.append("\uD83D\uDCC5 ПОНЕДЕЛЬНИК \n-------------------------------------------------------------\n");
                break;
            case 1:
                otvet.append("\uD83D\uDCC5 ВТОРНИК \n-------------------------------------------------------------\n");
                break;
            case 2:
                otvet.append("\uD83D\uDCC5 СРЕДА \n-------------------------------------------------------------\n");
                break;
            case 3:
                otvet.append("\uD83D\uDCC5 ЧЕТВЕРГ \n-------------------------------------------------------------\n");
                break;
            case 4:
                otvet.append("\uD83D\uDCC5 ПЯТНИЦА \n-------------------------------------------------------------\n");
                break;
        }
        return otvet;
    }

    public static StringBuilder PrintTime(int i, StringBuilder otvet){
        switch (i){
            case 0:
                otvet.append("⏰ 8:30-10:00 ⏰\n");
                break;
            case 1:
                otvet.append("⏰ 10:15-11:45 ⏰\n");
                break;
            case 2:
                otvet.append("⏰ 12:00-13:30 ⏰\n");
                break;
            case 3:
                otvet.append("⏰ 14:00-15:30 ⏰\n");
                break;
        }
        return otvet;
    }
}
