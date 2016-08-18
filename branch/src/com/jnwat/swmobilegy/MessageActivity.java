package com.jnwat.swmobilegy;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.jnwat.bean.BIntentObj;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterMessageSort;
import com.jnwat.tools.ModifySysTitle;
import com.lidroid.xutils.DbUtils;

public class MessageActivity extends BaseActivity {
	private ListView lv_message;
	private AdapterMessageSort mAdapter;
	private DbUtils db;
	private boolean isFres = false;

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
		lv_message.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MessageActivity.this,
						NotifiMessageActivity.class);
				intent.putExtra("selectObj", true);// 等于fasle查看推送的列表
				intent.putExtra("type", getType(arg2));
				startActivity(intent);
			}
		});

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.message_layout);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark, this);
		setBackListener("消息", false);
		BIntentObj.IsGetNewPushMessage = false;
		if (null == db) {
			db = DbUtils.create(MessageActivity.this);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isFres){
			mAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		lv_message = (ListView) findViewById(R.id.lv_message);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mAdapter = new AdapterMessageSort(this, db);
		lv_message.setAdapter(mAdapter);
		isFres =true;
	}

	private int getType(int posi) {
		if (posi == 0) {
			return 1;
		}
		if (posi == 1) {
			return 2;
		}
		if (posi == 2) {
			return 4;
		}
		if (posi == 3) {
			return 8;
		}
		return posi;
	}

}
