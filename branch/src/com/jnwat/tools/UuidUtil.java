package com.jnwat.tools;

import java.util.UUID;

import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 得到唯一的UUID 随机数
 */
public class UuidUtil {

	public static String getUUID() {
		String str_uuid = "";
		UUID uuid = UUID.randomUUID();
		str_uuid=	uuid.toString().split("-")[2];
		
		return str_uuid;
	}
}
