package com.jnwat.tools;

import java.io.File;

import android.os.Environment;

import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan SD Card的
 */
public class SDCardTools {

	private static final String file_name = "OaMobile"; // 文件名称
	public static final String file_path = "/jnwatoa/"; // 文件路径

	/**
	 * 得SD路径
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString().trim();
	}

	/**
	 * 得到文件的存储路径
	 * 
	 * @return
	 */
	public static String getAPKFilePath() {
		String filepath = getSDCardPath() + file_path + file_name
				+ UuidUtil.getUUID() + "。apk";
		LogUtils.d("文件的存储路径:" + filepath);
		return filepath.trim();
	}

	// 获取文件的保存路径
	public static File getFile_Path() throws Exception {
		File path = new File(getSDCardPath() + file_path);
		File file = new File(getAPKFilePath());
		if (!path.exists()) {
			path.mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	
	
	// 获取文件的保存路径
	public static File getFilePath() throws Exception {
		File path = new File(getSDCardPath() + file_path);
		if (!path.exists()) {
			path.mkdirs();
		}

		return path;
	}
}
