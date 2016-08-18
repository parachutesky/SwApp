package com.jnwat.bean;

/**
 * AppVersion 版本信息 Created by WeiBo on 14/12/12.
 */
public class BAppVersion extends BBase {

	/**
	 * APP版本code
	 */
	private float VERSION;
	/**
	 * APP版本号
	 */
	private String VEISIONCODE;

	public String getVEISIONCODE() {
		return VEISIONCODE;
	}

	public void setVEISIONCODE(String vEISIONCODE) {
		VEISIONCODE = vEISIONCODE;
	}

	/**
	 * 手机端
	 */
	private String PHONENAME;

	public String getPHONENAME() {
		return PHONENAME;
	}

	public void setPHONENAME(String pHONENAME) {
		PHONENAME = pHONENAME;
	}

	
	public float getVERSION() {
		return VERSION;
	}

	public void setVERSION(float vERSION) {
		VERSION = vERSION;
	}

	/**
	 * APP更新时间
	 */
	private String DATADATE;

	
	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}

	public String getDATADATE() {
		return DATADATE;
	}

	public void setDATADATE(String dATADATE) {
		DATADATE = dATADATE;
	}

	public String getINFO() {
		return INFO;
	}

	public void setINFO(String iNFO) {
		INFO = iNFO;
	}

	public String getFILENAME() {
		return FILENAME;
	}

	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public String getUPLOADUSER() {
		return UPLOADUSER;
	}

	public void setUPLOADUSER(String uPLOADUSER) {
		UPLOADUSER = uPLOADUSER;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	/**
	 * APP版本说明
	 */
	private String INFO;

	/**
	 * APP下载路径
	 */
	private String FILENAME;
	/**
	 * APP上传人
	 */
	private String UPLOADUSER;

	/**
	 * APP错误信息
	 */
	private String ErrorMsg;

}
