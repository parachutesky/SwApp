package com.jnwat.swmobilegy.mainpager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 办班情况图表
 */
public class ChartTrainActivity extends BaseActivity implements
		OnSeekBarChangeListener, OnChartValueSelectedListener {
	protected BarChart mChart;
	private Typeface mTf;
	private Button button_getchart;
	// "Name1": "期数",
	// "Name2": "培训天数",
	private String name1;
	private String name2;
	private ArrayList<HashMap<String, String>> data_list;
	int mYear;
	private final int SING_CHOICE_DIALOG = 1;
	private String years[];

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		http = new HttpUtils();
		http.configTimeout(2500);
		http.configCurrentHttpCacheExpiry(1500); // 缓存时间
		setContentView(R.layout.activity_charttrain);
		setBackListener("图表", false);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				ChartTrainActivity.this);
		// 获取网络数据
		Calendar c = Calendar.getInstance();// 首先要获取日历对象
		mYear = c.get(Calendar.YEAR); // 获取当前年份

		mChart = (BarChart) findViewById(R.id.chart_training);
		button_getchart = (Button) findViewById(R.id.button_getchart);
		getTrainingCourse(mYear);
		button_getchart.setText(mYear + "");
		years = new String[5];
		for (int i = 0; i < 5; i++) {
			years[i] = mYear + "";
			mYear--;
		}
		mChart.setOnChartValueSelectedListener(this);

		mChart.setDrawBarShadow(false);
		mChart.setDrawValueAboveBar(true);
		mChart.setDescription("柱状图");
		mChart.setDrawGridBackground(true);// 是否显示表格
		mChart.setScaleEnabled(true);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		mChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setDrawGridBackground(false);
		// mChart.setDrawYLabels(false);
		mChart.setNoDataTextDescription("暂无数据");

		mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setTypeface(mTf);
		xAxis.setDrawGridLines(false);
		xAxis.setSpaceBetweenLabels(1);

		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setForm(LegendForm.SQUARE);
		l.setFormSize(9f);
		l.setTextSize(11f);
		l.setXEntrySpace(2f);
		// l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });
		// l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });
		// setData(10, 10);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * switch (item.getItemId()) { case R.id.actionToggleValues: { for
		 * (IDataSet set : mChart.getData().getDataSets())
		 * set.setDrawValues(!set.isDrawValuesEnabled());
		 * 
		 * mChart.invalidate(); break; } case R.id.actionToggleHighlight: { if
		 * (mChart.getData() != null) { mChart.getData().setHighlightEnabled(
		 * !mChart.getData().isHighlightEnabled()); mChart.invalidate(); }
		 * break; } case R.id.actionTogglePinch: { if
		 * (mChart.isPinchZoomEnabled()) mChart.setPinchZoom(false); else
		 * mChart.setPinchZoom(true);
		 * 
		 * mChart.invalidate(); break; } case R.id.actionToggleAutoScaleMinMax:
		 * {
		 * mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
		 * mChart.notifyDataSetChanged(); break; } case
		 * R.id.actionToggleHighlightArrow: { if
		 * (mChart.isDrawHighlightArrowEnabled())
		 * mChart.setDrawHighlightArrow(false); else
		 * mChart.setDrawHighlightArrow(true); mChart.invalidate(); break; }
		 * case R.id.animateX: { mChart.animateX(3000); break; } case
		 * R.id.animateY: { mChart.animateY(3000); break; } case R.id.animateXY:
		 * {
		 * 
		 * mChart.animateXY(3000, 3000); break; } case R.id.actionSave: { if
		 * (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
		 * Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
		 * Toast.LENGTH_SHORT).show(); } else
		 * Toast.makeText(getApplicationContext(), "Saving FAILED!",
		 * Toast.LENGTH_SHORT).show(); break; } }
		 */
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		// setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
		mChart.invalidate();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置数据
	 * 
	 * @param count
	 * @param range
	 */
	private void setNetData(ArrayList<HashMap<String, String>> data) {
		int leng = data.size();
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < leng; i++) {
			// xVals.add(mMonths[i % 12]);
			xVals.add(data.get(i).get("Name"));
		}

		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

		for (int i = 0; i < leng; i++) {
			yVals1.add(new BarEntry(Float.parseFloat(data.get(i).get("Qishu")),
					i));
			yVals2.add(new BarEntry(Float
					.parseFloat(data.get(i).get("Tianshu")), i));
		}

		BarDataSet set1 = new BarDataSet(yVals1, "期数");
		BarDataSet set2 = new BarDataSet(yVals2, "培训天数");
		set1.setBarSpacePercent(35f);
		set1.setColor(Color.rgb(104, 241, 175));
		set2.setBarSpacePercent(35f);
		set2.setColor(Color.rgb(164, 228, 251));
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);
		dataSets.add(set2);

		BarData data1 = new BarData(xVals, dataSets);
		data1.setValueTextSize(10f);
		data1.setValueTypeface(mTf);
		mChart.setData(data1);
	}

	@SuppressLint("NewApi")
	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

		if (e == null)
			return;

		RectF bounds = mChart.getBarBounds((BarEntry) e);
		PointF position = mChart.getPosition(e, AxisDependency.LEFT);

		Log.i("bounds", bounds.toString());
		Log.i("position", position.toString());

		Log.i("x-index", "low: " + mChart.getLowestVisibleXIndex() + ", high: "
				+ mChart.getHighestVisibleXIndex());
	}

	public void onNothingSelected() {
	};

	/**
	 * 所有项目 TrainingCourse
	 */
	private void getTrainingCourse(int mnub) {
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("year", mnub + "");
		// 年份
		String url = AppConfig.getIp(ChartTrainActivity.this)
				+ AppConfig.METHOAD_GETCHART;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("绘图:" + responseInfo.result);
						JSONObject jsonobject;
						int Status;
						try {
							jsonobject = new JSONObject(responseInfo.result);

							String Message = jsonobject.get("Message")
									.toString().trim();// 错误提示信息
							ShowRemind.ErrorMessage = Message;

							Status = jsonobject.getInt("Status");
							// ShowRemind.HANDLER_STATUS = Status;
							if (0 != Status && Status == 200) {// 如果拿到的值是正确的继续解析
								JSONObject msonobject = jsonobject
										.getJSONObject("ReplyObject");

								name1 = msonobject.getString("Name1");// 期数
								name2 = msonobject.getString("Name2");// 培训天数

								JSONArray mArray = msonobject
										.getJSONArray("Data");
								int lenth = mArray.length();
								data_list = new ArrayList<HashMap<String, String>>();

								for (int i = 0; i < lenth; i++) {
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("Name", mArray.getJSONObject(i)
											.getString("Name"));
									map.put("Qishu", mArray.getJSONObject(i)
											.getString("Qishu"));
									map.put("Tianshu", mArray.getJSONObject(i)
											.getString("Tianshu"));
									data_list.add(map);

								}

							} else {// 解析错误编码，提示用户
								LogUtils.d("解析BUserlogin错误代码:" + Status);
								mHandler.sendEmptyMessage(-100);
							}
							mHandler.sendEmptyMessage(100);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							LogUtils.d("登录解析异常");
							BUserlogin.loginStatus = 0;
							e.printStackTrace();
							mHandler.sendEmptyMessage(-100);
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 刷新

			case 100:
				mChart.setVisibility(View.VISIBLE);
				setNetData(data_list);
				mChart.invalidate();
				break;
			case -100:
				mChart.setVisibility(View.INVISIBLE);
				ToastViewTools.initToast(ChartTrainActivity.this, "获取数据出错");
				break;

			}

		}
	};

	protected void onCreateDialog() {

		new AlertDialog.Builder(this)
				.setTitle("年份")
				.setSingleChoiceItems(years, 0,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mYear =	Integer.parseInt(years[which] );
								mChart.setVisibility(View.INVISIBLE);
								getTrainingCourse(mYear);
								button_getchart.setText(mYear+"");
								
								
								dialog.dismiss();
								
								
							}
						}).setNegativeButton("取消", null).show();

	}

	public void showDialog(View view) {
		onCreateDialog();
	}

}
