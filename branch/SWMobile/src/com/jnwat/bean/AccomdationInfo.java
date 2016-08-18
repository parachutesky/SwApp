package com.jnwat.bean;
/**
 * 住宿情况     项目明细中的  住宿情况 
 * @author wangzh
 *
 */
public class AccomdationInfo {

	private  String  Building ; //居住 楼号名
	private  String  Persons;  //居住人数
	public String getBuilding() {
		return Building;
	}
	public void setBuilding(String building) {
		Building = building;
	}
	public String getPersons() {
		return Persons;
	}
	public void setPersons(String persons) {
		Persons = persons;
	}
	
}
