package com.jnwat.bean;

/**
 * @author chang-zhiyuan 2016年4月21日 14:34:19 消息通知的bean
 */
public class BNoticeMessage {
	private String ID;
	private String TITLE;
	/**
	 * 
	 */
	private String FK_USERID;
	private String Account;
	private String SENDUSER;
	private String SENDDATE;
	private String BDATE;
	private String EDATE;
	private String CONTENT;
	private String NOTICETYPE;

	public String getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "BNoticeMessage [ID=" + ID + ", TITLE=" + TITLE + ", FK_USERID="
				+ FK_USERID + ", Account=" + Account + ", SENDUSER=" + SENDUSER
				+ ", SENDDATE=" + SENDDATE + ", BDATE=" + BDATE + ", EDATE="
				+ EDATE + ", CONTENT=" + CONTENT + ", NOTICETYPE=" + NOTICETYPE
				+ ", READSTATUS=" + READSTATUS + "]";
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public String getFK_USERID() {
		return FK_USERID;
	}

	public void setFK_USERID(String fK_USERID) {
		FK_USERID = fK_USERID;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getSENDUSER() {
		return SENDUSER;
	}

	public void setSENDUSER(String sENDUSER) {
		SENDUSER = sENDUSER;
	}

	public String getSENDDATE() {
		return SENDDATE;
	}

	public void setSENDDATE(String sENDDATE) {
		SENDDATE = sENDDATE;
	}

	public String getBDATE() {
		return BDATE;
	}

	public void setBDATE(String bDATE) {
		BDATE = bDATE;
	}

	public String getEDATE() {
		return EDATE;
	}

	public void setEDATE(String eDATE) {
		EDATE = eDATE;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getNOTICETYPE() {
		return NOTICETYPE;
	}

	public void setNOTICETYPE(String nOTICETYPE) {
		NOTICETYPE = nOTICETYPE;
	}

	public String getREADSTATUS() {
		return READSTATUS;
	}

	public void setREADSTATUS(String rEADSTATUS) {
		READSTATUS = rEADSTATUS;
	}

	private String READSTATUS;

}
