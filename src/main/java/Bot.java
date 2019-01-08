import com.google.gson.Gson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//https://graph.facebook.com/me

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "gluten_bot";
    }

    @Override
    public String getBotToken() {
        return "712052677:AAE5kn6gZWRbbUDMSTTXSFd1Fw1djBdAKso";
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    static class Item {
        String title;
        String link;
        String description;
        String img;
    }

    static class Page {
        String title;
        String link;
        String description;
        String language;
        List<Item> items;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String json = null;
        try {
            json = readUrl("https://raw.githubusercontent.com/VladislavMilev/json_telegram_bot/master/telegram.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Page page = gson.fromJson(json, Page.class);

        if (update.hasMessage() && update.getMessage().hasText()) {

            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            long user_id = update.getMessage().getChat().getId();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String answer = message_text;

            SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(answer);
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            if (message.getText().equals("/start")) {
                for (Item item : page.items) {
                    sendMsg(message, item.img, item.link);
                }

            } else {
                sendMsg(message, "What?", "WHAT?");
            }
        }
    }

    private void sendMsg(Message message, String text, String link) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
