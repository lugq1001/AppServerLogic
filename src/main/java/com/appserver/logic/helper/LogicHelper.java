package com.appserver.logic.helper;

import com.appserver.logic.config.ServerConfig;


public class LogicHelper {


	public static String fileDownloadUrl(String filePath) {
		String prefix = ServerConfig.getInstance().getDownloadUrlPrefix();
		String downloadPath = prefix + filePath;
		downloadPath = downloadPath.replaceAll("\\\\", "/");
		return downloadPath;
	}
}
