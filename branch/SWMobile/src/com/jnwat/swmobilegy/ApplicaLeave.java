package com.jnwat.swmobilegy;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ModifySysTitle;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicaLeave extends FragmentActivity{
    private TextView title_bar_name;
	private ImageView iv_back ,iv_message;
	private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Button edt_start_Time,edt_end_time;
    private final int STARTTIME = 1;
    private final int ENDTIME = 2;
    private int whichWidget = 0;
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            /*Toast.makeText(ApplicaLeave.this,
                    mFormatter.format(date), Toast.LENGTH_SHORT).show();*/
            if(whichWidget==STARTTIME){
            	edt_start_Time.setText(mFormatter.format(date));
            }else if(whichWidget ==ENDTIME){
            	edt_end_time.setText(mFormatter.format(date));
            }
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
        	whichWidget = 0;
          /*  Toast.makeText(ApplicaLeave.this,
                    "Canceled", Toast.LENGTH_SHORT).show();*/
        }
    };

	protected void setView() {
		// TODO Auto-generated method stub
	  setContentView(R.layout.activity_applica_leave);
	  title_bar_name =(TextView) findViewById(R.id.title_bar_name);
	  title_bar_name.setText("请假申请");
	  
	  ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
			  ApplicaLeave.this);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setView();
		initView();
		initListen();
	}
   public void initView(){
	   edt_start_Time =  (Button) findViewById(R.id.edt_start_time);
	   edt_end_time   = (Button) findViewById(R.id.edt_end_time);
	   iv_back = (ImageView) findViewById(R.id.iv_back);
	   iv_message = (ImageView) findViewById(R.id.iv_message);
	   iv_message.setVisibility(View.GONE);
   }
	
   public void initListen(){
	   iv_back.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
		   
	   });
	   edt_start_Time.setOnClickListener(new OnClickListener(){
       
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			whichWidget = STARTTIME;
			new SlideDateTimePicker.Builder(getSupportFragmentManager())
            .setListener(listener)
            .setInitialDate(new Date())
           
            //.setMinDate(minDate)
            //.setMaxDate(maxDate)
            //.setIs24HourTime(true)
            //.setTheme(SlideDateTimePicker.HOLO_DARK)
            //.setIndicatorColor(Color.parseColor("#990000"))
            .build()
            .show();
		}
		   
	   });
	   edt_end_time.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 whichWidget = ENDTIME;
			new SlideDateTimePicker.Builder(getSupportFragmentManager())
            .setListener(listener)
            .setInitialDate(new Date())
            //.setMinDate(minDate)
            //.setMaxDate(maxDate)
            //.setIs24HourTime(true)
            //.setTheme(SlideDateTimePicker.HOLO_DARK)
            //.setIndicatorColor(Color.parseColor("#990000"))
            .build()
            .show();
		} 
	   });
	   
	   
   }
}
