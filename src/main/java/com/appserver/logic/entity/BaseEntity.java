package com.appserver.logic.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础Model
 * @author Luguangqing
 *
 */
public class BaseEntity {

	@Id
	@JsonIgnore
	protected ObjectId id;
	
	@JsonIgnore
	protected long createAt = System.currentTimeMillis();
	
	@Indexed(unique = true)
	protected String sid;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	
	
}
