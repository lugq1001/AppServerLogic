package com.appserver.logic.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.appserver.logic.config.MessageLoader;
import com.appserver.logic.config.ServerConfig;
import com.appserver.logic.dao.redis.RedisManager;
import com.appserver.logic.entity.Sid;
import com.appserver.logic.entity.Sid.EntitySeq;
import com.appserver.logic.helper.AnnotationManager;


@WebListener
public class ContextListener implements ServletContextListener {

	private static Logger logger = LogManager.getLogger(ContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Servlet start init");
		logger.info("加载配置文件");
		ServerConfig.getInstance();
		logger.info("加载国际化资源");
		MessageLoader.loadMessage();
		logger.info("加载自定义标签");
		AnnotationManager.initAnnotation();
		
		// 测试数据库连接
		try {
			long seq = Sid.getNextSequence(EntitySeq.Test);
			logger.info("mongo test seq:" + seq + ",连接成功");
		} catch (Exception e) {
			logger.error("Mongo连接失败");
		}
		Jedis j = null;
		try {
			j = RedisManager.getResource();
			j.set("test", System.currentTimeMillis() + "");
			logger.info("redis test value:" + j.get("test") + ",连接成功");
		} catch (Exception e) {
			logger.error("Redis连接失败");
		} finally {
			if (j != null) {
				RedisManager.returnResource(j);
			}
		}
		
		logger.info("===============================================");
		logger.info("==   *************************************   ==");
		logger.info("==   *****                           *****   ==");
		logger.info("==   *****   Logic Server Started    *****   ==");
		logger.info("==   *****                           *****   ==");
		logger.info("==   *************************************   ==");
		logger.info("===============================================");
	}
}
