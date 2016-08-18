package com.jnwat.personDetail;



import com.jnwat.swmobilegy.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public abstract class ChooseDialog extends Dialog {
    private TextView tv_localFile,tv_takePhoto,tv_cancel,tv_title;
	public ChooseDialog(Context context) {
		super(context,R.style.Dialog);
		// TODO Auto-generated constructor stub
		super.setContentView(R.layout.layout_dialog);
		initView();
		initListen();
	}
	public void initView(){
		tv_localFile = (TextView) findViewById(R.id.tv_localFile);
		tv_takePhoto = (TextView) findViewById(R.id.tv_takePhoto);
		tv_cancel    = (TextView) findViewById(R.id.tv_cancel);
		this.setTitle("ѡ���ȡͼƬ��ʽ");
		
		
	}
	public void initListen(){
		tv_localFile.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				takepicture();
				dismiss();
			}
			
		});
		tv_takePhoto.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				takephoto();
				dismiss();
			}
			
		});
		tv_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
    public abstract  void takephoto(); //�����
    public abstract  void takepicture();//�ļ�ѡ����
}
