package com.jnwat.personDetail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jnwat.analysis.AGETPERSONALDETAIL;
import com.jnwat.analysis.AGetUserInfo;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BTeacherMessgaeBean;
import com.jnwat.bean.BUserlogin;

import com.jnwat.bean.UserInfo;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.LoginActivity;
import com.jnwat.swmobilegy.view.CircleImageView;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.SharedPrefsUtil;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.xycoding.www.ClipPictureActivity;
import com.xycoding.www.ImageUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author wangzh
 * 
 */
public class PersonalInfoActivity extends Activity {
	private ImageView iv_back;
	private CircleImageView iv_image;
	private EditText edt_tel, edt_off_tel, edt_qq, edt_weixin, edt_graduInst,
			edt_name, edt_position, edt_department, edt_birthday; // 手机号 办公电话 qq
																	// 微信 毕业学校
	private TextView tv_head_edt, tv_edt;
	private ScrollView scroll;
	private final int TAKEPHOTO = 1;
	private final int PICTURES = 2;
	private Bitmap headBitmap;
	ChooseDialog dialog;
	private final String FILENAME_TEMP = "head_temp.jpg";// 临时
	private String EXTERNAL;
	private String headPath_temp;// 临时
	private HttpUtils http;
	private String path;
	private String FROM; // 从 哪里来的
	private final String CHOOSEPICTURE = "choosePicture";
	private final String OHTER = "other";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_detail);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				PersonalInfoActivity.this);
		Intent intent = this.getIntent();
		FROM = intent.getStringExtra("from");
		initView();
		initListen();
		getWebData();
	}

	// 设置头像
	// 只有从选择完图片进来这个页面的时候才会启动这个方法
	// from 使用来判断是否是从 选择图片 确定后 来到这个页面的
	public void setHead() {

		if (CHOOSEPICTURE.equals(FROM)) {
			EXTERNAL = this.getFilesDir().getAbsolutePath();
			headPath_temp = EXTERNAL + "/" + FILENAME_TEMP;
			File file_temp = new File(headPath_temp);
			if (file_temp.exists()) {
				Bitmap bmp = ImageUtils.getSmallBitmap(file_temp.getPath());
				if (iv_image != null) {
					iv_image.setImageBitmap(bmp);

				}
			}
			openWidget();
		}
	}

	public void initView() {

		iv_back = (ImageView) findViewById(R.id.reback_imb_pdt);
		edt_name = (EditText) findViewById(R.id.edt_name);
		edt_position = (EditText) findViewById(R.id.edt_position);
		edt_department = (EditText) findViewById(R.id.edt_department);
		edt_birthday = (EditText) findViewById(R.id.edt_birthday);
		tv_edt = (TextView) findViewById(R.id.tv_edit);
		edt_off_tel = (EditText) findViewById(R.id.edt_offphone);
		edt_tel = (EditText) findViewById(R.id.edt_mobphone);
		edt_qq = (EditText) findViewById(R.id.edt_qq);
		edt_weixin = (EditText) findViewById(R.id.edt_weixin);
		edt_graduInst = (EditText) findViewById(R.id.edt_graduInst);
		iv_image = (CircleImageView) findViewById(R.id.head_img);
		tv_head_edt = (TextView) findViewById(R.id.tv_head_edt);

		setHead();

	}

	// 将获取到的数据 填充到控件
	public void addData(BTeacherMessgaeBean teacherInfo) {
		if (teacherInfo == null) {
			teacherInfo = new BTeacherMessgaeBean();
		}
		edt_name.setText(teacherInfo.getName());
		edt_position.setText(teacherInfo.getPosition());
		edt_department.setText(teacherInfo.getDepartment());
		edt_birthday.setText(teacherInfo.getBirthday());
		edt_off_tel.setText(teacherInfo.getOfficephone());
		edt_tel.setText(teacherInfo.getMobilephone());
		edt_qq.setText(teacherInfo.getQq());
		edt_weixin.setText(teacherInfo.getWechat());
		edt_graduInst.setText(teacherInfo.getSchool());
	}

	// 获取个人资料数据
	public void getWebData() {

		String teacherId = BUserlogin.userid;
		System.out.println(">>>>>>>>getWeb>>>" + teacherId);
		http = new HttpUtils();
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("userid", teacherId.trim());
		http.send(HttpRequest.HttpMethod.POST,
				AppConfig.getIp(PersonalInfoActivity.this)
						+ AppConfig.METHOD_GETPERSONALDETAIL, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("responseInfo.result:" + responseInfo.result);
						AGETPERSONALDETAIL a = new AGETPERSONALDETAIL();

						BTeacherMessgaeBean teacherMessageBean = a
								.analysisPersonalDetail(responseInfo.result);
						addData(teacherMessageBean);
						BitmapUtils bitmapUtils = new BitmapUtils(
								PersonalInfoActivity.this);
						path = teacherMessageBean.getPhoto();

						if (iv_image != null && !path.equals("")
								&& !(CHOOSEPICTURE).equals(FROM)) {
							bitmapUtils.display(iv_image,
									teacherMessageBean.getPhoto());
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println(">>>>>><<<<<<<< failed");

					}
				});

	}

	// 修改个人档案数据
	public void updatePersonalDetail(String fileStr) {
		if (http == null) {
			http = new HttpUtils();
		}

		String teacherId = BUserlogin.userid;
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("userid", teacherId.trim());
		params.addBodyParameter("mobilephone", edt_tel.getText().toString()
				.trim());
		params.addBodyParameter("officephone", edt_off_tel.getText().toString()
				.trim());
		params.addBodyParameter("qq", edt_qq.getText().toString().trim());
		params.addBodyParameter("wechat", edt_weixin.getText().toString()
				.trim());
		params.addBodyParameter("school", edt_graduInst.getText().toString()
				.trim());
		// params.addBodyParameter("photoStr",fileStr.trim()); // 图片的字符串
		http.send(HttpRequest.HttpMethod.POST,
				AppConfig.getIp(PersonalInfoActivity.this)
						+ AppConfig.METHOD_UPDAPERSONALDETAIL, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("responseInfo.result:" + responseInfo.result);
						try {
							JSONObject jsonObject = new JSONObject(
									responseInfo.result);
							String Status = jsonObject.getString("Status");
							if ("200".equals(Status)) {
								ToastViewTools.initToast(
										PersonalInfoActivity.this, "更新成功");
								closeWidget();
							} else if ("1000".equals(Status)) {
								ToastViewTools.initToast(
										PersonalInfoActivity.this, "更新失败");

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println(">>>>>><<<<<<<< failed");
						ToastViewTools.initToast(PersonalInfoActivity.this,
								"网络连接失败");

					}
				});

	}

	public void initListen() {

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});

		iv_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PersonalInfoActivity.this, AvatorView.class);
				intent.putExtra("data", path);
				startActivity(intent);

			}

		});
		tv_head_edt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				new mDialog(PersonalInfoActivity.this).show();
			}

		});
		tv_edt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (tv_edt.getText().toString().equals("提交")) {

					String fileStr = saveNewHeadPicture();// 将头像图片 转换成base64字符串
					updatePersonalDetail(fileStr);

				} else if (tv_edt.getText().toString().equals("编辑")) {

					openWidget();
				}
			}

		});
	}

	public void closeWidget() {
		tv_edt.setText("编辑");
		edt_tel.setEnabled(false);
		edt_off_tel.setEnabled(false);
		edt_qq.setEnabled(false);
		edt_weixin.setEnabled(false);
		edt_graduInst.setEnabled(false);
		tv_head_edt.setVisibility(View.GONE);
	}

	public void openWidget() {
		tv_edt.setText("提交");
		edt_tel.setEnabled(true);
		edt_off_tel.setEnabled(true);
		edt_qq.setEnabled(true);
		edt_weixin.setEnabled(true);
		edt_graduInst.setEnabled(true);
		tv_head_edt.setVisibility(View.VISIBLE);
	}

	// 将临时的头像存储为新的头像 同时返回图片的base64字符串
	// 这里 不能给 headPath_temp 赋值为 真正的路径 防止 提交时出现问题
	// 只有在 选择了图片 返回到现在这个界面时 headPath_temp才是有路径的
	public String saveNewHeadPicture() {
		if (headPath_temp == null) {

			headPath_temp = "";
		}
		File file_temp = new File(headPath_temp);
		String str = "";
		if (file_temp.exists()) { // 临时文件存在 时说明有 图片去更新
			if (file_temp.length() > 200000) { // 如果 文件大小大于 200K 压缩一下
				Bitmap bmp = ImageUtils.getSmallBitmap(file_temp
						.getAbsolutePath());
			}
			str = ImageUtils.bitmapToString(file_temp.getAbsolutePath());
		}
		return str;
	}

	class mDialog extends ChooseDialog {

		public mDialog(Context context) {
			super(context);
			// TODO Auto-generated constructor stub

		}

		@Override
		public void takephoto() { // �����
			// TODO Auto-generated method stub
			Intent intent = new Intent(PersonalInfoActivity.this,
					ClipPictureActivity.class);
			intent.putExtra("way", "001");
			startActivity(intent);
			finish();
		}

		@Override
		public void takepicture() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(PersonalInfoActivity.this,
					ClipPictureActivity.class);
			intent.putExtra("way", "002");
			startActivity(intent);
			finish();
		}

	}

}
