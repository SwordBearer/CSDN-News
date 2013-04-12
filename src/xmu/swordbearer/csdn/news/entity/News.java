package xmu.swordbearer.csdn.news.entity;

import java.io.Serializable;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String XML_TAG_ITEM = "item";
	public static final String XML_TAG_TITLE = "title";
	public static final String XML_TAG_LINK = "link";
	public static final String XML_TAG_DESC = "description";
	// public static final String XML_TAG_IMAGE = "image";
	public static final String XML_TAG_URL = "url";
	public static final String XML_TAG_AUTHOR = "author";
	public static final String XML_TAG_PUBDATE = "pubDate";

	//
	private String title;
	private String link;
	private String description;
	private String imgUrl;
	private String author;
	private String pubDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

}
