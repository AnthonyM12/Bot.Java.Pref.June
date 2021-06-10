package ru.vkbot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;


public class Bot {
    public static void main(String[] args) throws ClientException, ApiException, InterruptedException
    {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Random random = new Random();
        Keyboard keyboard = new Keyboard();
          List<List<KeyboardButton>> allKey = new ArrayList<>();
          List<KeyboardButton> line1 = new ArrayList<>();
          line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Начать").setType(KeyboardButtonActionType.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
          line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Неделя").setType(KeyboardButtonActionType.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
          allKey.add(line1);
        keyboard.setButtons(allKey);

        GroupActor actor = new GroupActor(203426840, "00d2acc9e6c722caf98538144fa34f8f2ef1ce9629e9091c132e7d35d370eef0311ab386d7246c3e4f02f");
        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        while (true){
            MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()){
                messages.forEach(message -> {
                    System.out.println(message.toString());
                    try {
                        if (message.getText().matches("Начать")){
                            vk.messages().send(actor).message("Напиши свою группу").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else if (message.getText().equals("Кнопки")) {
                            vk.messages().send(actor).userId(message.getFromId()).randomId(random.nextInt(10000)).keyboard(keyboard).execute();
                        }
                        else if (message.getText().equals("Неделя")){
                            vk.messages().send(actor).message("Идет "+TimeTest.SemesterWeek()+" учебная неделя").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else if (message.getText().toUpperCase().matches("^[А-Я]{3}[0-9]{1}[-]{1}[0-9]{2}$")) {
                            Data data = new Data();
                            vk.messages().send(actor).message(data.find(message.getText().toUpperCase())).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else {
                            vk.messages().send(actor).message("Неверный формат группы").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                    }
                    catch (ApiException | ClientException | IOException e) {e.printStackTrace();}
                });
            }
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);
        }
    }
}
