package br.com.idwall.model;

public class Form {

	private int length = 40;
	private String content;
	private boolean justify;

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

	public boolean isJustify() {
		return justify;
	}

	public void setJustify(boolean justify) {
		this.justify = justify;
	}

}
