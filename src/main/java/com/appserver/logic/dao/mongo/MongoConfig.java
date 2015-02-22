package com.appserver.logic.dao.mongo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "mongoConfig")
public class MongoConfig {

	@JacksonXmlProperty(localName = "ip")
	private String ip = "";

	@JacksonXmlProperty(localName = "port")
	private int port = 0;

	@JacksonXmlProperty(localName = "dbName")
	private String dbName = "";

	@JacksonXmlProperty(localName = "poolSize")
	private int poolSize = 10;

	@JacksonXmlProperty(localName = "threadsAllowedToBlockForConnectionMultiplier")
	private int threadsAllowedToBlockForConnectionMultiplier = 5;
	
	@JacksonXmlProperty(localName = "maxWaitTime")
	private int maxWaitTime = 120000;
	
	@JacksonXmlProperty(localName = "connectTimeout")
	private int connectTimeout = 10000;
	
	@JacksonXmlProperty(localName = "socketTimeout")
	private int socketTimeout = 0;
	
	@JacksonXmlProperty(localName = "socketKeepAlive")
	private boolean socketKeepAlive = false;

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

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public boolean isSocketKeepAlive() {
		return socketKeepAlive;
	}

	public void setSocketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
