package com.jnwat.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 头部有新咨询的Toast提示
 * */
/**
 * @author chang-zhiyuan
 * 
 */
public class ToastViewTools {
	
	
	public  static String TOAST_UPDATA = "正在下载最新版本!"; 
	   private static Toast toast = null;
	
	
	/**
	 * 初始化Toast提示，并show,显示图片
	 * */
	public static void initToast(Context context, String top_hint, int img_res) {
		Toast topToast = Toast.makeText(context, top_hint, Toast.LENGTH_SHORT);
		topToast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
		LinearLayout ll_bg = (LinearLayout) topToast.getView();
		ImageView img_bg = new ImageView(context);
		img_bg.setImageResource(img_res);
		ll_bg.addView(img_bg, 0);
		topToast.show();
	}

	/**
	 * @param context
	 *            上下文对象
	 * @param center_hint
	 *            显示的内容
	 */
	public static void initToast(Context context, String top_center) {
		   if (toast == null) {
	            toast = Toast.makeText(context, top_center, Toast.LENGTH_SHORT);
	        } else {
	            toast.setText(top_center);
	        }
	        toast.show();
	        
	        
	/*	Toast topToast = Toast.makeText(context, top_center, Toast.LENGTH_SHORT);
		if(topToast!=null){
			topToast.setText(top_center);
		}
		topToast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
		topToast.show();*/
		
		
		
		
	}
}
