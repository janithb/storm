package com.janith.storm;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import com.janith.storm.extra.net.ser1.stomp.Listener;
import com.janith.storm.extra.net.ser1.stomp.Server;
import com.janith.storm.extra.net.ser1.stomp.StompImpl;

public class ServerContext {

	public ServerContext(PackagesResource res, int port) {
		MutablePicoContainer pico;
		pico = new DefaultPicoContainer();

		Set<String> classes = res.getAnnotationClasses();
		Map<String, Class<?>> destClassMap = new HashMap<String, Class<?>>();
		Map<String, Method> destMethodMap = new HashMap<String, Method>();

		for (String string : classes) {

			Class<?> clazz = null;
			try {
				clazz = Class.forName(string);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String path = null;
			Annotation[] classAnnotations = clazz.getAnnotations();
			for (Annotation annotation : classAnnotations) {
				if (annotation instanceof Stomp) {
					Stomp stompc = (Stomp) annotation;
					path = stompc.value();
				}
			}

			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				Annotation[] annotations = method.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof Stomp) {
						Stomp stompm = (Stomp) annotation;
						destClassMap.put(path + stompm.value(), clazz);
						destMethodMap.put(path + stompm.value(), method);
						Object obj = pico.getComponent(clazz);
						if (obj == null) {
							pico.addComponent(clazz);
						}

					}
				}
			}
		}

		Server server = null;
		try {
			server = new Server(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("starting server");

		StompImpl local_client = server.getClient();

		local_client.subscribe("/stomp", new Listener() {
			public void message(Map header, String body) {
				if (header.get("type") == null
						|| !header.get("type").equals("resonse")) {

					if (body.equals("get-info")) {

					} else {
						Object returnObj = null;
						String path = (String) header.get("path");
						Class<?> clazz = destClassMap.get(path);
						Object obj = pico.getComponent(clazz);
						Method method = destMethodMap.get(path);
						try {
							returnObj = method.invoke(obj);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
						Map<String, String> headerMap = new HashMap<String, String>();
						headerMap.put("type", "resonse");
						local_client.send("/stomp", returnObj.toString());
					}
				}
			}

		});
	}
}
