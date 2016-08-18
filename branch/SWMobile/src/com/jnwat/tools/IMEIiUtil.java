package com.jnwat.tools;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author chang-zhiyuan
 * 获取手机的Imei
 */
public class IMEIiUtil {

    public static String getDeviceIMEI(Context context){
        TelephonyManager mTm=(TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        //获取用户唯一标示
        return mTm.getDeviceId();

    }

}
