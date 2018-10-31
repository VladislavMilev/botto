import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Bot extends TelegramLongPollingBot {

                @Override
                public String getBotUsername() {
                        return "jimroot_bot:";
                }

                @Override
                public String getBotToken() {
                        return "740174724:AAG_93TnzZPKG79qc010Lvyko5FDC3khQBY";
                }

                @Override
                public void onUpdateReceived(Update update) {

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

                        String km = "/ktomudak";

                        String kmM[] = {"Ты", "Simple", "Твой друг", "Твой начальник", "Floki", "Создатель TM"};

                        int idxkM = new Random().nextInt(kmM.length);
                        String kmMM = (kmM[idxkM]);

                        String rsk = "/randSuperKoder";

                        String supercoder[] = {"Reiko", "Panda", "Starboy", "Inright", "Точно не Simple", "Vortex", "MJHead"};

                        int idx = new Random().nextInt(supercoder.length);
                        String random = (supercoder[idx]);

                        String lt = "/theBestTester";
                        String cbz = "/choBudetZavtra";

                        String cbzM[] = {"Тебя уволят", "Опоздаешь на работу", "Забудешь пропуск", "Скажут гадости", "Не будет писаться код", "Сэндер даст задание", "Дизбонус епта"};

                        int idxCBZ = new Random().nextInt(cbzM.length);
                        String randomCBZ = (cbzM[idxCBZ]);

                        String cpzp = "/kogdaPodnimutZP";

                        if (message != null && message.hasText()) {

                           if (message.getText().equals("/start")){
                                        sendMsg(message,
                                                "Кто мудак:" + " - " + km + "\n" +
                                                "Рандомный суперкодер:" + " - " + rsk + "\n" +
                                                "Кто лучший тостер:" + " - " + lt + "\n" +
                                                "Что будет завтра:" + " - " + cbz + "\n" +
                                                "Когда поднимут ЗП:" + " - " + cpzp + "\n"
                                        );}
                           else if (message.getText().equals(km)){
                              sendMsg(message, kmMM + EmojiParser.parseToUnicode("\n:smile:"));
                           }
                           else if (message.getText().equals(rsk)){
                              sendMsg(message, random + EmojiParser.parseToUnicode("\n:smile:"));
                           }
                           else if (message.getText().equals(lt)){
                              sendMsg(message, "Floki is a SUPERTOSTER." + EmojiParser.parseToUnicode("\n:smile:"));
                           }
                           else if (message.getText().equals(cbz)){
                              sendMsg(message, randomCBZ + EmojiParser.parseToUnicode("\n:smile:"));
                           }
                           else if (message.getText().equals(cpzp)){
                              sendMsg(message, "Тебе - никогда!" + EmojiParser.parseToUnicode("\n:smile:"));
                           }
                           else{
                              sendMsg(message, "What?");
                           }
                        }
                }

                private void sendMsg(Message message, String text) {
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
