package com.janith.storm.sample;

import com.janith.storm.Stomp;

@Stomp("/test")
public class SampleImpl {
	
	
	@Stomp("/abc")
	public String getApple(){
		System.out.println("calling getApple");
		return "Lot of apple Return";
	}
}
