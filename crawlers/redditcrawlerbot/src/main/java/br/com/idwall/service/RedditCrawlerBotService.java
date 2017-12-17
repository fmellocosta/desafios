package br.com.idwall.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RedditCrawlerBotService {

	private static final String CRAWLER_URL = "http://localhost:8080/trending?subreddits=%s";
	
	public String fetch(String subreddits) throws Exception {
		
		HttpURLConnection con = getConnection(String.format(CRAWLER_URL, subreddits));
		con.setRequestMethod("GET");
		return parseInput(con.getInputStream());
		
	}	
	
	public HttpURLConnection getConnection(String url) throws Exception {
		
		try {
			HttpURLConnection connection = null;
			URL obj = new URL(url);
			connection = (HttpURLConnection) obj.openConnection();
			return connection;
		} catch (IOException e) {
			throw new Exception("Couldn't connect to crawler url, is it running properly? [URL: " + url + "]");
		}
		
	}

	public String parseInput(InputStream input) throws Exception {
		
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(input));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (Exception e) {
			throw new Exception("Couldn't read the return from crawler, try again later!");
		}
		
	}
	
}
