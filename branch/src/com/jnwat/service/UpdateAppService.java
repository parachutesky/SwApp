package com.jnwat.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.jnwat.bean.IntentObject;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.SDCardTools;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan App自动更新之通知栏下载 APP更新
 * 
 */
public class UpdateAppService extends Service {

	private Context context;
	private Notification notification;
	private NotificationManager nManager;
	private PendingIntent pendingIntent;
	private File filepath = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println(".onCreate()");
		context = getApplicationContext();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		System.out.println("onStart:线程启动");
		super.onStart(intent, startId);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		CreateInform();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	// 创建通知
	public void CreateInform() {

		// 创建一个通知
		notification = new Notification(R.drawable.icon, "开始下载...",
				System.currentTimeMillis());
		notification.setLatestEventInfo(context, "正在下载...", "点击查看详细内容",
				pendingIntent);
		// 用NotificationManager的notify方法通知用户生成标题栏消息通知
		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.notify(0, notification);// id是应用中通知的唯一标识
		// 如果拥有相同id的通知已经被提交而且没有被移除，该方法会用更新的信息来替换之前的通知。
		ToastViewTools.initToast(context, ToastViewTools.TOAST_UPDATA);
		new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程
	}

	class updateRunnable implements Runnable {
		int downnum = 0;// 已下载的大小
		int downcount = 0;// 下载百分比

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				// 下载地址
				DownLoadApp(IntentObject.aPPDownLoad);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * 下载app
		 * 
		 * @param urlString
		 * @throws Exception
		 */
		public void DownLoadApp(String urlString) throws Exception {

			filepath = SDCardTools.getFile_Path();

			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			//超时时间
			urlConnection.setConnectTimeout(15000);
			LogUtils.d("filepath:"+filepath);
			int length = urlConnection.getContentLength();
			InputStream inputStream = urlConnection.getInputStream();
			OutputStream outputStream = new FileOutputStream(filepath);
			byte buffer[] = new byte[1024 * 2];
			int readsize = 0;
			while ((readsize = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, readsize);
				downnum += readsize;
				if ((downcount == 0)
						|| (int) (downnum * 100 / length) - 1 > downcount) {
					downcount += 1;
					notification
							.setLatestEventInfo(context, "正在下载中...", "已下载了"
									+ (int) downnum * 100 / length + "%",
									pendingIntent);
					nManager.notify(0, notification);
				}
				if (downnum == length) {
					notification.setLatestEventInfo(context, "已下载完成", "点击安装",
							pendingIntent);
					nManager.notify(0, notification);
					// 通知程序更新
					openFile(filepath);
					notification.flags = Notification.FLAG_AUTO_CANCEL;
				}
			}
			inputStream.close();
			outputStream.close();
		}
	}

	private void openFile(File file) {
		// TODO Auto-generated method stub
		
		nManager.cancel(0);
		 
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
}
