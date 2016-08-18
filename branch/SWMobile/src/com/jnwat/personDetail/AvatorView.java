package com.jnwat.personDetail;

import java.io.File;

import com.jnwat.swmobilegy.R;
import com.lidroid.xutils.BitmapUtils;

import com.xycoding.www.ImageUtils;
import com.xycoding.www.ZoomImageView;






import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class AvatorView extends Activity{
    private ZoomImageView iv_head;
  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_avatorview);
		initView();
		getLocalHead();
	}
	public void initView(){
		
		iv_head = (ZoomImageView) findViewById(R.id.iv_head);
	}
	
	public void getLocalHead(){
		Intent intent =this.getIntent();
		String path =intent.getStringExtra("data");
		BitmapUtils bitmapUtils = new BitmapUtils(this);
		if(iv_head!=null){
		//   bitmapUtils.display(iv_head, path);
		   File file =bitmapUtils.getBitmapFileFromDiskCache(path);
		   if(file!=null){
			  
			   Bitmap bmp =ImageUtils.getSmallBitmap(file.getAbsolutePath());
			   iv_head.setImageBitmap(bmp);
		   }else{
			   Bitmap bmp =BitmapFactory.decodeResource(getResources(), R.drawable.detail_icon_user_default);
				if(iv_head!=null){
					iv_head.setImageBitmap(bmp);
				}
		   }
		}
		
		
	}
	 
}
