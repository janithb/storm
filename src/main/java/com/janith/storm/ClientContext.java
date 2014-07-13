package com.janith.storm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.janith.storm.extra.net.ser1.stomp.Client;
import com.janith.storm.extra.net.ser1.stomp.Listener;

public class ClientContext {

	Client c = null;
	public ClientContext(String url) {
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				try {
		c = new Client(uri.getHost(), uri.getPort(), "ser", "ser");
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

		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("path", "/test/abc");
		headerMap.put("type", "request");
		c.send("/stomp", "Message number two", headerMap);
	}
	
	public Object send(){
		return true;
	}
}
