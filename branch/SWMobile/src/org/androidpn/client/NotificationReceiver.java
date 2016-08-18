/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jnwat.analysis.APushMessage;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.dialog.PushListenner;
import com.jnwat.swmobilegy.mainpager.MainPageFragment;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * Broadcast receiver that handles push notification messages from the server.
 * This should be registered as receiver in AndroidManifest.xml.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public final class NotificationReceiver extends BroadcastReceiver {
	public static boolean notshortmessage;
	String notificationId;
	String notificationApiKey;
	String packetId;
	String notificationFrom;
	String notificationUri;
	String notificationMessage;
	private Context mcontext;
	private Notifier notifier;
	DbUtils db;// = DbUtils.create(context);
	private APushMessage maAPushMessage;// 解析
	private static final String LOGTAG = LogUtil
			.makeLogTag(NotificationReceiver.class);
	private PushListenner mPushListennerImp;

	// private NotificationService notificationService;

	public NotificationReceiver() {
	}

	// public NotificationReceiver(NotificationService notificationService) {
	// this.notificationService = notificationService;
	// }

	@Override
	public void onReceive(final Context context, Intent intent) {
		Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
		String action = intent.getAction();
		Log.d(LOGTAG, "action=" + action);
		if (mcontext == null) {
			mcontext = context;
			db = DbUtils.create(mcontext);
			LogUtils.e("初始化消息推送");
		}
		if (null == BIntentObj.list_PushMessage) {
			BIntentObj.list_PushMessage = new ArrayList<BPushMessageInfo>();
		}
		if (Constants.ACTION_SHOW_NOTIFICATION.equals(action)) {
			String notificationId = intent
					.getStringExtra(Constants.NOTIFICATION_ID);
			String notificationApiKey = intent
					.getStringExtra(Constants.NOTIFICATION_API_KEY);
			String notificationTitle = intent
					.getStringExtra(Constants.NOTIFICATION_TITLE);
			notificationMessage = intent
					.getStringExtra(Constants.NOTIFICATION_MESSAGE);
			String notificationUri = intent
					.getStringExtra(Constants.NOTIFICATION_URI);
			/*
			 * Log.d(LOGTAG, "notificationId=" + notificationId); Log.d(LOGTAG,
			 * "notificationApiKey=" + notificationApiKey); Log.d(LOGTAG,
			 * "notificationTitle=" + notificationTitle); Log.d(LOGTAG,
			 * "notificationMessage=" + notificationMessage); Log.d(LOGTAG,
			 * "notificationUri=" + notificationUri);
			 */
			/*
			 * new Thread(new Runnable() {
			 * 
			 * @Override public void run() {
			 */
			// TODO Auto-generated method stub
			maAPushMessage = new APushMessage(context);
			// 消息存储
			try {
				BIntentObj.list_PushMessage.addAll(maAPushMessage.APushMessage(
						notificationMessage, db));

				if (null == notifier) {
					notifier = new Notifier(context);
				}
				int totle = maAPushMessage.getListSub();
				if (totle > 0) {// 有新消息
					MainPageFragment mMainPageFragment = MainPageFragment
							.newInstance("");
					BIntentObj.IsGetNewPushMessage = true;
					mMainPageFragment.setMessageStage();
					// 消息通知
					notifier.notify(notificationId, notificationTitle, totle
							+ "条新消息 !", notificationUri);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * } }).start();
			 */

			// Toast.makeText(context, //db.save(act_pushMessageBean)
			// notificationTitle+"/n"+notificationMessage,
			// Toast.LENGTH_LONG).show();

		}

	}

}
