package com.appserver.logic.entity;

import java.util.ArrayList;

enum MediaTagPosition {
	
	Left, Right;
	
}


/**
 * 媒体文件标签
 * @author luguangqing
 *
 */
public class MediaTag {

	private String content = "";
	
	private ArrayList<Integer> textColor = new ArrayList<Integer>();
	
	private MediaTagPosition position = MediaTagPosition.Right;
	
	private Double posX = 0.0;
	
	private Double posY = 0.0;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<Integer> getTextColor() {
		return textColor;
	}

	public void setTextColor(ArrayList<Integer> textColor) {
		this.textColor = textColor;
	}

	public MediaTagPosition getPosition() {
		return position;
	}

	public void setPosition(MediaTagPosition position) {
		this.position = position;
	}

	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}
	
}
