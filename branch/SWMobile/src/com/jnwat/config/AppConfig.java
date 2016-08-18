package com.jnwat.config;

import android.content.Context;

/**
 * APP配置信息类
 * 
 * @author chang-zhiyuan
 * 
 */
public class AppConfig {

	/**
	 * 服务器IP地址 http://192.168.3.206:
	 */

	public static String getIp(Context mContext) {
		return com.jnwat.tools.SharedPrefsUtil.getSETTINGIP(mContext);
	}

	// private static String IP = "192.168.3.64";
	/**
	 * 端口
	 */
	// private static String WEBPROT = "8084";
//	public static String WEBPROT = "30139";
	public static String WEBPROT_OA= "8015";
    public  static String  WEBPROT = "8006";

	/**
	 * SW 服务空间
	 */
	public static String WEBSERVICE_SW =   "SysService.svc/";
	
	/**
	 * OA 服务空间
	 */
	public static String WEBSERVICE_OA =   "WebService/MOA.asmx/";
	/**
	 * SW网络地址
	 */
	public static String WEB_IP_PROT = WEBPROT + "/" + WEBSERVICE_SW;
	/**
	 * OA网络地址
	 */
	
   /* public static String OA_IP = "http://192.168.3.209:";
	public static String WEB_IP_PROT_OA = OA_IP +WEBPROT_OA + "/" + WEBSERVICE_OA;*/

	/**
	 * 登录方法
	 */
	public static String METHOAD_GETUSERINFO_TEACHER = WEB_IP_PROT
			+ "LoginTeacher";// "GetUserInfo";//
	public static String METHOAD_GETUSERINFO_STUDENT = WEB_IP_PROT
			+ "LoginStudent";// "GetUserInfo";//
	// 课表查询接口
	public static String METHOAD_SEARCHSCHEDULE = WEB_IP_PROT + "SearchSchedule";//
	// 我的课表查询接口
	public static String METHOAD_SEARCHSCHEDULEBYPROJECT = WEB_IP_PROT + "SearchScheduleByProject";//
	// 课表详情查询
	public static String METHOAD_SEARCHSCHEDULEDETAIL = WEB_IP_PROT + "SearchScheduleDetail";//
	
	// 项目查询 所有项目
	public static String METHOAD_TRAININGCOURSE = WEB_IP_PROT + "TrainingCourse";//
	// 项目查询 所有项目 项目详情
	public static String METHOAD_TRAININGCOURSEDETAIL= WEB_IP_PROT + "TrainingCourseDetail";//
	
	// 项目查询 我的项目
	public static String METHOAD_OWNTRAININGCOURSE= WEB_IP_PROT + "OwnTrainingCourse";//
	
	
	/**
	 * 非教学工作列表
	 * 
	 */
	public static String METHOAD_UNEDUCATION_WORK_LIST = WEB_IP_PROT
			+ "Unteaching";
	
	/**
	 * 非教学工作详情
	 * 
	 */
	public static String METHOAD_UNEDUCATION_WORK_DETAIL = WEB_IP_PROT
			+ "UnteachingDetail";
	/**
	 *  培训项目统计
	 */
	public static String METHOAD_TRAINING_PROGRAM_STATICS_LIST = WEB_IP_PROT
			+ "TrainItemCount";
	/**
	 * 
	 * 培训项目列表
	 * 
	 */
	public static String METHOAD_TRAINING_PROGRAM_LIST = WEB_IP_PROT
			+ "TrainingCourse";
	
	/**
	 * 培训项目详情
	 * 
	 */
	public static String METHOAD_TRAINING_PROGRAM_DETAIL = WEB_IP_PROT
			+ "TrainingCourseDetail";

	/**
	 * 薪资查询列表
	 * 
	 */
	public static String METHOAD_SALARY_LIST = WEB_IP_PROT
			+ "ArticleWagesYear";
	
	/**
	 * 薪资查询详细
	 * 
	 */
	public static String METHOAD_SALARY_DETAIL = WEB_IP_PROT
			+ "ArticleWages";
	
	// 获取用户的邮件ID
	public static String METHOAD_GETUSEREMAILID = WEB_IP_PROT
			+ "GetUserEmailID";//

	// 获取指定条数的通知
	public static String METHOAD_GETNOTICE = WEB_IP_PROT + "GetNotice";//

	// 获取任务细节
	public static String METHOAD_GETTASKDETAIL = WEB_IP_PROT + "GetTaskDetail";//
	// 获取任务的细节文件
	public static String METHOAD_GETTASKDETAILFIELD = WEB_IP_PROT
			+ "GetTaskDetailField";//
	// 获取任务的文件
	public static String METHOAD_GETTASKFIELD = WEB_IP_PROT + "GetTaskField";//
	// 获取任务的附件
	public static String METHOAD_GETTASKATTACHMENTS = WEB_IP_PROT
			+ "GetTaskAttachments";//
	// 审批接口
	public static String METHOAD_TASKAUDITING = WEB_IP_PROT + "TaskAuditing";//
    //获取学员名单
	public static String METHOAD_GETPARTICIPATANTS = WEB_IP_PROT + "StudentsList";
	// 获取通讯录信息
	public static String METHOAD_GETCONTACTS = WEB_IP_PROT + "GetContacts";

    //获取老师通讯录
	public static String METHOAD_GETTEACHERCONTACTS = WEB_IP_PROT + "AddressBook";
//	// 获取版本信息
//	public static String METHOAD_GETAPPVERSONINFO = WEB_IP_PROT
//			+ "GetAppVersonInfo";

	// 获取新闻接口
	public static String METHOD_GETNEWS = WEB_IP_PROT + "GetNews";
	// 选择接收人
	public static String METHOD_GETCURNODEBYWORKID = WEB_IP_PROT
			+ "GetTeacherMessage";
	// 个人档案的获取
	public static String METHOD_GETPERSONALDETAIL = WEB_IP_PROT
			+ "GetTeacherMessage";
	//更新个人档案
	public static String METHOD_UPDAPERSONALDETAIL = WEB_IP_PROT
			+ "UpdateTeacherMessage";
	/**
	 * APP状态 控制system和log的输出 true 为debug模式有打印 false 为上线模式无打印
	 */
	public static Boolean ISDEBUG = true;
	
	
	/*
	 * 版本信息
	 */
	public static String METHOAD_GETAPPVERSONINFO = WEB_IP_PROT
			+ "APKVesion";
	
	// 获取任务列表                                                                                                                                                                     
	public static String METHOAD_GETUSERTASKS_OA = WEB_IP_PROT + "GetUserTasks";//
	//归档
    public static String METHOAD_FLOWEND_OA= WEB_IP_PROT+"FlowEnd";
	// 获取任务信息
	public static String METHOAD_GETTASKINFO_OA = WEB_IP_PROT + "GetWorkInfo_All";//
	// 审批接口
 	public static String METHOAD_TASKAUDITING_OA = WEB_IP_PROT + "TaskAuditing";//
	// 获取 可发起流程的信息
    public static String METHOAD_GETSENDPROCESS_OA = WEB_IP_PROT
		+ "DB_GenerCanStartFlowsOfDataTable";
	// 办结流程列表接口
	public static String METHOAD_FLOWCOMPLETEGROUP_OA = WEB_IP_PROT
			+ "DB_FlowCompleteGroup";
	
	// 已办流程列表接口
	public static String METHOAD_GETUSERYIBAN_OA = WEB_IP_PROT
		+ "GetUserYiban";
	
	// 在途流程列表接口
	public static String METHOAD_GETUSERZAITU_OA = WEB_IP_PROT
			+ "GetUserZaitu";
	
	// 获得已完成数据统计列表点击后的详情
	public static String METHOD_DB_FLOWCOMPLETEDTL_OA = WEB_IP_PROT
			+ "DB_FlowCompleteDtl";
}
