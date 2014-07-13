package com.janith.storm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.janith.storm.extra.net.ser1.stomp.Client;
import com.janith.storm.extra.net.ser1.stomp.Listener;
import com.janith.storm.extra.net.ser1.stomp.Message;
import com.janith.storm.extra.net.ser1.stomp.Server;
import com.janith.storm.extra.net.ser1.stomp.StompImpl;

public class Main {

	public static void main(String[] args) {
		StompImpl local_client = null;
		try {
			// Queue queue =

			Server server = new Server(10000);

			System.out.println("starting server");

			 local_client = server.getClient();

			local_client.subscribe("/command/info", new Listener() {
				public void message(Map header, String body) {
					System.out.println("Server " +body);
					System.out.println("Server header"+header.get("method"));
					if (body.equals("get-info")) {
						System.out.println("header"+header.get("method"));
						System.out.println(body);
					}
				}
			});
			
			local_client.send("/command/info", "Message number one");
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
		
		c.subscribe("/command/info", new Listener() {
			
			@Override
			public void message(Map headers, String body) {
				System.out.println("Client" + body);
				
			}
		});
		
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("method", "SampleImpl.getApple");
		c.send("/command/info", "Message number two",headerMap);
		
		
		local_client.send("/command/info", "Message number ttt");
		
	}
}
