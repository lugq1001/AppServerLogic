package com.appserver.logic.handler;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.appserver.common.annotation.LogicHandler;
import com.appserver.common.network.BaseResponse;
import com.appserver.common.network.MessageID;
import com.appserver.common.network.SBMessage;
import com.appserver.common.network.SBMessageFile;
import com.appserver.logic.config.MessageLoader;
import com.appserver.logic.entity.User;
import com.appserver.logic.helper.LogicHelper;


@LogicHandler(id = MessageID.TEST, desc = "测试")
public class TestHandler extends ServerHandler {

	private static Logger logger = LogManager.getLogger(TestHandler.class);
	
	@Override
	public void logicProcess(SBMessage message) {
		try {
			logger.debug("-测试(logic)-");
			logger.debug("-测试(logic)-请求数据：" + message.getReq_data());
			//TestRequest req = objMapper.readValue(message.getReq_data(), TestRequest.class);
			String userSid = message.getReq_uid();
			ArrayList<SBMessageFile> files = message.getFiles();
			/*if (LangUtil.isEmpty(files)) {
				logger.debug("-测试(logic)-文件为空");
				sendFailureResp(message, TestResult.FailureFileNone);
				return;
			}*/
			
			User u = User.findBySid(userSid);
			if (u == null) {
				logger.debug("-测试(logic)-用户不存在");
				sendFailureResp(message, TestResult.Failure);
				return;
			}
			
			u.setAvatar(files.get(0).getFilePath());
			u.save();
			logger.debug("-测试(logic)-更新用户头像成功");
			TestResponse resp = new TestResponse(TestResult.Success.ordinal(), MessageLoader.load(TestResult.Success.i18nCode));
			resp.userSid = userSid;
			for (SBMessageFile f : files) {
				String path = LogicHelper.fileDownloadUrl(f.getFilePath());
				resp.filePaths.add(path);
				logger.debug("-头像下载路径-" + path);
			}
			message.send(respString(resp));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-测试(logic)-(文件上传) 失败:" + e.getLocalizedMessage());
			sendFailureResp(message, TestResult.Failure);
		}
	}
	
	private enum TestResult {
		Success(MessageID.TEST + ".succ"), 
		Failure(MessageID.TEST + ".failure"),
		FailureFileNone(MessageID.TEST + ".failure.file.none"),
		FailureFileMax(MessageID.TEST + ".failure.file.max");
		
		public String i18nCode;
		
		private TestResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}

	}
	
	private void sendFailureResp(SBMessage message, TestResult result) {
		TestResponse resp = new TestResponse(result.ordinal(), MessageLoader.load(result.i18nCode));
		message.send(respString(resp));
	}

	public class TestResponse extends BaseResponse {
		
		public String userSid;
		public List<String> filePaths = new ArrayList<String>();
		
		public TestResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
		}
	}
}
