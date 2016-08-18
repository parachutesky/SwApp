/**
 * 
 */
package com.jnwat.swmobilegy;

import java.util.HashMap;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.bean.TrainingProgramBean;
import com.jnwat.swmobilegy.R;

/**
 * @author zhaorl
 * 
 * 项目的详情页
 *
 */
public class TrainingProgramDetailActivity extends BaseActivity {

	private  static String TAG  = " TrainingProgramDetailActivity_20151203160120";
	private  String mTitle = "培训项目詳細頁";
	private  TextView  mTitle_tv;
	private  PullToRefreshListView mProgram_lv;
	private ImageView iv_back;
	private List<HashMap<String, String>> list;
	Gson gson = null;
	private TrainingProgramBean programBean  = null;
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		setBackListener(mTitle, false);
	}

	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#setListener()
	 */
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#initData()
	 */
	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#setView()
	 */
	@Override
	protected void setView() {
		// TODO Auto-generated method stub

		setContentView(R.layout.training_pro_detail);
		
		
		
	}

}
