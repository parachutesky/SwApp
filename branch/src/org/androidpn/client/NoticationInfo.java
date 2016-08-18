package org.androidpn.client;

public class NoticationInfo {

	private int time;// 消息id
	private String showtime;// 接收消息的时间

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	private String message_id;

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJson_content() {
		return json_content;
	}

	public void setJson_content(String json_content) {
		this.json_content = json_content;
	}

	public String getIs_had_open() {
		return is_had_open;
	}

	public void setIs_had_open(String is_had_open) {
		this.is_had_open = is_had_open;
	}

	public String getPush_id() {
		return push_id;
	}

	public void setPush_id(String push_id) {
		this.push_id = push_id;
	}

	private String json_content;// json内容

	private String is_had_open; // 是否打开过

	private String push_id; // 判断数据是否存在

	private String actyid;

	private String actytitle;
	private String actyurl;
	private String logo;

	private String message;
	private String messageType;
	private String shopid;

	private String shoptitle;
	private String shopurl;

	private String phone;
	private String address;
	private String introduce;
	private String sid;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getActyid() {
		return actyid;
	}

	public void setActyid(String actyid) {
		this.actyid = actyid;
	}

	public String getActytitle() {
		return actytitle;
	}

	public void setActytitle(String actytitle) {
		this.actytitle = actytitle;
	}

	public String getActyurl() {
		return actyurl;
	}

	public void setActyurl(String actyurl) {
		this.actyurl = actyurl;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getShoptitle() {
		return shoptitle;
	}

	public void setShoptitle(String shoptitle) {
		this.shoptitle = shoptitle;
	}

	public String getShopurl() {
		return shopurl;
	}

	public void setShopurl(String shopurl) {
		this.shopurl = shopurl;
	}

	public String getShowtime() {
		return showtime;
	}

	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}

	/*
	 * String actyid=object.getString("actyid"); String
	 * actytitle=object.getString("actytitle");//活动title String
	 * actyrul=object.getString("actyurl");//活动url String
	 * cycle=object.getString("cycle");//循环周期 String
	 * message=object.getString("message");//内容 String
	 * messageType=object.getString("messageType");//0代表万达，1代表商家活动，2代表商家 String
	 * pushstarttime=object.getString("pushstarttime");//推送开始时间 String
	 * pushstoptime=object.getString("pushstoptime");//推送结束时间 String
	 * shopid=object.getString("shopid");//商家id String
	 * shoptitle=object.getString("shoptitle");//商家title String
	 * shopurl=object.getString("shopurl");//商家url
	 */
}
