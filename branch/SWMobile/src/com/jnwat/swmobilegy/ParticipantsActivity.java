package com.jnwat.swmobilegy;

import java.util.ArrayList;
import java.util.List;

import com.jnwat.analysis.AGetParticipatants;
import com.jnwat.bean.Participatants;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.R;
import com.jnwat.personDetail.PersonalInfoActivity;
import com.jnwat.swmobilegy.dapter.AdapterParticipants;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ParticipantsActivity extends BaseActivity {
	private TextView title_bar_name;
	private ListView listView;
	private List<Participatants> data = new ArrayList<Participatants>();
	private AdapterParticipants adapter;
	private HttpUtils http;
	private LinearLayout header;
	private ImageView iv_back, iv_message;
	private String projectid;// 项目id
	PopLoginDialog mPopLoginDialog = new PopLoginDialog();

	/*
	 * PopupWindow popWindow = new PopupWindow(); View progress;
	 */
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		View v = LayoutInflater.from(this).inflate(
				R.layout.header_participatants, null);
		listView = (ListView) findViewById(R.id.list_participants);
		iv_message = (ImageView) findViewById(R.id.iv_message);
		iv_message.setVisibility(View.GONE);
		// header = (LinearLayout) v.findViewById(R.id.header);
		listView.addHeaderView(v);
		// progress = findViewById(R.id.progress_1);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position > 0) {
					Participatants participatants = data.get(position - 1);
					Intent intent = new Intent();
					intent.setClass(ParticipantsActivity.this,
							ParticipantsDetailActivity.class);

					intent.putExtra("Name", participatants.getName());
					intent.putExtra("MobilePhone",
							participatants.getMobilePhone());
					intent.putExtra("Organization",
							participatants.getOrganization());
					startActivity(intent);
				}

			}

		});

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		// projectid
		Intent intent = this.getIntent();
		projectid = intent.getStringExtra("Projectid");
		LogUtils.i("Particpants....Projectid is " + projectid);
		getWebData();

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_participants_list);
		title_bar_name = (TextView) findViewById(R.id.title_bar_name);
		title_bar_name.setText("学员名单");
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				ParticipantsActivity.this);

	}

	public void getWebData() {
		// String projectid = "ec5456a8-e6c6-492e-95b3-e3c7fd0bc0b6";
		RequestParams params = new RequestParams();
		params.addBodyParameter("projectid", projectid);
		http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				AppConfig.getIp(ParticipantsActivity.this)
						+ AppConfig.METHOAD_GETPARTICIPATANTS, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						// progress.setVisibility(View.GONE);
						ToastViewTools.initToast(ParticipantsActivity.this,
								"网络连接失败");
						/*
						 * if(data.size()==0){ lcoalData(); } adapter = new
						 * AdapterParticipants(ParticipantsActivity.this,data);
						 * listView.setAdapter(adapter); adapter.updateData();
						 */
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						LogUtils.d("responseInfo.result:" + responseInfo.result);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						AGetParticipatants aGetParticipatants = new AGetParticipatants();
						aGetParticipatants.analysisParticipatants(responseInfo.result, data);
						/*
						 * if(data.size()==0){ lcoalData(); }
						 */
						adapter = new AdapterParticipants(
								ParticipantsActivity.this, data);
						listView.setAdapter(adapter);
						adapter.updateData();
						// progress.setVisibility(View.GONE);
					}

				});
	}
	/*
	 * public void lcoalData(){ String name ="梅西"; for(int i =0;i < 20;i ++){
	 * Participatants participatants = new Participatants();
	 * participatants.setName(name); data.add(participatants); } }
	 */
}
