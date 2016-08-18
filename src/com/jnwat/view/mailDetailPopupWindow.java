package com.jnwat.view;

import java.util.List;

import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.mail.MailDetalActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

public class mailDetailPopupWindow extends PopupWindow{
    private List<String> telNUm ;
    private View mView;
    private ListView lv_tel;
    private MailDetalActivity mContext;
    public  mailDetailPopupWindow(MailDetalActivity context,List<String> array){
    	telNUm = array;
    	mContext = context;
    	LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  	    mView = mInflater.inflate(R.layout.popup_maildetail,null);
  	    lv_tel  = (ListView) mView.findViewById(R.id.lv_telNum);
  	    this.setContentView(mView);
  	    final ArrayAdapter adapter = new ArrayAdapter(mContext,android.R.layout.simple_list_item_1, telNUm);
  	    this.setWidth(android.app.ActionBar.LayoutParams.FILL_PARENT);
	    this.setHeight(LayoutParams.WRAP_CONTENT);
	    this.setFocusable(true);
	    this.setOutsideTouchable(true);
	    Drawable dw =mContext.getResources().getDrawable(R.color.mailDetail_gray);
	    this.setBackgroundDrawable(dw); 
	    WindowManager.LayoutParams lp=context.getWindow().getAttributes();
	    lp.alpha = 0.7f;
	    Window c =  context.getWindow();
	    c.setAttributes(lp);
	    lv_tel.setAdapter(adapter);
	    this.setOnDismissListener(new OnDismissListener(){
		    
	     	  //消失时恢复原来的背景色
	     	   public void onDismiss(){
	     			 WindowManager.LayoutParams lp=((Activity)mContext).getWindow().getAttributes();
	     			 lp.alpha = 1f;
	     			 ((Activity)mContext).getWindow().setAttributes(lp);	
	     		}			
	     	 });
  	    lv_tel.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view,
					int position, long l) {
				// TODO Auto-generated method stub
			    String tel = (String) adapter.getItem(position);
				/*if(mContext.ACTION ==  mContext.TEL){
					if (tel.matches("[0-9]+")) {
						
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_CALL);
							intent.setData(Uri.parse("tel:" + tel));
							mContext.startActivity(intent);
					}
				}else if(mContext.ACTION == mContext.SEND){
					    
					Intent intent2 = new Intent();
					intent2.setAction(Intent.ACTION_SENDTO);
					intent2.setData(Uri.parse("smsto:" + tel));
					mContext.startActivity(intent2);
				}*/
			}
  	    });
  	 
  	    
  	    
  	    
    }
    
    
}
