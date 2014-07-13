package com.janith.storm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageBody implements Serializable{

	private static final long serialVersionUID = -2947686801217294557L;
	
	List<Object> paramList = new ArrayList<>();

	public List<Object> getParamList() {
		return paramList;
	}

	public void setParamList(List<Object> paramList) {
		this.paramList = paramList;
	}
	
	public void setParameter(Object obj){
		paramList.add(obj);
	}
}
