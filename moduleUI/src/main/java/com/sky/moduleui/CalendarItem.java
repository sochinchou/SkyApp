package com.sky.moduleui;

public class CalendarItem{
	public long id;
	public String title;
	public String content;
	public String date;
	public String time;
	
	public CalendarItem(long id, String title, String content, String date, String time){
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.time = time;
	}
}