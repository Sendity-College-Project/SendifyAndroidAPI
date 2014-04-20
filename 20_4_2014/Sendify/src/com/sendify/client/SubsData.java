package com.sendify.client;

public class SubsData {

	private String data;
	private String channelName;
	
	public SubsData(String data) {
		this.data = data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	public String getData(){
		return this.data;	
	}
	
	public void setChannelName(String name){
		this.channelName = name;
	}
	
	public String getChannelName(){
		return this.channelName;
	}
	
}
