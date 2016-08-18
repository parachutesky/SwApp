package com.jnwat.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * @author chang-zhiyuan 推送消息
 */
// 指定该类保存在数据库中的表名
@Table(name = "PushMessageTable")
public class BPushMessageInfo {
	//@NoAutoIncrement
	@Id
	private int ID;
	
	/**
	 * 数据的ID
	 */
	private String IDid;


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * 消息内容
	 */
	private String MsgContent;

	public String getIDid() {
		return IDid;
	}

	public void setIDid(String id) {
		this.IDid = id;
	}

	public String getMsgContent() {
		return MsgContent;
	}

	public void setMsgContent(String msgContent) {
		MsgContent = msgContent;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getFromUser() {
		return FromUser;
	}

	public void setFromUser(String fromUser) {
		FromUser = fromUser;
	}

	public String getMsgSendTime() {
		return MsgSendTime;
	}

	public void setMsgSendTime(String msgSendTime) {
		MsgSendTime = msgSendTime;
	}

	public int getMsgType() {
		return MsgType;
	}

	public void setMsgType(int msgType) {
		MsgType = msgType;
	}

	private int isRead;

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	/**
	 * 发送标题
	 */
	private String Title;
	/**
	 * 发送人
	 */
	private String FromUser;

	/**
	 * 发送时间
	 */
	private String MsgSendTime;
	/**
	 * 消息类型
	 */
	private int MsgType;
	/**
	 * 地址
	 */
	private String NewsUrl;
	/**
	 * 是不是代办
	 */
	private String isDaiban;
	
	public String getIsDaiban() {
		return isDaiban;
	}

	public void setIsDaiban(String isDaiban) {
		this.isDaiban = isDaiban;
	}

	private String Fid;
	
	public String getFid() {
		return Fid;
	}

	public void setFid(String fid) {
		Fid = fid;
	}

	private String FlowID;
	public String getFlowID() {
		return FlowID;
	}

	public void setFlowID(String flowID) {
		FlowID = flowID;
	}

	public String getWorkID() {
		return WorkID;
	}

	public void setWorkID(String workID) {
		WorkID = workID;
	}

	private String WorkID;
	

	public String getNewsUrl() {
		return NewsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		NewsUrl = newsUrl;
	}
	/**
	 * 用户名
	 */
	private String LoginName;


	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

}
