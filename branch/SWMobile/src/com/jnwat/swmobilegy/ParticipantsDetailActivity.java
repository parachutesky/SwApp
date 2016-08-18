package com.jnwat.swmobilegy;

import java.util.regex.Pattern;

import com.jnwat.bean.Participatants;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ParticipantsDetailActivity extends BaseActivity{
    private TextView tv_name,
                     tv_department,
                     tv_mobphone;
    private ImageView iv_tel_tel;
    private  Participatants participatants = new Participatants();
                     
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_department = (TextView) findViewById(R.id.tv_department);
		tv_mobphone = (TextView) findViewById(R.id.tv_mobphone);
		iv_tel_tel = (ImageView) findViewById(R.id.iv_tel_tel);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		iv_tel_tel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tel(participatants.getMobilePhone());
			}
			
		});
		
	}
	// 打电话
		public void  tel(String telNUm){
			
			String regx  = "[0-9]+";
			if(null!=telNUm&&Pattern.matches(regx, telNUm)){
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL"); 
				intent.setData(Uri.parse("tel:"+telNUm));
				startActivity(intent);
				
			}else{
				ToastViewTools.initToast(this,"号码不存在或有误");
			}
			
			
		}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
		Intent intent = getIntent();
		participatants.setName(intent.getStringExtra("Name"));
		participatants.setMobilePhone(intent.getStringExtra("MobilePhone"));
		participatants.setOrganization(intent.getStringExtra("Organization"));
		tv_name.setText(participatants.getName());
		tv_department.setText(participatants.getOrganization());
		tv_mobphone.setText(participatants.getMobilePhone());
		
		
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_participants_detail);
		this.setBackListener("详情",true);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark, this);
	}

}
