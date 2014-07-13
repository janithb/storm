package com.janith.storm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.janith.storm.extra.net.ser1.stomp.Client;
import com.janith.storm.extra.net.ser1.stomp.Listener;
import com.janith.storm.sample.SampleImpl;

public class Strom {
	public static void main(String[] args) {
		PackagesResource resource = new PackagesResource(SampleImpl.class);
		StromContext context = new StromContext(resource);
		
		
		Client c = null;
		try {
			c = new Client( "localhost", 10000, "ser", "ser" );
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.subscribe("/stomp", new Listener() {
			
			@Override
			public void message(Map headers, String body) {
				if (headers.get("type") == null
						|| !headers.get("type").equals("request")) {
				System.out.println("Client" + body);
				}
			}
		});
		
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("path", "/test/abc");
		headerMap.put("type", "request");
		c.send("/stomp", "Message number two",headerMap);
		
	}
}
