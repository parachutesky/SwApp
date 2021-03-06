package com.jnwat.swmobilegy;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.bean.ApplicaInfo;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterLeaveManage;
import com.jnwat.tools.ModifySysTitle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaveManageActivity extends BaseActivity{
    private AdapterLeaveManage adapter;
    private TextView title_bar_name;
    private ImageView iv_message;
    private List<ApplicaInfo> Data = new ArrayList<ApplicaInfo>();
	private PullToRefreshListView  list;
    public void initView(){
    	
    	list = (PullToRefreshListView) findViewById(R.id.list_leaveManage);
    	iv_message = (ImageView) findViewById(R.id.iv_message);
    	iv_message.setVisibility(View.GONE);
    	/*title_bar_name = (TextView) findViewById(R.id.title_bar_name);
    	title_bar_name.setText("请假管理");*/
    }
   
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		this.setBackListener("请假管理", true);
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		localData();
		adapter  = new AdapterLeaveManage(this,Data);
		list.setAdapter(adapter);
		
	}
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_leave_manage);
		 ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
		    		LeaveManageActivity.this);
	}
    public void localData(){
    	String[] content = {"因小区燃气管道维修，家中需留人协调。因此请假半天望批准","到户籍所在地办理护照，路上需要两天。办理手续1天，因此请假3天。望批准.","到户籍所在地办理护照，路上需要两天，办理手续1天，因此请假3天。望批准","到户籍所在地办理护照，路上需要两天，办理手续1天，因此请假3天。望批准"};
    	String Time ="3小时前";
    	String adutor= "分管校长";
    	String status = "同意";
    	String proposer = "User";
    	for(int  i = 0;i < content.length;i ++){
    		ApplicaInfo applicaInfo = new ApplicaInfo();
    		applicaInfo.setContent(content[i].trim());
    		applicaInfo.setAdutior(adutor);
    		applicaInfo.setTime(Time);
    		applicaInfo.setProposer(proposer);
    		applicaInfo.setStatus(status);
    		Data.add(applicaInfo);
    	}
    	
    }
    public  String ToSBC(String input) { 
        char c[] = input.toCharArray(); 
        for (int i = 0; i < c.length; i++) { 
            if (c[i] == ' ') { 
                c[i] = '\u3000'; 
            } else if (c[i] < '\177') { 
                c[i] = (char) (c[i] + 65248); 
            } 
        } 
        return new String(c); 
    } 
} 
