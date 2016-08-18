package com.jnwat.swmobilegy.mainpager;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.dialog.TitlePopup;
import com.jnwat.dialog.TitlePopup.OnItemOnClickListener;
import com.jnwat.swmobilegy.LoginActivity;
import com.jnwat.swmobilegy.MessageActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.SalaryQueryActivity;
import com.jnwat.swmobilegy.TrainingProgramActivity;
import com.jnwat.swmobilegy.UnEducationListActivity;
import com.jnwat.swmobilegy.view.ActionItem;
import com.jnwat.swmobilegy.view.MyImgScroll;
import com.jnwat.tools.ToastViewTools;
import com.jnwat.view.WorkEventPopupWindow;
import com.lidroid.xutils.util.LogUtils;

@SuppressLint("ResourceAsColor")
public class MainPageFragment extends Fragment {
	private LayoutInflater layoutInflater;
	private View view;
	private TextView tv_sercher;
	public static ImageView iv_message;
	private Activity mContext;
	private LinearLayout lin_mainpager_dec;
	private TitlePopup titlePopup;
	LinearLayout lin_menu, LayoutPopup;
	private WorkEventPopupWindow mPopup;
	private GridView gridview_sw_main;
	// private List<BUserTasks> showList = new ArrayList<BUserTasks>();
	private String[] name_teacher = { "开班通知", "培训项目", "课表查询", "非教学工作", "请假管理",
			"学员互动", "培训项目统计", "薪资查询", "我的项目" };
	private ViewPager viewPager = null;
	private ViewPagerAdapter mAdapter = null;
	private int[] image = new int[] { R.drawable.image1, R.drawable.image2,
			R.drawable.image3, R.drawable.news_picture }; // 需要轮播的图片
	private ImageView[] indicator_imgs = new ImageView[image.length];// 存放引到图片数组
	private List<View> list = null;
	private MyImgScroll myPager = null;
	LinearLayout ovalLayout = null;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {

			case 1:

				mAdapter.notifyDataSetChanged();

				break;
			}

		}

	};

	public static MainPageFragment newInstance(String s) {
		MainPageFragment newFragment = new MainPageFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		return newFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_mainpage, null);
			initView(view);
			MyAdapter myAdapter = new MyAdapter();
			gridview_sw_main.setAdapter(myAdapter);
			initListen();
			// 设置消息图标
			setMessageStage();
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this.getActivity();
		layoutInflater = LayoutInflater.from(mContext);
		initPopWindow();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (null != iv_message) {
			// 设置消息图标
			setMessageStage();
		}
	}

	public void initView(View view) {

		gridview_sw_main = (GridView) view.findViewById(R.id.sw_gridview_item);
		lin_mainpager_dec = (LinearLayout) view
				.findViewById(R.id.lin_mainpager_dec);
		lin_mainpager_dec.getBackground().setAlpha(120);
		// 消息的button
		iv_message = (ImageView) view.findViewById(R.id.iv_message);
		LayoutPopup = (LinearLayout) view.findViewById(R.id.layout_popup);
		tv_sercher = (TextView) view.findViewById(R.id.tv_sercher);
		lin_menu = (LinearLayout) view.findViewById(R.id.lin_menu);

		myPager = (MyImgScroll) view.findViewById(R.id.vPager_image);
		ovalLayout = (LinearLayout) view.findViewById(R.id.home_vb);

		initViewPager();
		// 每隔3秒执行一次切换
		myPager.start(mContext, list, 3000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.indicator_focused, R.drawable.indicator);

	}

	/**
	 * 初始化图片
	 */
	private void initViewPager() {

		list = new ArrayList<View>();
		int[] imageResId = new int[] { R.drawable.image1, R.drawable.image2,
				R.drawable.image3, R.drawable.news_picture };
		for (int i = 0; i < imageResId.length; i++) {

			ImageView imageView = new ImageView(mContext);
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {// 设置图片点击事件

					if (myPager != null) {
						myPager.stopTimer();
					}

				}
			});

			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			list.add(imageView);
		}
	}

	// private void initIndicator(View view) {
	// // TODO Auto-generated method stub
	//
	// ImageView imgView;
	// View v = view.findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
	// int size = image.length;
	//
	// for (int i = 0; i < size; i++) {
	//
	// imgView = new ImageView(view.getContext());
	// LinearLayout.LayoutParams params_linear = new
	// LinearLayout.LayoutParams(10,10);
	// params_linear.setMargins(12, 10, 12, 10);
	// imgView.setLayoutParams(params_linear);
	// indicator_imgs[i] = imgView;
	//
	// if (i == 0) { // 初始化第一个为选中状态
	// indicator_imgs[i].setBackgroundResource(R.drawable.indicator_focused);
	// } else {
	// indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
	// }
	// ((ViewGroup)v).addView(indicator_imgs[i]);
	// }
	//
	//
	// }

	class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {

			// 改变所有导航的背景图片为：未选中
			for (int i = 0; i < indicator_imgs.length; i++) {

				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);

			}

			// 改变当前背景图片为：选中
			indicator_imgs[position]
					.setBackgroundResource(R.drawable.indicator_focused);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}

	class ViewPagerAdapter extends PagerAdapter {

		// private AsyncImageLoader asyncImageLoader;
		private List<View> mList;

		public ViewPagerAdapter(List<View> list) {
			mList = list;
			// asyncImageLoader = new AsyncImageLoader();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(mList.get(position));

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {

			// Drawable cachedImage = asyncImageLoader.loadDrawable(
			// urls[position], new ImageCallback() {
			//
			// public void imageLoaded(Drawable imageDrawable,
			// String imageUrl) {
			//
			// View view = mList.get(position);
			// image = ((ImageView) view.findViewById(R.id.image));
			// image.setBackground(imageDrawable);
			// container.removeView(mList.get(position));
			// container.addView(mList.get(position));
			// // adapter.notifyDataSetChanged();
			//
			// }
			// });

			View view = mList.get(position);
			ImageView imageView = ((ImageView) view.findViewById(R.id.image));
			imageView.setBackgroundResource(image[position]);

			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			return mList.get(position);

		}

	}

	// 监听 事件
	public void initListen() {
		gridview_sw_main.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				// TODO Auto-generated method stub
				if (name_teacher[position].equals("开班通知")) {
					ToastViewTools.initToast(getActivity(), "功能开发中，尽请期待");
					/*
					 * intent.setClass(getActivity(),
					 * ClassNotificationActivity.class); startActivity(intent);
					 */

				} else if (name_teacher[position].equals("培训项目")) {
					intent.setClass(getActivity(), ProjectSearchActivity.class);
					startActivity(intent);

				} else if (name_teacher[position].equals("课表查询")) {
					BIntentObj.isGetClass = 0;

					System.out.println("课表查询");
					intent.setClass(getActivity(), SearchScheduleActivity.class);
					startActivity(intent);

				} else if (name_teacher[position].equals("非教学工作")) {

					intent.setClass(getActivity(),
							UnEducationListActivity.class);
					startActivity(intent);

				} else if (name_teacher[position].equals("请假管理")) {
					/*
					 * intent.setClass(getActivity(),
					 * LeaveManagerActivity.class); startActivity(intent);
					 */

					ToastViewTools.initToast(getActivity(), "功能开发中，尽请期待");

				} else if (name_teacher[position].equals("学员互动")) {
					intent.setClass(getActivity(),
							ProjectQuestionAnswerActivity.class);
					startActivity(intent);

				} else if (name_teacher[position].equals("培训项目统计")) {
					intent.setClass(getActivity(),
							TrainingProgramActivity.class);
					startActivity(intent);
					// TrainingProgramActivity
				} else if (name_teacher[position].equals("薪资查询")) {
					intent.setClass(getActivity(), SalaryQueryActivity.class);
					startActivity(intent);
					// TrainingProgramActivity
				} else if (name_teacher[position].equals("我的项目")) {
					BIntentObj.isGetClass = 1;

					intent.setClass(getActivity(), MyPjSearchActivity.class);
					startActivity(intent);
					// TrainingProgramActivity
				}
			}
		});
		tv_sercher.setOnClickListener(new OnClickListener() {// 跳转到搜索页面
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						mPopup = new WorkEventPopupWindow(getActivity());
						mPopup.setOutsideTouchable(false);
						mPopup.setFocusable(true);
						// mPopup.setBackgroundDrawable(new BitmapDrawable());
						mPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
						mPopup.showAtLocation(LayoutPopup,
								Gravity.FILL_VERTICAL, 0, 100);

						@SuppressWarnings("static-access")
						InputMethodManager imm = (InputMethodManager) mContext
								.getSystemService(mContext.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(0,
								InputMethodManager.HIDE_NOT_ALWAYS);

					}
				});
		lin_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				titlePopup.show(view);
			}
		});
		iv_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
	}

	private void initPopWindow() {
		// 实例化标题栏弹窗
		titlePopup = new TitlePopup(getActivity(), LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		titlePopup.setItemOnClickListener(onitemClick);
		// 给标题栏弹窗添加子类
		titlePopup
				.addAction(new ActionItem(getActivity(),
						R.string.action_settings,
						R.drawable.menu_button_workevent_add));
		titlePopup
				.addAction(new ActionItem(getActivity(),
						R.string.action_settings,
						R.drawable.menu_button_workevent_add));
		titlePopup
				.addAction(new ActionItem(getActivity(),
						R.string.action_settings,
						R.drawable.menu_button_workevent_add));
		titlePopup
				.addAction(new ActionItem(getActivity(),
						R.string.action_settings,
						R.drawable.menu_button_workevent_add));
	}

	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// 发起群聊
				break;
			case 1:// 添加朋友
				break;
			case 2:// 扫一扫
				break;
			case 3:// 收钱
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 设置图片的状态
	 */
	public void setMessageStage() {
		if (BIntentObj.IsGetNewPushMessage) {
			LogUtils.d("BIntentObj.IsGetNewPushMessage:"
					+ BIntentObj.IsGetNewPushMessage);
			iv_message.setImageResource(R.drawable.nav_button_message_new);
		} else {
			iv_message.setImageResource(R.drawable.nav_button_message);
			LogUtils.d("设置无消息");
		}

	}

	// 自定义适配器
	class MyAdapter extends BaseAdapter {
		// 图片数组
		private Integer[] imgs_teacher = { R.drawable.index_shortcut_icon8,
				R.drawable.index_shortcut_icon6,
				R.drawable.index_shortcut_icon5,
				R.drawable.index_shortcut_icon1,
				R.drawable.index_shortcut_icon3,
				R.drawable.index_shortcut_icon4,
				R.drawable.index_shortcut_icon2,
				R.drawable.index_shortcut_icon7,
				R.drawable.index_shortcut_icon9 };

		public int getCount() {
			return imgs_teacher.length;
		}

		public Object getItem(int item) {
			return item;
		}

		public long getItemId(int id) {
			return id;
		}

		// 创建View方法
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.sw_item_gridview_mainpage, null);
			}
			ImageView mImageView = (ImageView) convertView
					.findViewById(R.id.sw_iv_item_icon);
			TextView mTextView = (TextView) convertView
					.findViewById(R.id.sw_tv_item_name);

			mImageView.setImageResource(imgs_teacher[position]);
			mTextView.setText(name_teacher[position]);

			return convertView;
		}
	}

}
