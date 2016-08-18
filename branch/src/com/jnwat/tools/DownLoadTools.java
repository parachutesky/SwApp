package com.jnwat.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author chang-zhiyuan 下载工具类
 */
public class DownLoadTools {

	/**
	 * 更新APP方法
	 * 
	 * @param urlString
	 *            下载路径
	 * @throws Exception
	 */
	public void DownLoadApp(String urlString) throws Exception {

		URL url = new URL(urlString);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		int length = urlConnection.getContentLength();
		InputStream inputStream = urlConnection.getInputStream();
		OutputStream outputStream = new FileOutputStream(new File(
				SDCardTools.getAPKFilePath()));
		byte buffer[] = new byte[1024 * 3];
		int readsize = 0;
		while ((readsize = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, readsize);
		}
		inputStream.close();
		outputStream.close();
	}
}
