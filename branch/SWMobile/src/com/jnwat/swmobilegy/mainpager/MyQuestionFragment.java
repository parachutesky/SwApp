package com.jnwat.swmobilegy.mainpager;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.swmobilegy.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyQuestionFragment  extends Fragment  {
	private View view;
	private Activity mContext;
	private PullToRefreshListView list;
	
	static MyQuestionFragment newInstance(String s) {
		MyQuestionFragment newFragment = new MyQuestionFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		return newFragment;

	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_listview_overprocess, null);
		//、intView();
		//initData();
		//initListen();
		}
		
		ViewGroup parent = (ViewGroup) view.getParent();
	/*	if(BUserlogin.loginStatus==1&&BIntentObj.IsGetOverProcess==false){
			getWebData();
		}
		if(BUserlogin.loginStatus==0){
			clearData();
		}*/
		if (parent != null) {
			parent.removeView(view);
		}
		return view;

	
}
}
