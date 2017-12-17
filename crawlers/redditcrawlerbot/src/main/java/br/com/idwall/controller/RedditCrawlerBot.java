package br.com.idwall.controller;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.service.RedditCrawlerBotService;

public class RedditCrawlerBot extends TelegramLongPollingBot {

	private static final String BOT_USERNAME = "RedditCrawler_bot";
	private static final String BOT_TOKEN = "455746339:AAEfy3Xrqp1RiM3Xu9De3l6vGNvi9toZR70";
	private static final String BOT_REDDITCRAWLER_COMMAND = "/nadaprafazer";
	
	public String getBotUsername() {
		return BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}

	public void onUpdateReceived(Update update) {
		
		System.out.println("Message received, processing...");
		
		if (update.hasMessage() && update.getMessage().hasText() && isValidMessage(update.getMessage().getText())) {
			
			try {
			
				String message_text = getSubredditsFromMessage(update.getMessage().getText());
				long chat_id = update.getMessage().getChatId();

				SendMessage message = new SendMessage() 
						.setChatId(chat_id)
						.setText(message_text);
	        
	            execute(message);
	            
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        
	    }
	    
	}	

	private String getSubredditsFromMessage(String text) throws Exception {
		
		String redditList = "";
		String[] commands = text.split(" ");
		
		if (commands.length < 2) {
			throw new Exception("Commands doesn't have the subreddits, can't continue!");
		}		
		
		RedditCrawlerBotService service = new RedditCrawlerBotService();
		redditList = service.fetch(commands[1]);
		
		return redditList;
		
	}

	private boolean isValidMessage(String text) {
		
		String[] commands = text.split(" ");
		
		if (commands.length < 1) {
			return false;
		}
		
		if (!BOT_REDDITCRAWLER_COMMAND.equalsIgnoreCase(commands[0])) {
			return false;
		}
		
		return true;
		
	}	
	
}