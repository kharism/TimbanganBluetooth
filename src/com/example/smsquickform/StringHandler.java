package com.example.smsquickform;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;


@SuppressLint("NewApi")
public class StringHandler {
	private Map<String,AbstractHandler> mHandlers;
	public StringHandler(){
		mHandlers = new HashMap<String, AbstractHandler>();
		mHandlers.put("BB",new BBeratHandler());
	}
	private boolean isNumeric(String j){
		try{
			double d = Double.parseDouble(j);
		}catch(NumberFormatException ex){
			return false;
		}
		return true;
	}
	public String Handle(String input){
		String[] lines=input.split("\r\n");
		int g=0;
		if(lines[g].isEmpty() || !this.mHandlers.containsKey(lines[g].split(" ")[0])){
			g++;
		}
		String h = "";
		try{
			do{
				String i = new String(lines[g].split(" ")[0]);
				AbstractHandler l = (AbstractHandler)mHandlers.get(i);
				h=l.Handle(lines[g]);
				g++;
			}
			while(h.isEmpty()&&g<lines.length);
			return h;
		}catch(Exception ex){
			return "";
		}
	}
	public String getSatuan(String input){
		String[] lines=input.split("\r\n");
		int g=0;
		if(lines[g].isEmpty() || !this.mHandlers.containsKey(String.valueOf(lines[g].charAt(0)))){
			g++;
		}
		String i = new String(lines[g].split(" ")[0]);
		AbstractHandler l = (AbstractHandler)mHandlers.get(i);
		return l.getSatuan(lines[g]);
	}
}
