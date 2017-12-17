package br.com.idwall;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.controller.RedditCrawlerBot;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Initializing bot...");
		
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		
		try {
			botsApi.registerBot(new RedditCrawlerBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

}
