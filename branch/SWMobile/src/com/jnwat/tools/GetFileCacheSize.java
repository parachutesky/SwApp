package com.jnwat.tools;

import java.io.File;
import java.io.IOException;

import android.text.TextUtils;


/**
 * @author chang-zhiyuan
 * 得到缓存大小
 */
public class GetFileCacheSize {
	
	  /**
	    * 获取文件夹大小
	    * @param file File实例
	    * @return long 单位为M
	    * @throws Exception
	    */
	    public static long getFolderSize(java.io.File file)throws Exception{
	    long size = 0;
	    java.io.File[] fileList = file.listFiles();
	    for (int i = 0; i < fileList.length; i++)
	    {
	    if (fileList[i].isDirectory())
	    {
	    size = size + getFolderSize(fileList[i]);
	    } else
	    {
	    size = size + fileList[i].length();
	    }
	    }
	    return size/1048576;
	    }
	    
	    /**
	     * 删除指定目录下文件及目录
	     *
	     * @param deleteThisPath
	     * @param filepath
	     * @return
	     */
	     public static  void deleteFolderFile(String filePath, boolean deleteThisPath)
	     throws IOException {
	     if (!TextUtils.isEmpty(filePath)) {
	     File file = new File(filePath);
	     if (file.isDirectory()) {// 处理目录
	     File files[] = file.listFiles();
	     for (int i = 0; i < files.length; i++) {
	     deleteFolderFile(files[i].getAbsolutePath(), true);
	     } 
	     }
	     if (deleteThisPath) {
	     if (!file.isDirectory()) {// 如果是文件，删除
	     file.delete();
	     } else {// 目录
	     if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
	     file.delete();
	     }
	     }
	     }
	     }
	     }
	     
	     
	   //递归删除文件及文件夹
	 	public static void delete(File file) {
	 		if (file.isFile()) {
	 			file.delete();
	 			return;
	 		}
	 		if(file.isDirectory()){
	 			File[] childFiles = file.listFiles();
	 			if (childFiles == null || childFiles.length == 0) {
	 				file.delete();
	 				return;
	 			}
	 			for (int i = 0; i < childFiles.length; i++) {
	 				delete(childFiles[i]);
	 			}
	 			file.delete();
	 		}
	 	}
}
