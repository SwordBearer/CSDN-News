package xmu.swordbearer.csdn.news.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class NewsList implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<News> news = new ArrayList<News>();

	public List<News> getNews() {
		return news;
	}

	public static NewsList fromXML(InputStream inputStream)
			throws XmlPullParserException, IOException {
		NewsList newsList = new NewsList();
		News news = null;
		XmlPullParser xpp = Xml.newPullParser();
		xpp.setInput(inputStream, null);

		String tagName = null;
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			tagName = xpp.getName();
			if (eventType == XmlPullParser.START_TAG) {
				if (tagName.equals(News.XML_TAG_ITEM)) {
					news = new News();
				} else if (news != null) {
					if (tagName.equals(News.XML_TAG_TITLE)) {
						news.setTitle(xpp.nextText());
					} else if (tagName.equals(News.XML_TAG_LINK)) {
						news.setLink(xpp.nextText());
					} else if (tagName.equals(News.XML_TAG_DESC)) {
						news.setDescription(xpp.nextText());
					} else if (tagName.equals(News.XML_TAG_URL)) {
						news.setImgUrl(xpp.nextText());
					} else if (tagName.equals(News.XML_TAG_PUBDATE)) {
						news.setPubDate(xpp.nextText());
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (tagName.equals(News.XML_TAG_ITEM) && news != null) {
					newsList.getNews().add(news);
					news = null;
				}
			}
			eventType = xpp.next();
		}
		Log.e("NewsList", "总共的News有 " + newsList.getNews().size());
		inputStream.close();
		return newsList;
	}
}
