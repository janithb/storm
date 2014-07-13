package com.janith.storm;


public class StromContext {
	
	public ServerContext getServerContext(PackagesResource res, int port) {
		return new ServerContext(res, port);
	}
	
	public ClientContext getClientContext(String url){
		return new ClientContext(url);
	}
}
