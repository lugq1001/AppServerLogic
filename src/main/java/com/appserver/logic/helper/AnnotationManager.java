package com.appserver.logic.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appserver.common.annotation.LogicHandler;
import com.appserver.common.util.PackageUtil;
import com.appserver.logic.handler.ServerHandler;


/**
 * 自定义标签工具类 
 * @author Luguangqing
 *
 */
public class AnnotationManager {

	private static Logger logger = LogManager.getLogger(AnnotationManager.class);

	private static Map<Integer, Class<?>> logicHandlers = new HashMap<Integer, Class<?>>();
	
	/**
	 * 扫描AppLogicHandler所在包 加载所有@LogicHandler标签类
	 */
	public static void initAnnotation() {
		String packageName = ServerHandler.class.getPackage().getName();
		logger.info("扫描包：" + packageName);
		logger.info("加载@LogicHandler");
		List<Class<?>> classes = PackageUtil.getClasssFromPackage(packageName);
		for (Class<?> clazz : classes) {
			LogicHandler handler = clazz.getAnnotation(LogicHandler.class);
			if (handler != null) {
				logger.info(clazz.getName() + ":" + handler.desc() + "(" + handler.id() + ")");
				logicHandlers.put(handler.id(), clazz);
			}
		}
		logger.info("@LogicHandler加载完成");
	}
	
	/**
	 * 反射读取@LogicHandler标签类
	 * @param messageID
	 * @return
	 */
	public static ServerHandler createLogicHandlerInstance(int messageID) {
		ServerHandler handler = null;
		Class<?> clazz = logicHandlers.get(messageID);
		if (clazz != null) {
			try {
				handler = (ServerHandler) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return handler;
		
	}
	
}
