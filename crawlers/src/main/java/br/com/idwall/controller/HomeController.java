package br.com.idwall.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.idwall.model.Form;
import br.com.idwall.model.RedditThread;
import br.com.idwall.service.CrawlerService;

@Controller
public class HomeController {

	@Autowired
	public CrawlerService service;	
	
	@GetMapping("/")
	public String showForm(Model model) {
		model.addAttribute("form", new Form());
		return "form";
	}	

	@ResponseBody
	@PostMapping("/trending")
	public String trending(@ModelAttribute Form form) {
		Map<String, List<RedditThread>> redditThreads = service.fetch(form);
		return service.convertToJson(redditThreads);
	}
	
}
