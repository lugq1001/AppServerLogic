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


@LogicHandler(desc = "用户登录接口", id = MessageID.USR_LOGIN)
public class LoginHandler extends ServerHandler {

	private static Logger logger = LogManager.getLogger(LoginHandler.class);
	
	@Override
	public void logicProcess(SBMessage message) {
		try {
			logger.debug("-用户登录-");
			String reqData = message.getReq_data();
			logger.debug("-请求数据-" + reqData);
			LoginRequest req = objMapper.readValue(reqData, LoginRequest.class);

			String username = req.username;
			String password = req.password;
			String s = req.getS();

			// 验证MD5
			String magicKey = ServerConfig.getInstance().getMagicKey();
			String verify = MD5Util.md5(username + magicKey + password);
			if (!verify.equals(s)) {
				logger.debug("-登录失败-验证错误");
				sendFailureResp(message, LoginResult.FailureVerify);
				return;
			}

			// 用户名为空
			if (LangUtil.isEmpty(username)) {
				logger.debug("-登录失败-用户名为空");
				sendFailureResp(message, LoginResult.FailureUserNone);
				return;
			}

			// 查找用户
			User u = User.findByUsername(username);

			// 用户不存在
			if (u == null) {
				logger.debug("-登录失败-" + username + " 不存在");
				sendFailureResp(message, LoginResult.FailureUserNone);
				return;
			}

			// 密码错误
			if (!u.getPassword().equals(password)) {
				logger.debug("-登录失败-" + username + " 密码错误");
				sendFailureResp(message, LoginResult.FailurePasswordError);
				return;
			}
			long time = System.currentTimeMillis();
			// 更新用户最后登录时间
			u.setLateseLogin(time);
			u.save();
			logger.debug("-更新用户最后登录时间-" + time);
			// 响应
			LoginResult result = LoginResult.Success;
			LoginResponse resp = new LoginResponse(result.ordinal(), MessageLoader.load(result.i18nCode));
			resp.user = u;
			message.send(respString(resp));
			logger.info("-登录成功-" + u.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-登录失败-" + e.getLocalizedMessage());
			sendFailureResp(message, LoginResult.Failure);
		}
	}

	private static class LoginRequest extends BaseRequest {

		public String username;
		public String password;

	}

	public class LoginResponse extends BaseResponse {

		public LoginResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
			reqid = MessageID.USR_LOGIN;
		}

		public User user;
	}

	private void sendFailureResp(SBMessage message, LoginResult result) {
		LoginResponse resp = new LoginResponse(result.ordinal(), MessageLoader.load(result.i18nCode));
		message.send(respString(resp));
	}

	private enum LoginResult {
		Success(MessageID.USR_LOGIN + ".succ"),
		Failure(MessageID.USR_LOGIN + ".failure"),
		FailureVerify(MessageID.USR_LOGIN + ".failure.verify"),
		FailureUserNone(MessageID.USR_LOGIN + ".failure.user.none"),
		FailurePasswordError(MessageID.USR_LOGIN + ".failure.password.error");

		public String i18nCode;

		private LoginResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}
	}

	

	

}
