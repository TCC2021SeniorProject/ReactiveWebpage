package com.iot.webserverapp.submission.controller;

import org.apache.catalina.connector.Connector;

public class PageRedirectController {

	protected Connector redirectConnector() {
	  Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	  connector.setScheme("http");
	  connector.setPort(8080);
	  connector.setSecure(false);
	  connector.setRedirectPort(443);
	  return connector;
	}
}
