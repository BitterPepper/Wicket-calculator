package com.macbook;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.MovedContextHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacCalcStart {
	private static final Logger log = LoggerFactory.getLogger(MacCalcStart.class);

	public static void main(String[] args) throws Exception
	{
		Server server = new Server();

		WebAppContext web = new WebAppContext();
		web.setContextPath("/");
		web.setWar("src/main/webapp");
		
		server.addHandler(web);
		server.addHandler(new MovedContextHandler(server, "/", "/"));

		SelectChannelConnector http = new SelectChannelConnector();
		http.setPort(8080);
		server.setConnectors(new Connector[] { http });

		server.start();
		log.info("Server started. Address for access http://localhost:8080/maccalc");
		server.join();
	}
}