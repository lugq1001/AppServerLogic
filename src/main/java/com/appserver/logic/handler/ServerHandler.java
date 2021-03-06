package com.appserver.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appserver.common.network.BaseResponse;
import com.appserver.common.network.SBMessage;
import com.appserver.common.util.StringBufferLine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 业务逻辑处理入口
 * @author Luguangqing
 *
 */
public abstract class ServerHandler {

	private static Logger logger = LogManager.getLogger(ServerHandler.class);
	
	protected ObjectMapper objMapper = new ObjectMapper();
	
	public abstract void logicProcess(SBMessage message);
	
	public void process(SBMessage message) {
		StringBufferLine logBuffer = new StringBufferLine();
		logBuffer.append("\n*************************** LogicServerHandler process start *********************************************************");
		long time = System.currentTimeMillis();
		logBuffer.append("== reqid(" + message.getReq_id() + ") logic process start ==");
		logicProcess(message);
		long interval = System.currentTimeMillis() - time;
		logBuffer.append("== reqid(" + message.getReq_id() + ") completed with logic process in " + interval + " ms. ==");
		logBuffer.append("***************************** LogicServerHandler process end *******************************************************");
		logger.info(logBuffer.toString());
	}
	
	protected String respString(BaseResponse resp) {
		String json = "";
		try {
			json = objMapper.writeValueAsString(resp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.info("make respString failure:" + e.getLocalizedMessage());
		}
		return json;
	}
	
	
}
