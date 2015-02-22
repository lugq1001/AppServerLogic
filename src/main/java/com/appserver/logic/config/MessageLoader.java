package com.appserver.logic.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 国际化工具类 message.properties读取
 * @author Luguangqing
 *
 */
public class MessageLoader {

	private static Logger logger = LogManager.getLogger(MessageLoader.class);

	public static Map<String, String> msaages = new HashMap<String, String>();
	
	public static String load(String code) {
		String msg = msaages.get(code);
		return msg == null ? "" : msg;
	}

	public static void loadMessage() {
		logger.info("读取message.properties:");
		try {
			Properties prop = new Properties();
			InputStream in = MessageLoader.class.getResourceAsStream("/message.properties");
			prop.load(in);
			Enumeration<?> e = prop.propertyNames();// 得到配置文件的名字
			logger.info("===========================");
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String val = prop.getProperty(key);
				logger.info(key + " = " + val);
				msaages.put(key, val);
			}
			logger.info("===========================");
			logger.info("message.properties 读取成功");
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取message.properties:" + e.getLocalizedMessage());
		}
		

	}
}
