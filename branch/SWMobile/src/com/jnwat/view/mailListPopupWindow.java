package com.jnwat.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jnwat.bean.Contacts;
import com.jnwat.bean.Contacts2;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.mail.CharacterParser;
import com.jnwat.swmobilegy.mail.ContactAdapter;
import com.jnwat.swmobilegy.mail.MailDetalActivity;

/**
 * 通讯录的popupWindow 主要为了  查询的方便
 * @author wangzhen
 *
 */
public class mailListPopupWindow extends PopupWindow implements OnClickListener{
    
    List<Contacts2>  userList = new ArrayList<Contacts2>();//获取 所有的 通讯录数据
    List<Contacts2>  showList = new ArrayList<Contacts2>();//临时存放 通讯录的数据   为了查询的显示使用
    Activity mContext; 
    View mView,v,mLca;
    ListView listView;
    TextView tipTxt,fillTxt;
    ContactAdapter adapter; // 适配器
    InputMethodManager imm;
    AutoCompleteTextView search_popupAtxt; //查询内容输入的控件
    LinearLayout layout;
    boolean hasFocus;
   /* mailListPopupWindow mPop;
    int mScreenWidth;
    int mScreenHeight;
    int titleHeight;
    int fillHeight;*/
    public mailListPopupWindow(Activity context,List<Contacts2> list,View layout){
    	super(context);
    	userList = list;
    	mContext = context;
    	mLca  = layout;
    //	 mPop  = this;
    	LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  	    mView = mInflater.inflate(R.layout.mail_popup,null);
  	    this.setContentView(mView);
  	    this.setWidth(LayoutParams.FILL_PARENT);
	    this.setHeight(LayoutParams.FILL_PARENT);
	    this.setFocusable(true);
	    this.setOutsideTouchable(true);
	    ColorDrawable cd = new ColorDrawable(0x000000);
	    this.setBackgroundDrawable(cd); 
	    WindowManager.LayoutParams lp=context.getWindow().getAttributes();
	    lp.alpha = 0.7f;
	    Window c =  context.getWindow();
	    c.setAttributes(lp);
	 /*  WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

	    mScreenWidth = wm.getDefaultDisplay().getWidth();
	    mScreenHeight = wm.getDefaultDisplay().getHeight();
	    
	    View mv =  mInflater.inflate(R.layout.layout_title, null);*/
	   
	   /* titleHeight = mv.getHeight();
	    int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mv.measure(w, h);
        int height = mv.getMeasuredHeight();
        int width = mv.getMeasuredWidth();*/
	    this.setOnDismissListener(new OnDismissListener(){
	    
   	  //消失时恢复原来的背景色
   	   public void onDismiss(){
   			 WindowManager.LayoutParams lp=((Activity)mContext).getWindow().getAttributes();
   			 lp.alpha = 1f;
   			 ((Activity)mContext).getWindow().setAttributes(lp);	
   		}			
   	 });
	    initView();
	    initData();
	    initListen();
    }
    
    

	public void initView(){
    	listView = (ListView) mView.findViewById(R.id.mailist_list);
    	
    	search_popupAtxt = (AutoCompleteTextView) mView.findViewById(R.id.search_popup);
    	
    	tipTxt    = (TextView) mView.findViewById(R.id.searchTip);
    	v =mView.findViewById(R.id.Layout);
    	layout = (LinearLayout) mView.findViewById(R.id.Layout);
    	fillTxt = (TextView) mView.findViewById(R.id.fill_txt);
    	hasFocus = true;
    }
    public void initData(){
   	    adapter = new ContactAdapter(mContext,showList);  
    	listView.setAdapter(adapter);
    	
    }
    public void initListen(){
    	mView.setOnClickListener(this);
    	search_popupAtxt.addTextChangedListener(new searchLis ());
    	//弹出软软键盘
    	listView.setOnItemClickListener(new listItemOnClick());
    	search_popupAtxt.setOnKeyListener(new OnKeyListener(){

    		@Override
    		public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
    			// TODO Auto-generated method stub
    			 if (keyCode == KeyEvent.KEYCODE_BACK&&showList.size()==0){
    		            dismiss();
    		    }
    			return false;
    		}
    		 
    	 });
    }
   
	class searchLis implements TextWatcher{

    	@Override
    	public void afterTextChanged(Editable arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void beforeTextChanged(CharSequence content, int arg1, int arg2,
    			int arg3) {
    		// TODO Auto-generated method stub
    		
    		
    	}

    	@Override
    	public void onTextChanged(CharSequence content, int arg1, int arg2, int arg3) {
    		// TODO Auto-generated method stub
    		tipTxt.setVisibility(View.GONE);
    		filterData(content.toString()); //根据输入的字符 进行过滤   数据
    		
    	}		
    	}
    //过滤查询数据
       @SuppressLint("NewApi")
	public void filterData(String str){
    	   
    	   str = str.toUpperCase(); // 这里主要是将输入的 英文字母 转变为大写
    	   showList.clear();        // 将本来得到的数据  清楚掉
	   	   if(TextUtils.isEmpty(str)){
	   		  v.setBackground(null);
	   		 
	   		}else{
	   			for(Contacts2 user:userList){
	   				String name="";
	   				if(user.getName()!=null){
	   				    name = user.getName().toUpperCase(); //
	   				}
	   			    String mobphone =user.getMobphone();
	   			    String pingyin="";
		   			if(user.getPinyin1()!=null){
		   				pingyin = user.getPinyin1().toUpperCase();
		   			}
		   			String shortPinyin = "";
		   			if(user.getPinyin2()!=null){
	   			       shortPinyin = user.getPinyin2().toUpperCase();
		   			}
	   				if(name.indexOf(str)!=-1||pingyin.startsWith(str)||shortPinyin.startsWith(str)||pingyin.equals("")&&CharacterParser.getInstance().getSelling(name).toUpperCase().startsWith(str)||mobphone.indexOf(str)!=-1||
	   						mobphone.startsWith(str)||shortPinyin.equals("")&&CharacterParser.getInstance().getSimpleLetter(name).toUpperCase().startsWith(str) ){
	   					showList.add(user);
	   				}
	   				
	   			}
	   			
	   			if(showList.size()>0){
	   				this.setWindowLayoutMode(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	   			}else{
	   				this.setWindowLayoutMode(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	   			    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);   
	                imm.hideSoftInputFromWindow(search_popupAtxt.getWindowToken(),0);   
	   			}
	   			
	   		 if(showList.size()==0){
			   		tipTxt.setVisibility(View.VISIBLE);
			   	   }else{
			   		  tipTxt.setVisibility(View.GONE);
			   		
			   		  int color = mContext.getResources().getColor(R.color.windows_color_maillist);
			   		  v.setBackgroundColor(color);
					   		
					   		
			   	   }
	   		}
	       
	     	adapter.updateListView(showList);
       }
       /**
   	 * listView的监听事件
   	 */
   	class listItemOnClick   implements OnItemClickListener{

   		@Override
   		public void onItemClick(AdapterView<?> arg0, View v, int position,
   				long arg3) {
   			// TODO Auto-generated method stub
   			   Contacts2 user =showList.get(position);
   			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("name", user.getName());
			
			bundle.putString("department", user.getDepartment());
			bundle.putString("offphone", user.getOffphone());
			bundle.putString("mobphone", user.getMobphone());
			bundle.putString("email", user.getEmail());
			bundle.putString("headurl", user.getHeadurl());
			intent.putExtras(bundle);
			intent.setClass(mContext, MailDetalActivity.class);
			mContext.startActivity(intent);
   			
   		}
   		
   	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id){
		case R.id.search_popup:
			break;
	    default:
	    	if(showList.size()==0&&search_popupAtxt.getText().toString().isEmpty()){
	    	    this.dismiss();
	    	}
	    	break;
		}
		
	}

	
}
