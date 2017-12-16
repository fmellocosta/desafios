package br.com.idwall.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.idwall.model.Form;
import br.com.idwall.service.FormService;

@Controller
public class HomeController {

	@Autowired
	public FormService service;

	@GetMapping("/form")
	public String showForm(Model model) {
		model.addAttribute("form", new Form());
		return "form";
	}

	@ResponseBody
	@PostMapping("/form")
	public void showResult(@ModelAttribute Form form, HttpServletResponse response) {

		try {
			service.applyFormatting(form);
			service.download(response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}