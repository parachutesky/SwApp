package com.jnwat.view;

import android.widget.ImageView;

import com.jnwat.swmobilegy.R;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 2015年9月23日 10:36:32 控制消息状态的类
 */
public class ContralInfoState {

	/**
	 * 控制通知图标
	 */
	public void ContralInfoSJtate(ImageView iv ,boolean isHadInfo) {
		if(null!=iv){//不等于null
		if(isHadInfo){//有消息
			iv.setImageResource(R.drawable.nav_button_message_new);
			
		}else{
			
			iv.setImageResource(R.drawable.nav_button_message);
		}
		
	
	}else{
		LogUtils.d("消息状态IV没有注册,null");
	}
}
	
	
	
	
	
	
	
	
	
}