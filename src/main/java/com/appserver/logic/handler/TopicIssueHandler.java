package com.appserver.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appserver.common.annotation.LogicHandler;
import com.appserver.common.network.BaseRequest;
import com.appserver.common.network.BaseResponse;
import com.appserver.common.network.MessageID;
import com.appserver.common.network.SBMessage;
import com.appserver.common.util.LangUtil;
import com.appserver.common.util.MD5Util;
import com.appserver.logic.config.MessageLoader;
import com.appserver.logic.config.ServerConfig;
import com.appserver.logic.entity.User;



@LogicHandler(desc = "发布主题", id = MessageID.TOPIC_ISSUE)
public class TopicIssueHandler extends ServerHandler {

	private static Logger logger = LogManager.getLogger(TopicIssueHandler.class);
	
	@Override
	public void logicProcess(SBMessage message) {
		try {
			logger.debug("-发布主题-");
			String reqData = message.getReq_data();
			logger.debug("-请求数据-" + reqData);
			TopicIssueRequest req = objMapper.readValue(reqData, TopicIssueRequest.class);

			String username = req.userSid;
			String s = req.getS();

			// 验证MD5
			String magicKey = ServerConfig.getInstance().getMagicKey();
			String verify = MD5Util.md5(username + magicKey);
			if (!verify.equals(s)) {
				logger.debug("-登录失败-验证错误");
				sendFailureResp(message, TopicIssueResult.FailureVerify);
				return;
			}

			// 用户名为空
			if (LangUtil.isEmpty(username)) {
				logger.debug("-登录失败-用户名为空");
				sendFailureResp(message, TopicIssueResult.FailureUserNone);
				return;
			}

			// 查找用户
			User u = User.findByUsername(username);

			// 用户不存在
			if (u == null) {
				logger.debug("-登录失败-" + username + " 不存在");
				sendFailureResp(message, TopicIssueResult.FailureUserNone);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-登录失败-" + e.getLocalizedMessage());
			//sendFailureResp(message, TopicIssueResult.Failure);
		}
	}

	private static class TopicIssueRequest extends BaseRequest {

		public String userSid;
		public String content;
		

	}

	public class TopicIssueResponse extends BaseResponse {

		public TopicIssueResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
			reqid = MessageID.TOPIC_ISSUE;
		}

		public User user;
	}

	private void sendFailureResp(SBMessage message, TopicIssueResult result) {
		TopicIssueResponse resp = new TopicIssueResponse(result.ordinal(), MessageLoader.load(result.i18nCode));
		message.send(respString(resp));
	}

	private enum TopicIssueResult {
		Success(MessageID.TOPIC_ISSUE + ".succ"),
		Failure(MessageID.TOPIC_ISSUE + ".failure"),
		FailureVerify(MessageID.TOPIC_ISSUE + ".failure.verify"),
		FailureUserNone(MessageID.TOPIC_ISSUE + ".failure.user.none"),
		FailureFileMax(MessageID.TOPIC_ISSUE + ".failure.file.max"),
		FailureContentMax(MessageID.TOPIC_ISSUE + ".failure.content.max");

		public String i18nCode;

		private TopicIssueResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}
	}

}
