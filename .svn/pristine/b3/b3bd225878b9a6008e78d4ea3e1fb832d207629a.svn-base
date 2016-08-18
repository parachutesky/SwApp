package com.jnwat.bean;

/**
 * beans基础类 Created by WeiBo on 14/12/11.
 */
public class BBase {
	/**
	 * 是否成功
	 */
	private boolean isSuccess;
	/**
	 * 扩展数据
	 */
	private String extdata;

	/**
	 * data数据
	 */
	private String data;

	/**
	 * 错误原因
	 */
	private String errorMsg;

	/**
	 * @return 是否成功
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess
	 *            是否成功
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getExtdata() {
		return extdata;
	}

	public void setExtdata(String extdata) {
		this.extdata = extdata;
	}

	/**
	 * 错误原型
	 * 
	 * @param errorMsg
	 */
	protected void setNetError(String Msg) {
		setSuccess(false);
		setErrorMsg(Msg);
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
