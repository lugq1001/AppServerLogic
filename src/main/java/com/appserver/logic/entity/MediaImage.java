package com.appserver.logic.entity;

import java.util.ArrayList;


/**
 * 用户发布图片
 * @author luguangqing
 *
 */
public class MediaImage {

	private String imageURL = "";
	
	private ArrayList<MediaTag> tags = new ArrayList<MediaTag>();

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public ArrayList<MediaTag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<MediaTag> tags) {
		this.tags = tags;
	}

}
