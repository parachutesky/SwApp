package com.jnwat.swmobilegy.set;

import com.jnwat.bean.BUserlogin;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.MainActivity;
import com.jnwat.tools.ModifySysTitle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalDataActivity extends Activity{
   private ImageView rebackImg;
   private TextView tv_username , tv_department, tv_offTel ,tv_mobphone ,tv_Email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_personal);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				PersonalDataActivity.this);
		initView();
		initListen();
		initData();
	}
    public void initView(){
    	rebackImg =(ImageView) this.findViewById(R.id.reback_imb_pdt);
    	tv_username = (TextView) this.findViewById(R.id.name_txt);
    	tv_department = (TextView) this.findViewById(R.id.department_txt);
    	tv_offTel= (TextView) this.findViewById(R.id.offphone_txt);
    	tv_mobphone =(TextView) this.findViewById(R.id.mobphone_txt);
    	tv_Email    = (TextView) this.findViewById(R.id.email_txt);
    }
    public void initListen(){
    	rebackImg.setOnClickListener(new OnClickListener(){
    		

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
    	});
    	
    }
    public void initData(){
/*    	tv_username.setText(BUserlogin.NAME);
    	tv_department.setText(BUserlogin.FK_DEPT);
    	tv_offTel.setText(BUserlogin.TEL);
    	tv_mobphone.setText(BUserlogin.TEL1);
    	tv_Email.setText(BUserlogin.EMAIL);*/
    	
    }
}
