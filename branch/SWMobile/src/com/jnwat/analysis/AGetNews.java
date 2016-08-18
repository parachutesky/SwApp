package com.jnwat.analysis;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.NewsInfo;
import com.jnwat.bean.OverTask;
import com.lidroid.xutils.util.LogUtils;

public class AGetNews {
      
public int analysisNews(String result,List<NewsInfo> list,int type){
		int  isGETDATA = 0; //当 为 0 时说明 是 未获取新数据   当   为  1 时 说明  获取了新的数据
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	if(type==1){ //如果 type为1 说明是下拉  刷新   当 type 为2的时候说明是上拉加载
		    	     list.clear();
		    	}
		    	
		    	 JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
		    	 int length = jsonArray.length();
		    	 if(length>0){
		    		 isGETDATA = 1;
		    	 }else{
		    		 isGETDATA = 2; // 当 isGetDATA为 2时 说明  正常情况下 无数据
		    	 }
		    	 String str="";
		    	 for(int i =0;i < length ;i ++){
			    		JSONObject obj = (JSONObject) jsonArray.get(i);
			    		String ID = obj.getString("ID");
			    		String Title = obj.getString("Title");
			    		String NextTitle = obj.getString("NextTitle");
			    		String publisher = obj.getString("publisher");
			    		String PublishDate = obj.getString("PublishDate");
			    	    String Url = obj.getString("Url" );
			    		String ThumUrl = obj.getString( "ThumUrl");
			    		NewsInfo news = new NewsInfo();
			    		news.setID(ID);
			    		news.setTitle(Title);
			    		news.setNextTitle(NextTitle);
			    		news.setPublishDate(PublishDate);
			    		news.setPublisher(publisher);
			    		news.setUrl(Url);
			    		news.setThumUrl(ThumUrl);
			    		list.add(news);
		    	 }
		    	
		    	 
		     }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("新闻解析异常");
			e.printStackTrace();
			
		}
		return isGETDATA;
	}
  
}
