package com.jnwat.swmobilegy.workevent;

import org.androidpn.client.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.swmobilegy.LoginActivity;
import com.jnwat.swmobilegy.MessageActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.view.PagerSlidingTabStrip;
import com.jnwat.tools.ToastViewTools;
import com.jnwat.view.WorkEventPopupWindow;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 发起流程
 */
public class WorkeventFragment extends Fragment {
	private View view;
	ViewPager viewPager;
	PagerSlidingTabStrip tabs;
	ImageView iv_menu;
	ImageView iv_back;
	public static ImageView iv_message;
	Animation animation;
	TextView tv_sercher;
	private static WorkeventFragment workeventfragment = null;
	WorkEventPopupWindow mPopup;
	Activity mContext;
	public String curFragmentTag = "";
	/**
	 * Tab标题
	 */
	private static final String[] TITLE = new String[] { "待办流程", "已办流程", "办结流程" };

	public static WorkeventFragment newInstance(String s) {
		if (null == workeventfragment) {
			workeventfragment = new WorkeventFragment();
		}
		Bundle bundle = new Bundle();
		// newFragment.setArguments(bundle);
		return workeventfragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_workevent, null);
			try {
				String string = getArguments().getString("key"); 
				iv_back.setVisibility(View.VISIBLE);
					
			} catch (Exception e) {
				// TODO: handle exception
			}
			mContext = this.getActivity();
		}
		// ViewPager的adapter
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		//
		// 消息的button
		iv_message = (ImageView) view.findViewById(R.id.iv_message);
		viewPager.setAdapter(new myPagerAdapter(getChildFragmentManager()));
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		tv_sercher = (TextView) view.findViewById(R.id.tv_sercher);
		tabs.setViewPager(viewPager);
		iv_menu = (ImageView) view.findViewById(R.id.iv_workevent_menu);
		iv_back = (ImageView) view.findViewById(R.id.iv_back);

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		iv_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				iv_menu.startAnimation(animation);
				ToastViewTools.initToast(mContext, "此功能正在研发中，敬请期待");
			}
		});
		iv_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (null != Constants.notificationManager) {
					Constants.notificationManager.cancel(0);
				}
				if (BUserlogin.loginStatus == 0) {
					Intent intent = new Intent();
					intent.setClass(mContext, LoginActivity.class);
					startActivity(intent);

				} else {
					Intent intent = new Intent();
					intent.setClass(mContext, MessageActivity.class);
					BIntentObj.IsGetNewPushMessage = false;
					setMessageStage();
					startActivity(intent);
				}

			}

		});
		// 设置消息图标
		setMessageStage();
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;

	}

	/**
	 * 设置图片的状态
	 */
	public void setMessageStage() {
		if (BIntentObj.IsGetNewPushMessage && null != iv_message) {
			iv_message.setImageResource(R.drawable.nav_button_message_new);
		} else {
			iv_message.setImageResource(R.drawable.nav_button_message);
			LogUtils.d("设置无消息");
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 如果登录状态为4，就显示
		if (BUserlogin.Flag.equals("4") && BUserlogin.loginStatus == 1
				&& null != iv_message) {
			iv_message.setVisibility(View.VISIBLE);

		} else {
			iv_message.setVisibility(View.INVISIBLE);

		}

	}

	class myPagerAdapter extends FragmentPagerAdapter {
		DoProcessFragment fragment1;
		// DoingFragment fragment2;
		HadDidFragment fragment3;
		EndFragment fragment4;

		public myPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (null == fragment1) {
					fragment1 = new DoProcessFragment();
				}

				return fragment1;
				/*
				 * case 1: if (null == fragment2) { fragment2 = new
				 * DoingFragment(); } return fragment2;
				 */
			case 1:
				if (null == fragment3) {
					fragment3 = new HadDidFragment();
				}

				return fragment3;
			case 2:
				if (null == fragment4) {
					fragment4 = new EndFragment();
				}

				return fragment4;
			default:
				return null;
			}

		}

		@Override
		public int getCount() {

			return TITLE.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[position];
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);



			
		animation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.menu_zoom_exit);
	}
}
