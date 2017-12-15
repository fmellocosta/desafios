package br.com.idwall.model;

import java.util.List;

public class Form {

	private int length;
	private String content;
	private List<String> formattedContent;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getFormattedContent() {
		return formattedContent;
	}

	public void setFormattedContent(List<String> formattedContent) {
		this.formattedContent = formattedContent;
	}
	
}
