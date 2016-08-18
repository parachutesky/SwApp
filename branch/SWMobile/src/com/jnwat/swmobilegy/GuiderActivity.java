package com.jnwat.swmobilegy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.jnwat.swmobilegy.R;
import com.jnwat.tools.SharedPrefsUtil;
import com.jnwat.view.ColorAnimationView;

public class GuiderActivity extends FragmentActivity {
	private static final int[] resource = new int[] { R.drawable.welcome1,
			R.drawable.welcome4, R.drawable.welcome3, R.drawable.welcome4 };
	private static final String TAG = GuiderActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_guider);
		MyFragmentStatePager adpter = new MyFragmentStatePager(
				getSupportFragmentManager());
		ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adpter);
		/**
		 * 首先，你必须在 设置 Viewpager的 adapter 之后在调用这个方法 第二点，setmViewPager(ViewPager
		 * mViewPager,Object obj, int count, int... colors) 第一个参数 是 你需要传人的
		 * viewpager 第二个参数 是
		 * 一个实现了ColorAnimationView.OnPageChangeListener接口的Object,用来实现回调 第三个参数 是
		 * viewpager 的 孩子数量 第四个参数 int... colors ，你需要设置的颜色变化值~~ 如何你传人
		 * 空，那么触发默认设置的颜色动画
		 * */
		/**
		 * Frist: You need call this method after you set the Viewpager adpter;
		 * Second: setmViewPager(ViewPager mViewPager,Object obj， int count,
		 * int... colors) so,you can set any length colors to make the animation
		 * more cool! Third: If you call this method like below, make the colors
		 * no data, it will create a change color by default.
		 * */
		colorAnimationView.setmViewPager(viewPager, resource.length);
		colorAnimationView
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
						// Log.e("TAG", "onPageScrolled");
					}

					@Override
					public void onPageSelected(int position) {
						// Log.e("TAG", "onPageSelected");
					}

					@Override
					public void onPageScrollStateChanged(int state) {
						// Log.e("TAG", "onPageScrollStateChanged");
					}
				});
		// Four : Also ,you can call this method like this:
		// colorAnimationView.setmViewPager(viewPager,this,resource.length,0xffFF8080,0xff8080FF,0xffffffff,0xff80ff80);
	}

	public class MyFragmentStatePager extends FragmentStatePagerAdapter {

		public MyFragmentStatePager(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// Log.d(TAG, position+"");
			if (position == 3) {
				return new GurderEndFragment(position);
			}
			return new GurderFragment(position);
		}

		@Override
		public int getCount() {
			return resource.length;
		}
	}

	@SuppressLint("ValidFragment")
	public class GurderFragment extends Fragment {

		private int position;

		public GurderFragment(int position) {
			this.position = position;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// View view = inflater.inflate(R.layout.guider_viewpager,
			// container, false);
			// view..setImageResource(resource[position]);
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(resource[position]);
			return imageView;

		}
	}

	@SuppressLint("ValidFragment")
	public class GurderEndFragment extends Fragment {

		private int position;

		public GurderEndFragment(int position) {
			this.position = position;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View view = inflater.inflate(R.layout.guider_viewpager, container,
					false);

			// LinearLayout gurder_linlayout
			// =(LinearLayout)view.findViewById(R.id.gurder_linlayout);
			// view..setImageResource(resource[position]);
			ImageView imageview_guider = (ImageView) view
					.findViewById(R.id.imageview_gurder);
			Button imageview_guider_start = (Button) view
					.findViewById(R.id.imageview_gurder_start);
			imageview_guider.setImageResource(resource[position]);
			imageview_guider_start.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// ToastViewTools.initToast(GuiderActivity.this,
					// "imageview_guider_start");
					// 数据是使用Intent返回
					SharedPrefsUtil.putGurder_Once(GuiderActivity.this, true);
					Intent intent = new Intent();
					// 把返回数据存入Intent
					intent.putExtra("result", "1");
					// 设置返回数据
					GuiderActivity.this.setResult(RESULT_OK, intent);
					// 关闭Activity
					GuiderActivity.this.finish();
				}
			});

			return view;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			// 把返回数据存入Intent
			intent.putExtra("result", "2");
			// 设置返回数据
			GuiderActivity.this.setResult(RESULT_OK, intent);
			// 关闭Activity
			GuiderActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
