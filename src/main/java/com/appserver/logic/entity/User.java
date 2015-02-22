package com.appserver.logic.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.query.Query;

import com.appserver.logic.dao.mongo.MongoDao;
import com.appserver.logic.dao.mongo.MongoManager;
import com.appserver.logic.entity.Sid.EntitySeq;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(value="user", noClassnameStored = true)
public class User extends BaseEntity {

	@Indexed(unique = true)
	private String username = "";

	@JsonIgnore
	private String password = "";

	private String nickname = "";

	private String avatar = "";

	private String cellphone = "";

	@JsonIgnore
	private long lateseLogin = 0;

	/* ======================= DAO ======================= */

	private static MongoDao<User> getDao() {
		return new MongoDao<User>(User.class, MongoManager.store);
	}

	public void save() {
		MongoDao<User> dao = getDao();
		if (sid == null) {
			sid = Sid.getNextSequence(EntitySeq.User) + "";
		}
		dao.save(this);
	}

	public static User findByUsername(String username) {
		MongoDao<User> dao = getDao();
		Query<User> q = dao.createQuery("username", username);
		User u = dao.findOne(q);
		return u;
	}
	
	public static User findBySid(String sid) {
		MongoDao<User> dao = getDao();
		Query<User> q = dao.createQuery("sid", sid);
		User u = dao.findOne(q);
		return u;
	}
	
	public static List<User> findAll() {
		MongoDao<User> dao = getDao();
		//Query<User> q = dao.createQuery().limit(3).order("-sid").offset(2);
		//q.filter(condition, value)
		List<User> users = dao.find().asList();
		return users;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getLateseLogin() {
		return lateseLogin;
	}

	public void setLateseLogin(long lateseLogin) {
		this.lateseLogin = lateseLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("\n");
		b.append("****************User*********************\n");
		b.append("ObjectId:" + id + "\n");
		b.append("sid:" + sid + "\n");
		b.append("createAt:" + createAt + "\n");
		b.append("username:" + username + "\n");
		b.append("nickname:" + nickname + "\n");
		b.append("avatar:" + avatar + "\n");
		b.append("cellphone:" + cellphone + "\n");
		b.append("latestLogin:" + lateseLogin + "\n");
		b.append("*****************************************\n");
		return b.toString();
	}

}
