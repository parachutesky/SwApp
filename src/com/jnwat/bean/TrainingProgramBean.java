/**
 * 
 */
package com.jnwat.bean;

/**
 * @author 赵荣林
 *
 * 项目类实体
 * 
 */
public class TrainingProgramBean {

	
	private String code;  //代码号
	private String proName;  //项目名
	private String proID;  //id
	private String trainType;  //类别
	private String startTime;  //开始时间
	private String endTime;  //结算时间
	private String peopleNum;  //人数
	
	public TrainingProgramBean(){
		
	}
	
	public TrainingProgramBean(String code,String proName,String proID,String trainType,
			String startTime,String endTime,String peopleNum){
		this.code  = code;
		this.proName  = proName;
		this.proID  = proID;
		this.trainType  = trainType;
		this.startTime  = startTime;
		this.endTime  = endTime;
		this.peopleNum  = peopleNum;
		
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProID() {
		return proID;
	}
	public void setProID(String proID) {
		this.proID = proID;
	}
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
	
	
}
