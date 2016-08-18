package com.jnwat.swmobilegy.mainpager.deptmanager.bean;

public class FileBean {
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	@TreeNodeAccount
	private String account;
	private long length;
	private String desc;
	private boolean isHadSeclect = false;// 是否选中 false 默认没有选中

	public FileBean(int _id, int parentId, String name, String _account) {
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
		this.account =_account;
	}

	public boolean isHadSeclect() {
		return isHadSeclect;
	}

	public void setHadSeclect(boolean isHadSeclect) {
		this.isHadSeclect = isHadSeclect;
	}

}
