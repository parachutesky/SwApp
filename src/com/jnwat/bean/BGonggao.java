package com.jnwat.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * @author chang-zhiyuan 公告讨论组
 */
@Table(name = "BGonggaoTable")
public class BGonggao {

	@Id
	private int ID;

	private String userName ;// 用户名

	private String title ;// 标题名称

	private String nmb ; // 人数

	private String contentData ;// 内容
	private String time ;// 时间
	private String account ;// 时间
	private String name ;// 时间
	private boolean checkPeople = false;// 默认没有选中


	public boolean isCheckPeople() {
		return checkPeople;
	}

	public void setCheckPeople(boolean checkPeople) {
		this.checkPeople = checkPeople;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNmb() {
		return nmb;
	}

	public void setNmb(String nmb) {
		this.nmb = nmb;
	}

	public String getContentData() {
		return contentData;
	}

	public void setContentData(String contentData) {
		this.contentData = contentData;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
