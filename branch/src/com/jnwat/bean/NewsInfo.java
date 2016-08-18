package com.jnwat.bean;

public class NewsInfo {
	private String ID;
    private String Title;// 新闻 标题
    private String NextTitle;//
    private String publisher;// 发布人
    private String PublishDate;//新闻日期
    private String url ;//地址
    private String ThumUrl; // 新闻 图片
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		this.Title = title;
	}
	public String getNextTitle() {
		return NextTitle;
	}
	public void setNextTitle(String nextTitle) {
		NextTitle = nextTitle;
	}
	public String getPublishDate() {
		return PublishDate;
	}
	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getThumUrl() {
		return ThumUrl;
	}
	public void setThumUrl(String thumUrl) {
		ThumUrl = thumUrl;
	}
    
}
