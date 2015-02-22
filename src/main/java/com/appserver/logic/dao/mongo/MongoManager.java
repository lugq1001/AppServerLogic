package com.appserver.logic.dao.mongo;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.appserver.logic.config.ServerConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MongoManager {

	private static Logger logger = LogManager.getLogger(MongoManager.class);

	public static Datastore store = null;

	static {
		MongoConfig config = ServerConfig.getInstance().getMongoConfig();
		MongoClientOptions.Builder builder = MongoClientOptions.builder();
		builder.connectionsPerHost(config.getPoolSize());
		builder.threadsAllowedToBlockForConnectionMultiplier(config.getThreadsAllowedToBlockForConnectionMultiplier());
		builder.maxWaitTime(config.getMaxWaitTime());
		builder.connectTimeout(config.getConnectTimeout());
		builder.socketTimeout(config.getSocketTimeout());
		builder.socketKeepAlive(config.isSocketKeepAlive());
		MongoClientOptions opt = builder.build();

		ServerAddress addr = null;
		try {
			addr = new ServerAddress(config.getIp(), config.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("创建Mongo连接失败：" + e.getMessage());
		}
		MongoClient mongo = new MongoClient(addr, opt);
		Morphia morphia = new Morphia();
		store = morphia.createDatastore(mongo, config.getDbName());
		store.ensureIndexes(); // 创建索引
		store.ensureCaps(); // 设置默认的mongoDB集合容量
	}
}
