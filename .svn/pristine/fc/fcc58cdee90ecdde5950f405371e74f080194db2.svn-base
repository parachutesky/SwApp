package com.jnwat.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;


public class GetRawStringFile {
	/**
	 * 加载本地数据的方法
	 * 
	 * @return
	 */
	 public static   String rawRead(Context mContext,int file) {
		String ret = "";
		try {
			InputStream is = mContext.getResources().openRawResource(file);
			int len = is.available();
			byte[] buffer = new byte[len];
			is.read(buffer);
			ret = EncodingUtils.getString(buffer, "gb2312");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	 public static String read(File file){
		 byte[] btt = null ;
		  try {
				 if(!file.exists()){
				     file.createNewFile();
				 }
				 FileInputStream fis = new FileInputStream(file);
				 long length = file.length();
				 btt = new byte[(int)length];
				 fis.read(btt);
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			return new String(btt);
	 }
	
}
