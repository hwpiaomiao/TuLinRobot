package com.example.tulindemo;

public class ListData {

	public static final int SEND=1;
	public static final int RECEIVER=2;
	private String context;
	private int flag;
	private String time;
	
	public ListData(String context,int flag,String time) {
		setContext(context);
		setFlag(flag);
		setTime(time);
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
