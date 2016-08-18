package com.jnwat.bean;

/**
 * @author chang-zhiyuan 任务java bean 文件
 */
public class BUserTasks {
	

	/**
	 * 当前节点编号
	 */
	private String CurrNodeID;
	/**
	 * 当前节点名称
	 */
	private String CurrNodeName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 部门
	 * 
	 */
	private String department;//部门
	/**
	 * 流程编号
	 */
	private String FlowID;
	private String FlowName;
	/**
	 * TaskId
	 */
	private String TaskId;
	private String TaskName;
	/**
	 * 流程说明
	 */
	private String TaskDesc;
	/**
	 * 记录日期
	 */
	private String rdt;
	/**
	 * 流程节点开始时间
	 */
	/**
	 * 状态
	 */
	private String State;
	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	private String StartDate;
	private String Sender_Photo;
	private String Work_State;
	public String getWork_State() {
		return Work_State;
	}

	public void setWork_State(String work_State) {
		Work_State = work_State;
	}

	private String Sender;
	private boolean HaveDetail;
	private int AttCount;
	private String isDaiBan = "";
	public String getIsDaiBan() {
		return isDaiBan;
	}

	public void setIsDaiBan(String isDaiBan) {
		this.isDaiBan = isDaiBan;
	}

	/**
	 * 父流程工作编号
	 */
	private String FID;

	public String getSender_Photo() {
		return Sender_Photo;
	}

	public void setSender_Photo(String sender_Photo) {
		Sender_Photo = sender_Photo;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getCurrNodeID() {
		return CurrNodeID;
	}

	public void setCurrNodeID(String currNodeID) {
		CurrNodeID = currNodeID;
	}

	public String getCurrNodeName() {
		return CurrNodeName;
	}

	public void setCurrNodeName(String currNodeName) {
		CurrNodeName = currNodeName;
	}

	public String getFlowID() {
		return FlowID;
	}

	public void setFlowID(String flowID) {
		FlowID = flowID;
	}

	public String getFlowName() {
		return FlowName;
	}

	public void setFlowName(String flowName) {
		FlowName = flowName;
	}

	public String getTaskId() {
		return TaskId;
	}

	public void setTaskId(String taskId) {
		TaskId = taskId;
	}

	public String getTaskName() {
		return TaskName;
	}

	public void setTaskName(String taskName) {
		TaskName = taskName;
	}

	public String getTaskDesc() {
		return TaskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		TaskDesc = taskDesc;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public boolean isHaveDetail() {
		return HaveDetail;
	}

	public void setHaveDetail(boolean haveDetail) {
		HaveDetail = haveDetail;
	}

	public int getAttCount() {
		return AttCount;
	}

	public void setAttCount(int attCount) {
		AttCount = attCount;
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRdt() {
		return rdt;
	}

	public void setRdt(String rdt) {
		this.rdt = rdt;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
    
}
