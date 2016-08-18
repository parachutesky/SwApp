package com.jnwat.swmobilegy.set;

import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.MainActivity;
import com.jnwat.tools.ModifySysTitle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;

public class MessageSet extends PreferenceActivity {
   private ImageView iv_reback;
   private CheckBoxPreference ck_message_set;
   SwitchPreference a;
   PreferenceScreen sf;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		setContentView(R.layout.activity_message_set);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				MessageSet.this);
		initView();
	//	initListen();
	}
	
    public void initView(){
    	iv_reback =(ImageView) findViewById(R.id.iv_reback);
    	ck_message_set = (CheckBoxPreference) this.findPreference("ck_message_set");
    	/*a = (SwitchPreference) this.findPreference("ccc");
    	a.setLayoutResource(R.layout.preference);*/
    }
    public void initListen(){
    	iv_reback.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
    		
    	}
    	);
    	
    	
    }
}
