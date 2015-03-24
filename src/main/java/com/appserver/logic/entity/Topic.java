package com.appserver.logic.entity;

import java.util.ArrayList;
import java.util.Date;


enum TopicMediaType {
	
	Image, Video;
	
}

/**
 * 用户发布主题
 * @author luguangqing
 *
 */
public class Topic {

	private TopicMediaType mediaType = TopicMediaType.Image;
	
	private ArrayList<MediaImage> images = new ArrayList<MediaImage>();
	
	private String sid = "";
	
	private User user;
	
	private String content = "";
	
	private Date issueTime = new Date();
	
	private int commentCount = 0;
	
	private int likedNum = 0;
	
	private String location = "";

	public TopicMediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(TopicMediaType mediaType) {
		this.mediaType = mediaType;
	}

	public ArrayList<MediaImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<MediaImage> images) {
		this.images = images;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getLikedNum() {
		return likedNum;
	}

	public void setLikedNum(int likedNum) {
		this.likedNum = likedNum;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}









