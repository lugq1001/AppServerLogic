package com.appserver.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appserver.common.network.BaseRequest;
import com.appserver.common.network.BaseResponse;
import com.appserver.common.network.MessageID;
import com.appserver.common.network.SBMessage;
import com.appserver.common.util.LangUtil;
import com.appserver.common.util.MD5Util;
import com.appserver.logic.config.MessageLoader;
import com.appserver.logic.config.ServerConfig;
import com.appserver.logic.entity.User;
import com.appserver.logic.helper.annotation.LogicHandler;


@LogicHandler(desc = "用户注册接口", id = MessageID.USR_REGISTER)
public class RegisterHandler extends ServerHandler {

	private static Logger logger = LogManager.getLogger(LoginHandler.class);
	
	@Override
	public void logicProcess(SBMessage message) {
		try {
			logger.debug("-用户注册-");
			String reqData = message.getReq_data();
			logger.debug(reqData);
			RegisterRequest req = objMapper.readValue(reqData, RegisterRequest.class);
			
			String username = req.username;
			String password = req.password;
			String s = req.getS();
			
			// 验证MD5
			String magicKey = ServerConfig.getInstance().getMagicKey();
			String verify = MD5Util.md5(username + magicKey + password);
			if (!verify.equals(s)) {
				logger.debug("-注册失败-验证错误");
				sendFailureResp(message, RegisterResult.FailureVerify);
				return;
			}
			
			// 用户名格式错误
			if (LangUtil.isEmpty(username) || username.length() < 6 || username.length() > 12) {
				logger.debug("-注册失败-用户名格式错误");
				sendFailureResp(message, RegisterResult.FailureUsernameFormat);
				return;
			}
			
			// 查找用户
			User u = User.findByUsername(username);
			
			// 用户已存在
			if (u != null) {
				logger.debug("-注册失败-" + username + " 已存在");
				sendFailureResp(message, RegisterResult.FailureUsernameExist);
				return;
			}
			
			// 密码格式错误
			if (LangUtil.isEmpty(password) || username.length() < 6 || username.length() > 20) {
				logger.debug("-注册失败-" + username + " 密码格式错误");
				sendFailureResp(message, RegisterResult.FailurePasswordFormat);
				return;
			}
			
			logger.debug("-用户注册-" + " " + username + " " + password);
			
			u = new User();
			u.setUsername(username);
			u.setPassword(password);
			u.save();
			
			// 响应
			RegisterResult result = RegisterResult.Success;
			RegisterResponse resp = new RegisterResponse(result.ordinal(), MessageLoader.load(result.i18nCode));
			resp.user = u;
			message.send(resp);
			logger.debug("-注册成功-" + u.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-注册失败-" + e.getLocalizedMessage());
			sendFailureResp(message, RegisterResult.Failure);
		}
	}

	private static class RegisterRequest extends BaseRequest {
		
		public String username;
		public String password;
	
	}
	
	public class RegisterResponse extends BaseResponse {

		public RegisterResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
			reqid = MessageID.USR_REGISTER;
		}
		
		public User user;
	}
	
	private void sendFailureResp(SBMessage message, RegisterResult result) {
		RegisterResponse resp = new RegisterResponse(result.ordinal(), MessageLoader.load(result.i18nCode));
		message.send(resp);
	}

	private enum RegisterResult {
		Success(MessageID.USR_REGISTER + ".succ"), 
		Failure(MessageID.USR_REGISTER + ".failure"),
		FailureVerify(MessageID.USR_REGISTER + ".failure.verify"),
		FailureUsernameExist(MessageID.USR_REGISTER + ".failure.username.exist"),
		FailureUsernameFormat(MessageID.USR_REGISTER + ".failure.username.format"),
		FailurePasswordFormat(MessageID.USR_REGISTER + ".failure.password.format");

		public String i18nCode;

		private RegisterResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}
	}

	

}
