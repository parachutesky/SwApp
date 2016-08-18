package com.xycoding.www;
/**
 * 图片压缩和转换成base64字符串
 */
/**
 * @author wangz copy 
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class ImageUtils {
	//����ͼƬ������ֵ
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}
	// ���·�����ͼƬ��ѹ��������bitmap������ʾ
	public static Bitmap getSmallBitmap(String filePath) {
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);
	        // Calculate inSampleSize
	      options.inSampleSize = calculateInSampleSize(options, 480, 800);
	     //  options.inSampleSize = calculateInSampleSize(options, 120, 200);
	        // Decode bitmap with inSampleSize set
	     options.inJustDecodeBounds = false;

	    return BitmapFactory.decodeFile(filePath, options);
	 
	}
	//��bitmapת����String
	public static String bitmapToString(String filePath) {
            if(filePath!=null&&new File(filePath).exists()){
	        Bitmap bm = getSmallBitmap(filePath);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
	        byte[] b = baos.toByteArray();
	        return Base64.encodeToString(b, Base64.DEFAULT);
            }
            return "";
	    }


}
