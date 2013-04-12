package xmu.swordbearer.csdn.news.ui.acitvity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import xmu.swordbearer.csdn.R;
import xmu.swordbearer.csdn.news.entity.News;
import xmu.swordbearer.csdn.news.entity.NewsList;
import xmu.swordbearer.csdn.news.ui.widget.BaseChannelFrag;
import xmu.swordbearer.smallraccoon.http.HttpGetHelper;
import xmu.swordbearer.smallraccoon.http.HttpUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class NewsDetailActivity extends Activity {
	private Button btnBack;
	private Button btnRefresh;
	private TextView tvTitle;
	private WebView wvContent;
	private Button btnComment;
	private Button btnFavorite;
	private Button btnShare;

	private List<News> dataList = null;
	private int curPos = 0;

	private String infor = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		initView();

		Intent intent = getIntent();
		NewsList newsList = (NewsList) intent
				.getSerializableExtra(BaseChannelFrag.EXTRA_NEWSLIST);
		curPos = intent.getIntExtra(BaseChannelFrag.EXTRA_CUR_POS, 0);
		if (newsList == null || newsList.getNews().size() == 0) {
			finish();
		} else {
			dataList = newsList.getNews();
		}
		showNews();
	}

	@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
	private void initView() {
		btnBack = (Button) findViewById(R.id.title_btn_back);
		btnRefresh = (Button) findViewById(R.id.title_btn_refresh);
		btnComment = (Button) findViewById(R.id.comment);
		btnFavorite = (Button) findViewById(R.id.favorite);
		btnShare = (Button) findViewById(R.id.share);
		wvContent = (WebView) findViewById(R.id.webview);

		WebSettings ws = wvContent.getSettings();
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setDefaultTextEncodingName("utf-8"); // 设置文本编码
		ws.setAppCacheEnabled(true);
		ws.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置缓存模式
		ws.setJavaScriptEnabled(true);
	}

	private void showNews() {
		News news = dataList.get(curPos);
		String newsUri = "http://m.csdn.net/article.html?arcid=2814646";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGetHelper httpGetHelper = new HttpGetHelper();
		InputStream inputStream = httpGetHelper.httpGetStream(httpClient,
				newsUri);
		try {
			String content = HttpUtils.readStream2String(inputStream);
			wvContent.loadDataWithBaseURL(null, content, "text/html", "utf-8",
					null);
			// wvContent.loadUrl(newsUri);
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
			wvContent.loadUrl(newsUri);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			HttpUtils.shutdown(httpClient);
		}
	}

	private void filterPage() {
	}
}
