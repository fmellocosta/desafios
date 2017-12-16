package br.com.idwall.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import br.com.idwall.model.Form;

@Component
public class FormService {

	private static final String OUTPUT_FILENAME = "download.txt";

	public void applyFormatting(Form form) throws Exception {
		
		if (!validation(form))
			return;
		
		if (form.isJustify()) {
			createFile(formatAndJustify(form), OUTPUT_FILENAME);
		} else {
			createFile(format(form), OUTPUT_FILENAME);
		}
		
	}
	
	private boolean validation(Form form) throws Exception {
		
		if (form.getLength() < 0) {
			throw new Exception("Length must be specified!");
		}
		
		if (form.getContent().length() < 0) {
			throw new Exception("Content must be specified!");
		}
		
		return true;
		
	}

	public List<String> format(Form form) throws Exception {
	
		List<String> lines = new ArrayList<>();
		
		Pattern p = Pattern.compile(".{1," + form.getLength() + "}(?:\\s|$)", Pattern.DOTALL);
		Matcher m = p.matcher(form.getContent());

		while (m.find()) {
			lines.add(m.group());
		}		
		
		return lines;			

	}
	
	public List<String> formatAndJustify(Form form) throws Exception {
		
        List<String> lines = new ArrayList<String>();
        String[] words = form.getContent().split(" ");
        int limit = form.getLength();
		int index = 0;
		
		while (index < words.length) {
			
			int count = words[index].length();
			int last = index + 1;
			
			while (last < words.length) {
				
				if (words[last].length() + count + 1 > limit)
					break;
				
				count += 1 + words[last].length();
				last++;
				
			}
			
			StringBuilder builder = new StringBuilder();
			builder.append(words[index]);
			int diff = last - index - 1;
			
			if (last == words.length || diff == 0) {
				
				for (int i = index + 1; i < last; i++) {
					builder.append(" ");
					builder.append(words[i]);
				}
				
				for (int i = builder.length(); i < limit; i++) {
					builder.append(" ");
				}
				
			} else {
				
				int spaces = (limit - count) / diff;
				int r = (limit - count) % diff;
				
				for (int i = index + 1; i < last; i++) {
					
					for (int k = spaces; k > 0; k--) {
						builder.append(" ");
					}
					
					if (r > 0) {
						builder.append(" ");
						r--;
					}
					
					builder.append(" ");
					builder.append(words[i]);
					
				}
				
			}
			
			lines.add(builder.toString());
			index = last;
			
		}
        
        return lines;
        
    }

	private void createFile(List<String> lines, String filename) throws FileNotFoundException, IOException, Exception {
		
		if (lines.isEmpty()) {
			throw new Exception("The content is empty, there is no need to create a file!");
		}
		
		File fout = new File(filename);
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (String s : lines) {
			bw.write(s);
			bw.newLine();				
		}
	 
		bw.close();
		
	}	
	
	public void download(HttpServletResponse response) throws Exception {
		
	    File initialFile = new File(OUTPUT_FILENAME);
		InputStream myStream = new FileInputStream(initialFile);

		response.addHeader("Content-disposition", "attachment;filename=" + OUTPUT_FILENAME);
		response.setContentType("txt/plain");

		IOUtils.copy(myStream, response.getOutputStream());
		
		response.flushBuffer();
		
	}
	
}