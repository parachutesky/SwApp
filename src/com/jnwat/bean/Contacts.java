package com.jnwat.bean;

import java.util.List;

//通讯录信息
public class Contacts {
      private String name;//姓名
      private String sex;//性别
      private String pingyin;//全拼
      private String shortPingyin;//简称 拼音
      private String department;//部门
      private String mobPhone;//移动电话
      private String offPhone;//办公电话
      private String email;//邮箱
      private String headUrl;//头像路径
      private String firstLetter;//首字母
      private String account ;//首字母
      
      
      
      
      
    public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	private List<Contacts2>  ReplyObject; 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getMobPhone() {
		return mobPhone;
	}
	public void setMobPhone(String mobPhone) {
		this.mobPhone = mobPhone;
	}
	public String getOffPhone() {
		return offPhone;
	}
	public void setOffPhone(String offPhone) {
		this.offPhone = offPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	public String getPingyin() {
		return pingyin;
	}
	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}
	public String getShortPingyin() {
		return shortPingyin;
	}
	public void setShortPingyin(String shortPingyin) {
		this.shortPingyin = shortPingyin;
	}
	public List<Contacts2> getReplyObject() {
		return ReplyObject;
	}
	public void setReplyObject(List<Contacts2> replyObject) {
		ReplyObject = replyObject;
	}
      
}
