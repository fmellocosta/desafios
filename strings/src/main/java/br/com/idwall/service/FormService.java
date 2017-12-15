package br.com.idwall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.idwall.model.Form;

@Component
public class FormService {

	public void formatString(Form form) {
		
        List<String> result = new ArrayList<>();

        Pattern p = Pattern.compile("\\b.{1," + (form.getLength()-1) + "}\\b\\W?");
        Matcher m = p.matcher(form.getContent());
        
        while (m.find()) {
            result.add(m.group());
        }
        
        form.setFormattedContent(result);
		
	}
	
}
