/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.SharedPrefsUtil;


/** 
 * Activity for displaying the notification setting view.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationSettingsActivity extends BaseActivity {
    private ImageView iv_message;
    private ImageView iv_mge,
                      iv_voice,
                      iv_vibrate;
    private boolean message_on = true;
    private boolean voice_on = true;
    private boolean vibrate_on = true;
                      
	@Override
	protected void initView(){
		// TODO Auto-generated method stub
		iv_message = (ImageView) findViewById(R.id.iv_message);
		iv_message.setVisibility(View.GONE);
		iv_mge = (ImageView) findViewById(R.id.iv_mage);
		iv_voice = (ImageView) findViewById(R.id.iv_voice);
		iv_vibrate = (ImageView) findViewById(R.id.iv_vibrate);
		setSwitch(message_on,iv_mge);
		setSwitch(voice_on,iv_voice);
		setSwitch(vibrate_on,iv_vibrate);
		
	}
    public void setSwitch(boolean isOn,ImageView iv){
    	if(isOn){
    		iv.setImageResource(R.drawable.bg_settings_drag_on);
    	}else{
    		iv.setImageResource(R.drawable.bg_settings_drag_off);
    	}
    }
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		this.setBackListener("消息设置",true);
		iv_mge.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(message_on){
					message_on = false;
					SharedPrefsUtil.putMessageSet(NotificationSettingsActivity.this,Constants.SETTINGS_NOTIFICATION_ENABLED, message_on);
				   iv_mge.setImageResource(R.drawable.bg_settings_drag_off);
				   if(null !=Constants.xmppManager&&Constants.xmppManager.getConnection().isConnected()){
						Constants.xmppManager.disconnect();
				   }
				
					
					
				}else{
					message_on = true;
					SharedPrefsUtil.putMessageSet(NotificationSettingsActivity.this,Constants.SETTINGS_NOTIFICATION_ENABLED, message_on);
					iv_mge.setImageResource(R.drawable.bg_settings_drag_on);
					   if(null !=Constants.xmppManager&&!Constants.xmppManager.getConnection().isConnected()){
							Constants.xmppManager.connect();
					   }
	
				}
			}
			
		});
		iv_voice.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(voice_on){
					voice_on = false;
					SharedPrefsUtil.putMessageSet(NotificationSettingsActivity.this,Constants.SETTINGS_SOUND_ENABLED, voice_on);
				    iv_voice.setImageResource(R.drawable.bg_settings_drag_off);
				}else{
					voice_on = true;
					SharedPrefsUtil.putMessageSet(NotificationSettingsActivity.this,Constants.SETTINGS_SOUND_ENABLED, voice_on);
					iv_voice.setImageResource(R.drawable.bg_settings_drag_on);
				}
			}
			
		});
		iv_vibrate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(vibrate_on){
					vibrate_on = false;
					SharedPrefsUtil.putMessageSet(NotificationSettingsActivity.this,Constants.SETTINGS_VIBRATE_ENABLED, vibrate_on);
					iv_vibrate.setImageResource(R.drawable.bg_settings_drag_off);
				}else{
					vibrate_on = true;
					SharedPrefsUtil.putMessageSet(NotificationSettingsActivity.this,Constants.SETTINGS_VIBRATE_ENABLED, vibrate_on);
					iv_vibrate.setImageResource(R.drawable.bg_settings_drag_on);
				}
			}
			
		});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_message_item_set);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,this);
		message_on =  SharedPrefsUtil.getMessageSet(NotificationSettingsActivity.this, Constants.SETTINGS_NOTIFICATION_ENABLED);
		voice_on = SharedPrefsUtil.getMessageSet(NotificationSettingsActivity.this, Constants.SETTINGS_SOUND_ENABLED);
		vibrate_on = SharedPrefsUtil.getMessageSet(NotificationSettingsActivity.this, Constants.SETTINGS_VIBRATE_ENABLED);
	}

}
