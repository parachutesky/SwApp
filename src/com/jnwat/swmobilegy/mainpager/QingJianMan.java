package com.jnwat.swmobilegy.mainpager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.workevent.WorkeventFragment;
import com.jnwat.tools.ModifySysTitle;

/**
 * @author chang-zhiyuan 
 * 工作流程
 */
public class QingJianMan extends FragmentActivity {
	WorkeventFragment mWorkeventFragment;
	private ImageView iv_back = null;
	private TextView title_bar_name = null;
	public FragmentManager childFm;

	private void setDefaultFragment() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		setListener();

		 iv_back = (ImageView) findViewById(R.id.iv_back);
			iv_back.setVisibility(View.VISIBLE);
		 iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		 });
		 
	}

	protected void initView() {
		// TODO Auto-generated method stub
		ModifySysTitle
				.ModifySysTitleStyle(R.color.title_blue, QingJianMan.this);
		setContentView(R.layout.activity_qingjiaguanli);
		childFm = getSupportFragmentManager();
		FragmentTransaction ft = childFm.beginTransaction();

		mWorkeventFragment = new WorkeventFragment();
		Bundle bundle = new Bundle();
		bundle.putString("key", "qingjiaman");
		mWorkeventFragment.setArguments(bundle);
		ft.add(mWorkeventFragment, "第一").commit();

	}

	protected void setListener() {
		// TODO Auto-generated method stub
		/**
		 * 设置返回Title的返回监听
		 * 
		 * @param view
		 */
		/*
		 * iv_back = (ImageView) findViewById(R.id.iv_back);
		 * iv_back.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View view) { // TODO Auto-generated
		 * method stub finish(); } }); title_bar_name = (TextView)
		 * findViewById(R.id.title_bar_name); title_bar_name.setText("请假管理");
		 */
	}

	protected void initData() {
		// TODO Auto-generated method stub

	}

	protected void setView() {
		// TODO Auto-generated method stub

	}

}
