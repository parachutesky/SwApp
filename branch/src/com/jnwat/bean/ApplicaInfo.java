package com.jnwat.bean;

public class ApplicaInfo {
	   private String Content;
	   private String Time;
	   private String Proposer;//申请人
	   private String Adutior;//审核人
	   private String Status;//申请的状态
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getProposer() {
		return Proposer;
	}
	public void setProposer(String proposer) {
		Proposer = proposer;
	}
	public String getAdutior() {
		return Adutior;
	}
	public void setAdutior(String adutior) {
		Adutior = adutior;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
   
}
