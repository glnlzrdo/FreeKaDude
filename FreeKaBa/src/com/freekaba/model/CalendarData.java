package com.freekaba.model;

public class CalendarData {
	private int id;
	private String title;
	private String start;
	private String end;
	
	public CalendarData(int id, String title, String start, String end) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
}