package com.appserver.logic.dao.redis;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "mongoConfig")
public class RedisConfig {

	@JacksonXmlProperty(localName = "ip")
	private String ip = "";

	@JacksonXmlProperty(localName = "port")
	private int port = 0;
	
	@JacksonXmlProperty(localName = "maxIdle")
	private int maxIdle = 0;
	
	@JacksonXmlProperty(localName = "maxWaitMillis")
	private int maxWaitMillis = 0;
	
	@JacksonXmlProperty(localName = "testWhileIdle")
	private boolean testWhileIdle = true;
	
	@JacksonXmlProperty(localName = "testOnBorrow")
	private boolean testOnBorrow = true;
	
	@JacksonXmlProperty(localName = "testOnReturn")
	private boolean testOnReturn = true;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	
}
