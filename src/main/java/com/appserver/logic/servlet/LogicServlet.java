package com.appserver.logic.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appserver.common.Constant;
import com.appserver.common.network.SBMessage;
import com.appserver.common.network.SBMessageFile;
import com.appserver.common.util.LangUtil;
import com.appserver.logic.handler.ServerHandler;
import com.appserver.logic.helper.AnnotationManager;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(name = "LogicServlet", urlPatterns = "/main")
public class LogicServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(LogicServlet.class);
	private static final long serialVersionUID = 1L;
	
	// 限制访问频率 500次每分钟
	private static Map<String, Integer> reqFrequencyMap = new HashMap<String, Integer>();
	private static final int MAX_ACCESS = 500;
	private static Timer clearTimer = new Timer();
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		SBMessage message = new SBMessage(resp);
		try {
			if (LangUtil.isEmpty(req.getParameter("tkey"))) {
				// 客户端请求，频率拦截
				if (!accessFrequencyFilter(req.getRemoteAddr())) {
					resp.getWriter().write(Constant.BAD_ACCESS);
					return;
				}
			} 
			
			
			int reqid = Integer.parseInt(req.getParameter("rid"));
			String uid = req.getParameter("uid");
			String data = req.getParameter("data");
			if (LangUtil.isEmpty(data)) {
				logger.debug("请求失败:参数错误");
				message.send(Constant.BAD_ACCESS);
				return;
			}
			String filejson = req.getParameter("files");
			if (!LangUtil.isEmpty(filejson)) {
				ObjectMapper mapper = new ObjectMapper(); 
				JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, SBMessageFile.class);
				ArrayList<SBMessageFile> files = mapper.readValue(filejson, type);
				message.setFiles(files);
			}
			
			message.setReq_data(data);
			message.setReq_id(reqid);
			message.setReq_uid(uid);
			// 传递给handler处理业务逻辑
			ServerHandler handler = AnnotationManager.createLogicHandlerInstance(reqid);
			if (handler != null) {
				handler.process(message);
			} else {
				// 无效请求
				logger.debug("请求失败:参数错误");
				message.send(Constant.BAD_ACCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求失败:" + e.getMessage());
			message.send(Constant.BAD_ACCESS);
		}
	}
	
	private boolean accessFrequencyFilter(String ip) {
		if (LangUtil.isEmpty(ip))
			return true;
		Integer count = reqFrequencyMap.get(ip);
		if (count == null) {
			reqFrequencyMap.put(ip, 1);
		} else {
			if (count >= MAX_ACCESS) {
				return false;
			} else {
				reqFrequencyMap.put(ip, ++count);
			}
		}
		return true;
	}
	
	static {
		clearTimer.schedule(new ClearTask(), 0, 1000 * 60);
	}
	
	static class ClearTask extends TimerTask {

		public void run() {
			reqFrequencyMap.clear();
		}
	}

}
