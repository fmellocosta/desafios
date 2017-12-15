package br.com.idwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.idwall.model.Form;
import br.com.idwall.service.FormService;

@Controller
public class HomeController {

	@Autowired
	public FormService service;
	
	@GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("greeting", new Form());
        return "form";
    }
	
	@PostMapping("/form")
    public String showResult(@ModelAttribute Form form) {
		service.formatString(form);
        return "result";
    }
	
}