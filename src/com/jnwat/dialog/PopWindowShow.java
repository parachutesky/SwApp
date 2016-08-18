package com.jnwat.dialog;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jnwat.swmobilegy.R;

/**
 * Created by Parachute on 14/12/12.
 */
public class PopWindowShow {
	private PopupWindow popupWindow;

	public PopWindowShow() {
	}

	/**
	 * 用于类似Dialog的提示
	 * 
	 * @param ac
	 *            POP
	 * @param title
	 *            标题
	 * @param Content
	 *            内容
	 * @param ol
	 *            实现的接口方法
	 */
	public void showPopupWindowDialog(Activity ac, String title,
			String Content, final PopClickListening ol) {

		if (popupWindow == null) {
			popupWindow = new PopupWindow(ac);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setTouchable(true); // 设置PopupWindow可触摸
			popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		}
		View view = ac.getLayoutInflater().inflate(R.layout.popupwindow_dialog,
				null);
		Button pop_positiveButton = (Button) view
				.findViewById(R.id.pop_positiveButton);
		Button pop_negativeButton = (Button) view
				.findViewById(R.id.pop_negativeButton);
		TextView pop_title = (TextView) view.findViewById(R.id.pop_title);
		TextView pop__message = (TextView) view.findViewById(R.id.pop__message);
		pop_title.setText(title);
		pop__message.setText(Content);

		pop_positiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ol.PopPositiveLister();
			}
		});
		pop_negativeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ol.PopNegativeLister();
			}
		});
		popupWindow.setContentView(view);

		// 软键盘不会挡着popupwindow
		popupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LayoutParams.MATCH_PARENT);
		popupWindow.setAnimationStyle(R.style.popuStyle); // 设置
		popupWindow.showAtLocation(
				ac.getWindow().getDecorView()
						.findViewById(android.R.id.content), Gravity.CENTER, 0,
				0);
		popupWindow.update();

	}

	/**
	 * 会议申请审核
	 * 
	 * @param ac
	 * @param title
	 * @param Content
	 * @param ol
	 */
	public void showPopupWindowApply(Activity ac, final PopClickListening ol) {
		if (popupWindow == null) {
			popupWindow = new PopupWindow(ac);
			ColorDrawable cd = new ColorDrawable(0x000000);
			// 响应返回键必须的语句
			popupWindow.setBackgroundDrawable(cd);
			popupWindow.setFocusable(true);
			popupWindow.setTouchable(true); // 设置PopupWindow可触摸
			popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		}

		View view = ac.getLayoutInflater().inflate(R.layout.popupwindow_apply,
				null);
		Button pop_positiveButton = (Button) view
				.findViewById(R.id.pop_positiveButton);
		Button pop_negativeButton = (Button) view
				.findViewById(R.id.pop_negativeButton);
		LinearLayout lin_pop = (LinearLayout) view.findViewById(R.id.lin_pop);
		ListView lv_popwindow = (ListView) view.findViewById(R.id.lv_popwindow);
		ol.setListViewForName(lv_popwindow,lin_pop);
		final EditText pop__message = (EditText) view
				.findViewById(R.id.et_pop__message);
		pop_positiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ol.EditPopPositiveLister(pop__message);
			}
		});
		pop_negativeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ol.EditPopNegativeLister(pop__message);
			}
		});
		popupWindow.setContentView(view);

		// 软键盘不会挡着popupwindow
		popupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT); 
		popupWindow.setHeight(LayoutParams.MATCH_PARENT);
		popupWindow.setAnimationStyle(R.style.popuStyle); // 设置
		popupWindow.showAtLocation(
				ac.getWindow().getDecorView()
						.findViewById(android.R.id.content), Gravity.BOTTOM, 0,
				0);
		popupWindow.update();

	}

	/**
	 * 让popupWindow Dialog_waiting消失
	 */
	public void popWindowdismiss() { 
		if (popupWindow != null) {
			try {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 判断 PopWindowd_load 是否已经show
	 * 
	 * @return
	 */
	public boolean isShowPopWindowd() {
		if (popupWindow != null) {
			return popupWindow.isShowing();
		} else {
			return false;
		}
	}

}
