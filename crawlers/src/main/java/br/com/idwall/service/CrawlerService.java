package br.com.idwall.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.idwall.model.Form;
import br.com.idwall.model.RedditThread;

@Component
public class CrawlerService {

	/**
	 * Values possible: all, day, hour...
	 */
	private static final String PERIOD = "day";
	private static final int VOTES_REQUIRED = 5000;
	private static final String REDDIT_URL = "https://www.reddit.com/";
	private static final String SUBREDDIT_URL = "https://www.reddit.com/r/%s/top/?sort=top&t=%s";

	public Map<String, List<RedditThread>> fetch(Form form) {
	
		String[] channels = form.getChannels().split(";");
		Map<String, List<RedditThread>> hotThreads = new HashMap<>();
		
		for (String channel : channels) {
			
			try {
				
				Document doc = Jsoup.connect(String.format(SUBREDDIT_URL, channel, PERIOD)).userAgent("Java Crawler (by /u/fmellocosta)").get();
				Elements threadsInSubreddit = doc.select("#siteTable > .linkflair");
				List<RedditThread> threadList = new ArrayList<>();
				
				for (Element thread : threadsInSubreddit) {
					
					int votes = Integer.parseInt(thread.attr("data-score"));
					
					if (votes < VOTES_REQUIRED) {
						continue;
					}
					
					String title = thread.getElementsByAttributeValue("data-event-action", "title").text();
					String link = REDDIT_URL + thread.getElementsByAttributeValue("data-event-action", "title").attr("href");
					String commentsLink = thread.getElementsByAttributeValue("data-event-action", "comment").attr("href");
					
					RedditThread currentThread = new RedditThread();
					currentThread.setSubreddit(channel);
					currentThread.setVotes(votes);
					currentThread.setTitle(title);
					currentThread.setLink(link);
					currentThread.setCommentsLink(commentsLink);
					
					threadList.add(currentThread);

				}
				
				if (!threadList.isEmpty()) {
					hotThreads.put(channel, threadList);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return hotThreads;
		
	}

	public String convertToJson(Map<?, ?> map) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(map);		
	}
	
}
