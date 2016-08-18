package com.jnwat.bean;

/**
 * @author chang-zhiyuan
 *
 * 主页菜单Bean文件
 */
public class BMainPagerGridItem {
	 private String title;   
     private int imageId;   
     private String description;
  
     public BMainPagerGridItem()   
     {   
         super();   
     }   
    
     public BMainPagerGridItem(String title, int imageId,String time)   
     {   
         super();   
         this.title = title;   
         this.imageId = imageId;   
      //   this.description = time;  
     }   
    
     public String getTime( )  
     {  
         return description;  
     }  

     public String getTitle()   
     {   
         return title;   
     }   
    
     public int getImageId()   
     {   
         return imageId;   
     }   
 }  
