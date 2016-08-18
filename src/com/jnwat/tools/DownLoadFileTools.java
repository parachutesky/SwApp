package com.jnwat.tools;

import java.io.File;

import android.os.Handler;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @author chang-zhiyuan 下载文件
 */
public class DownLoadFileTools {
	public static String SDpatn = "";
	
	

	/**
	 * @param http
	 * @param filepath
	 */
	public  void downFile(HttpUtils http,String filePath,String savePath,final Handler mHandler){
		System.out.println("文件名:"+savePath);
		http.download(filePath,savePath, true, false, new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(10000);
			}
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				super.onLoading(total, current, isUploading);
				System.out.println("文件下载进度:"+current + "/" + total);
				
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(10001);
			}
		}
				); 
		
	}
}
