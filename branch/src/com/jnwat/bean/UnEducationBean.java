/**
 * 
 */
package com.jnwat.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 赵荣林
 *
 * @category 非培训课程实体
 * 
 */
public class UnEducationBean {

	private String workId = ""; //非教学工作ID
	private  String  title = ""; // 显示标题
	private String type = "";  //工作类别
	private  String content  = "";  //工作内容
	private  String  startTime = ""; // 开始时间
	private  String  endTime = ""; // 结束时间
	private  String  coast = ""; // 花费时间
	private String  weekStart=""; //显示开始星期数
	private String  weekEnd=""; //显示结束星期数
	
	public  List<Map<String,Object>> list  = new  ArrayList<Map<String,Object>>();
	
	
	
//	public List<Map<String, Object>> getList() {
//		return list;
//	}
//
//	public void setList(List<Map<String, Object>> list) {
//		this.list = list;
//	}

	
	public String getTitle() {
		return title;
	}

	

	public String getWeekStart() {
		return weekStart;
	}



	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}



	public String getWeekEnd() {
		return weekEnd;
	}



	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}



	public void setTitle(String title) {
		this.title = title;
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

	public String getCoast() {
		return coast;
	}

	public void setCoast(String coast) {
		this.coast = coast;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
