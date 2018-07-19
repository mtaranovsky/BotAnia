import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e){
            e.printStackTrace();
        }

    }

    public void sendMsg(Message message,String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }



    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList= new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("хто твоя любов?"));
        keyboardFirstRow.add(new KeyboardButton("чи можу я з тобою зустрічатись?"));
        keyboardSecondRow.add(new KeyboardButton("randInst"));
        keyboardThirdRow.add(new KeyboardButton("Interesting about Ukraine"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
    public void onUpdateReceived(Update update) {
        Double numberOfPhoto=Math.random()*18;
        int i = numberOfPhoto.intValue();
        Message message = update.getMessage();
        if(message != null && message.hasText()){
            switch (message.getText()){
                case "/start":
                    sendMsg(message,"Привіт, я Анна");
                    break;
                case "хто твоя любов?":
                    sendMsg(message,"Микола Тарановський");
                    break;
                case "чи можу я з тобою зустрічатись?":
                    sendMsg(message,"ні, бо я Анна Тарановська");
                    break;

                case "randInst":
                    sendMsg(message, Parse.getApi().getJSONArray("data").getJSONObject(i).getString("link"));
                    break;
                case "Interesting about Ukraine":
                    sendMsg(message, Parse.ukraine());
                    break;
                default:
            }
        }
    }

    public String getBotUsername() {
        return "Ania";
    }

    public String getBotToken() {
        return "681656615:AAHlPpdP_Rb6kc1jkv-xs6j2iTYDRemlZZQ";
    }
}
