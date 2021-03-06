/**
 * 
 */
package com.jnwat.bean;

import java.util.List;

/**
 * @author zhaorl
 *
 * @category 搭班车实体
 * 
 * @version 1.0
 * 
 */
public class TempClassBean {

	  private String  Projectname;  //项目名称
	  private String Projecttype;  //项目类型
	  private String Code;         //期数
	  private String Startdate;   
	  private String Enddate;
	  private String Pronum;       //培训人数
	  private String Days;         //培训天数
	  private String Classroom;    //教室
	  private String Remark;       //备注
	  private String Client;       //联系人       
	  private List<AccomdationInfo>   Accommodation;
	  
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return super.toString();
	}
	
	
	public List<AccomdationInfo> getAccommodation() {
		return Accommodation;
	}


	public void setAccommodation(List<AccomdationInfo> accommodation) {
		Accommodation = accommodation;
	}


	public String getProjectname() {
		return Projectname;
	}
	public void setProjectname(String projectname) {
		Projectname = projectname;
	}
	public String getProjecttype() {
		return Projecttype;
	}
	public void setProjecttype(String projecttype) {
		Projecttype = projecttype;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getStartdate() {
		return Startdate;
	}
	public void setStartdate(String startdate) {
		Startdate = startdate;
	}
	public String getEnddate() {
		return Enddate;
	}
	public void setEnddate(String enddate) {
		Enddate = enddate;
	}
	public String getPronum() {
		return Pronum;
	}
	public void setPronum(String pronum) {
		Pronum = pronum;
	}
	public String getDays() {
		return Days;
	}
	public void setDays(String days) {
		Days = days;
	}
	public String getClassroom() {
		return Classroom;
	}
	public void setClassroom(String classroom) {
		Classroom = classroom;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getClient() {
		return Client;
	}
	public void setClient(String client) {
		Client = client;
	}
	  
	  
	  
}
