package com.jnwat.config;

import android.util.Log;

/**
 * APP的LOG输出工具类 Created by WeiBo on 14/12/11.
 */
public class AppLog {
	/**
	 * e
	 * 
	 * @param biaoshi
	 *            标识
	 * @param logdata
	 *            打印数据
	 */
	public static void e(String biaoshi, String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.e("APPLOG", "标识为" + biaoshi + "的数据====>[" + logdata + "]");
		}
	}

	/**
	 * w
	 * 
	 * @param biaoshi
	 *            标识
	 * @param logdata
	 *            打印数据
	 */
	public static void w(String biaoshi, String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.w("APPLOG", "标识为" + biaoshi + "的数据====>[" + logdata + "]");
		}
	}

	/**
	 * d
	 * 
	 * @param biaoshi
	 *            标识
	 * @param logdata
	 *            打印数据
	 */
	public static void d(String biaoshi, String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.d("APPLOG", "标识为" + biaoshi + "的数据====>[" + logdata + "]");
		}
	}

	/**
	 * i
	 * 
	 * @param biaoshi
	 *            标识
	 * @param logdata
	 *            打印数据
	 */
	public static void i(String biaoshi, String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.i("APPLOG", "标识为" + biaoshi + "的数据====>[" + logdata + "]");
		}
	}

	/**
	 * v
	 * 
	 * @param biaoshi
	 *            标识
	 * @param logdata
	 *            打印数据
	 */
	public static void v(String biaoshi, String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.v("APPLOG", "标识为" + biaoshi + "的数据====>[" + logdata + "]");
		}
	}

	/**
	 * e
	 * 
	 * @param logdata
	 *            打印数据
	 */
	public static void e(String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.e("APPLOG", "打印数据====>[" + logdata + "]");
		}
	}

	/**
	 * w
	 * 
	 * @param logdata
	 *            打印数据
	 */
	public static void w(String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.w("APPLOG", "打印数据====>[" + logdata + "]");
		}
	}

	/**
	 * d
	 * 
	 * @param logdata
	 *            打印数据
	 */
	public static void d(String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.d("APPLOG", "打印数据====>[" + logdata + "]");
		}
	}

	/**
	 * i
	 * 
	 * @param logdata
	 *            打印数据
	 */
	public static void i(String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.i("APPLOG", "打印数据====>[" + logdata + "]");
		}
	}

	/**
	 * v
	 * 
	 * @param logdata
	 *            打印数据
	 */
	public static void v(String logdata) {
		if (AppConfig.ISDEBUG) {
			Log.v("APPLOG", "打印数据====>[" + logdata + "]");
		}
	}

}
