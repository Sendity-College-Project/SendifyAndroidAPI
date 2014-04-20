package com.sendify.client;

import java.util.EventObject;

public class MessageObject extends EventObject {

	SubsData sData = null;
	
	public MessageObject(Object source, SubsData sData) {
		super(source);
		this.sData = sData;
	}
	
	public SubsData getData(){
		return this.sData;
	}
}
