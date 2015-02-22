package com.appserver.logic.config;

import java.io.File;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appserver.logic.dao.mongo.MongoConfig;
import com.appserver.logic.dao.redis.RedisConfig;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * Server配置
 * @author Luguangqing
 *
 */
@JacksonXmlRootElement(localName = "ServerConfig")
public class ServerConfig {

	private static Logger logger = LogManager.getLogger(ServerConfig.class);
	
	@JacksonXmlProperty(localName = "name")
	private String name = "";
	
	@JacksonXmlProperty(localName = "versionName")
	private String verName = "1.0";
	
	@JacksonXmlProperty(localName = "versionCode")
	private int verCode = 1;
	
	@JacksonXmlProperty(localName = "host")
	private String host = "";
	
	@JacksonXmlProperty(localName = "port")
	private int port = 8080;
	
	@JacksonXmlProperty(localName = "url")
	private String url = "AppServer/";
	
	@JacksonXmlProperty(localName = "transmitKey")
	private String transmitKey = "";
	
	@JacksonXmlProperty(localName = "magicKey")
	private String magicKey = "";
	
	// mongo配置
	private MongoConfig mongoConfig = new MongoConfig();
	
	// redis配置
	private RedisConfig redisConfig = new RedisConfig();
	
	private static ServerConfig instance = null;

	private ServerConfig() {
		
	}

	public static ServerConfig getInstance() {
		if (instance == null) {
			instance = loadConfig();
		}
		return instance;
	}

	private static ServerConfig loadConfig() {
		ServerConfig config = new ServerConfig();
		URL u = ServerConfig.class.getResource("/ServerConfig.xml");
		File xml = new File(u.getFile());
		if (xml == null || !xml.exists()) {
			logger.error("读取ServerConfig.xml失败: ServerConfig.xml文件不存在。");
			return config;
		} 
		try {
			logger.info("读取ServerConfig.xml:");
			XmlMapper mapper = new XmlMapper();
			config = mapper.readValue(xml, ServerConfig.class);
			logger.info("===========ServerConfig================");
			logger.info("name:" + config.getName());
			logger.info("verName:" + config.getVerName());
			logger.info("verCode:" + config.getVerCode());
			logger.info("host:" + config.getVerCode());
			logger.info("port:" + config.getVerCode());
			logger.info("url:" + config.getVerCode());
			logger.info("transmitKey:" + config.getVerCode());
			logger.info("magicKey:" + config.getVerCode());
			logger.info("===========MongoConfig=================");
			MongoConfig mongoConfig = config.getMongoConfig();
			logger.info("ip:" + mongoConfig.getIp());
			logger.info("port:" + mongoConfig.getPort());
			logger.info("dbName:" + mongoConfig.getDbName());
			logger.info("poolSize:" + mongoConfig.getPoolSize());
			logger.info("threadsAllowedToBlockForConnectionMultiplier:" + mongoConfig.getThreadsAllowedToBlockForConnectionMultiplier());
			logger.info("maxWaitTime:" + mongoConfig.getMaxWaitTime());
			logger.info("connectTimeout:" + mongoConfig.getConnectTimeout());
			logger.info("socketTimeout:" + mongoConfig.getSocketTimeout());
			logger.info("socketKeepAlive:" + mongoConfig.isSocketKeepAlive());	
			logger.info("===========RedisConfig=================");
			RedisConfig redisConfig = config.getRedisConfig();
			logger.info("ip:" + redisConfig.getIp());
			logger.info("port:" + redisConfig.getPort());
			logger.info("maxIdle:" + redisConfig.getMaxIdle());
			logger.info("maxWaitMillis:" + redisConfig.getMaxWaitMillis());
			logger.info("testOnBorrow:" + redisConfig.isTestOnBorrow());
			logger.info("testOnReturn():" + redisConfig.isTestOnReturn());
			logger.info("testWhileIdle:" + redisConfig.isTestWhileIdle());	
			logger.info("=======================================");
			logger.info("ServerConfig.xml 读取成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取ServerConfig.xml失败:" + e.getLocalizedMessage());
		}
		return config;
	}

	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}

	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTransmitKey() {
		return transmitKey;
	}

	public void setTransmitKey(String transmitKey) {
		this.transmitKey = transmitKey;
	}

	public String getMagicKey() {
		return magicKey;
	}

	public void setMagicKey(String magicKey) {
		this.magicKey = magicKey;
	}

	
}
