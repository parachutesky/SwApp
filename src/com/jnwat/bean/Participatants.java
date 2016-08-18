package com.jnwat.bean;
/**
 * 学员  bean
 * @author wangzh   
 *
 */
public class Participatants {
    private String Name;  //姓名
    private String MobilePhone; //手机号
    private String Organization; //组织
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getMobilePhone() {
		return MobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		MobilePhone = mobilePhone;
	}
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
   
   
}
