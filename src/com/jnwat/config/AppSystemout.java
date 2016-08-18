package com.jnwat.config;

/**
 * @author chang-zhiyuan APP的System的输出工具类
 */
public class AppSystemout {
	/**
	 * 打印数据
	 * 
	 * @param flag
	 *            打印数据标识
	 * @param data
	 *            打印数据
	 */
	public static void print(String flag, String data) {
		if (AppConfig.ISDEBUG) {
			System.out.println("标识" + flag + "的数据====>[" + data + "]");
		}
	}

	/**
	 * 打印数据
	 * 
	 * @param data
	 *            打印数据
	 */
	public static void print(String data) {
		if (AppConfig.ISDEBUG) {
			System.out.println("打印的数据====>[" + data + "]");
		}
	}
}
