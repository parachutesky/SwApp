package com.jnwat.tools;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.jnwat.bean.BAppVersion;

/**
 * AppVersion 的逻辑层
 * Created by WeiBo on 14/12/12.
 */
public class UpAppVersion {

    /**
     * 判断是否有新版本发布
     *
     * @param context     上下文
     * @param bAppVersion 版本对象
     * @return true 标识有更新  false标识没有更新
     */
    public static boolean isUpData(Context context, BAppVersion bAppVersion) {
        if (bAppVersion.getVERSION() > getVersionCode(context)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取APP的versionCode值
     *
     * @param context 上下文
     * @return APP的版本号
     */
    private static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    
    
    
    
    
    
    //打开APK程序代码
    
    private void openFile(File file) {
                     // TODO Auto-generated method stub
                     Log.e("OpenFile", file.getName());
                     Intent intent = new Intent();
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     intent.setAction(android.content.Intent.ACTION_VIEW);
                     intent.setDataAndType(Uri.fromFile(file),
                                     "application/vnd.android.package-archive");
                    // startActivity(intent);
             }
    
    
    
    
    
    
    
}
