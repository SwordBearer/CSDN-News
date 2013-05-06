package xmu.swordbearer.csdn.news.widget;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import xmu.swordbearer.csdn.common.util.NetUtils;
import xmu.swordbearer.csdn.common.util.UIUtils;
import xmu.swordbearer.csdn.news.entity.News;
import xmu.swordbearer.csdn.news.entity.NewsAdapter;
import xmu.swordbearer.csdn.news.entity.NewsList;
import xmu.swordbearer.csdn.news.entity.Page;
import xmu.swordbearer.csdn.news.ui.NewsDetailActivity;
import xmu.swordbearer.smallraccoon.cache.CacheUtil;
import xmu.swordbearer.smallraccoon.http.HttpGetHelper;
import xmu.swordbearer.smallraccoon.http.HttpUtils;
import xmu.swordbearer.smallraccoon.widget.LiveListView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class BaseChannelFrag extends Fragment implements
		OnItemClickListener, LiveListView.OnRefreshListener {

	public static final String EXTRA_NEWSLIST = "extra_newslist";
	public static final String EXTRA_CUR_POS = "extra_cur_pos";
	private static final String KEY_TAG_PREF = "pref_current_tag";
	/* 页面数据的加载方式 */
	public static final int LOAD_TYPE_CACHE = 1;
	public static final int LOAD_TYPE_NET = 2;

	/* 四个频道 ，依次是业界，移动，云计算，研发 */
	public static final int CHANNEL_YEJIE = 1;
	public static final int CHANNEL_MOBILE = 2;
	public static final int CHANNEL_CLOUD = 3;
	public static final int CHANNEL_PD = 4;

	/* 每个频道下面有不同的TAG，代表不同的页面内容 */
	// yejie
	public static final int TAG_YEJIE_ENTRANCE = 1;
	public static final int TAG_YEJIE_PRODUCT = 2;
	public static final int TAG_YEJIE_CHUANGYE = 3;
	public static final int TAG_YEJIE_CAREER = 4;
	public static final int TAG_YEJIE_RENWU = 5;
	public static final int TAG_YEJIE_DESIGN = 6;
	// mobile
	public static final int TAG_MOBILE_ENTRANCE = 7;
	public static final int TAG_MOBILE_IOS = 8;
	public static final int TAG_MOBILE_ANDROID = 9;
	public static final int TAG_MOBILE_WP = 10;
	public static final int TAG_MOBILE_RIM = 11;
	public static final int TAG_MOBILE_TOP = 12;
	public static final int TAG_MOBILE_CMDN = 13;
	// cloud
	public static final int TAG_CLOUD_ENTRANCE = 14;
	public static final int TAG_CLOUD_BIGDATA = 15;
	public static final int TAG_CLOUD_DATACENTER = 16;
	public static final int TAG_CLOUD_SERVER = 17;
	public static final int TAG_CLOUD_STORAGE = 18;
	public static final int TAG_CLOUD_VIRTUALIZE = 19;
	public static final int TAG_CLOUD_NOSQL = 20;
	public static final int TAG_CLOUD_OPENPLATFORM = 21;
	public static final int TAG_CLOUD_SAVE = 22;
	// sd
	public static final int TAG_SD_ENTRANCE = 23;
	public static final int TAG_SD_LANGUAGE = 24;
	public static final int TAG_SD_PLATFORM = 25;
	public static final int TAG_SD_HTML5 = 26;
	public static final int TAG_SD_FRONTEND = 27;
	public static final int TAG_SD_ALGORITHM = 28;
	public static final int TAG_SD_ARCHITECTURE = 29;
	public static final int TAG_SD_OS = 30;

	//
	public static final String NEWS_DETAIL = "news_detail";
	public static final String NEWS_EXTRA = "news_extra";

	private static final int MSG_UPDATE_DATA = 0X03;
	private static final int MSG_ERROR_DATA = 0X04;

	protected LiveListView lv;
	protected NewsList newsList = new NewsList();
	protected NewsAdapter newsAdapter;
	protected List<Fragment> pages = new ArrayList<Fragment>();
	private Context mContext;

	protected int currentTag;

	public abstract void initViews(View v);

	public abstract String getUri();

	// 当数据下载完了后通知刷新
	protected Handler dataHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MSG_UPDATE_DATA) {
				lv.onRefreshComplete();
				// progressDialog.dismiss();
				// newsAdapter.notifyDataSetChanged();
				newsAdapter = new NewsAdapter(getActivity(), newsList.getNews());
				lv.setAdapter(newsAdapter);
			} else if (msg.what == MSG_ERROR_DATA) {
				UIUtils.showToast(mContext, "数据加载错误...");
			}
		}
	};

	public void onItemClick(AdapterView<?> arg0, View item, int pos, long id) {
		Log.e("TEST", "CLICKITEMT");
		Intent gotoDetail = new Intent(getActivity(), NewsDetailActivity.class);
		gotoDetail.putExtra(EXTRA_NEWSLIST, newsList);
		gotoDetail.putExtra(EXTRA_CUR_POS, pos);
		startActivity(gotoDetail);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	public void gotoDetail(int newsId) {
		News news = (News) newsAdapter.getItem(newsId);
		Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
		Bundle extra = new Bundle();
		extra.putSerializable(NEWS_DETAIL, news);
		intent.putExtra(NEWS_EXTRA, extra);
		getActivity().startActivity(intent);
	}

	/**
	 * 加载数据
	 * 
	 * @param tag频道类型标志
	 * @param isFirst是否首次加载页面
	 * @param isRefresh
	 */
	protected final void loadDataList(int tag, final int loadType) {
		this.currentTag = tag;
		dataHandler.post(new Thread(new Runnable() {
			public void run() {
				loadData(loadType);
			}
		}));
	}

	private void loadData(final int loadType) {
		if (loadType == LOAD_TYPE_CACHE) {
			String cacheKey = "news_list_" + currentTag + ".cache";
			// 如果该缓存文件存在，则加载缓存，否则直接从网络下载
			if (CacheUtil.isExistCache(mContext, cacheKey)) {
				getDataFromCache(cacheKey);
			} else {
				getDataFromNet();
			}
		} else {// 如果要刷新
			getDataFromNet();
		}

	}

	private void getDataFromCache(String cacheKey) {
		Object cacheObj = CacheUtil.readCache(mContext, cacheKey);
		if (cacheObj == null) {
			updateDataList(false);
		} else {
			newsList = (NewsList) cacheObj;
			updateDataList(true);
		}
	}

	private void getDataFromNet() {
		String cacheKey = "news_list_" + currentTag + ".cache";
		// 从网络下载
		if (NetUtils.isNetworkConnected(mContext)) {
			String uri = getUri();
			HttpGetHelper getHelper = new HttpGetHelper();
			HttpClient httpClient = new DefaultHttpClient();
			try {
				InputStream inStream = getHelper.httpGetStream(httpClient, uri);
				// progressDialog.show();
				if (inStream == null) {// 如果数据为空，则加载缓存
					UIUtils.showToast(mContext, "从网络获取数据失败");
					getDataFromCache(cacheKey);
				} else {
					try {
						newsList = NewsList.fromXML(inStream);
						updateDataList(true);
						/* 保存缓存 */
						CacheUtil.saveCache(mContext, cacheKey, newsList);
					} catch (IOException e) {
						UIUtils.showToast(mContext, "从网络获取数据失败");
						getDataFromCache(cacheKey);
					}
				}
			} catch (Exception ex) {// 下载失败，加载缓存
				getDataFromCache(cacheKey);
			} finally {
				HttpUtils.shutdown(httpClient);
			}
		} else {/* 如果没有刷新，,或者网络连接异常，则加载缓存 */
			getDataFromCache(cacheKey);
		}

	}

	private void updateDataList(boolean isSuccess) {
		if (isSuccess) {
			dataHandler.sendEmptyMessage(MSG_UPDATE_DATA);
		} else {
			dataHandler.sendEmptyMessage(MSG_ERROR_DATA);
		}
	}

	public void onRefresh() {
		dataHandler.postDelayed(new Thread(new Runnable() {
			public void run() {
				loadData(LOAD_TYPE_NET);
			}
		}), 1600);
	}

	public void onLoadMore() {
	}

	private class SlidePageAdapter extends PagerAdapter {
		private List<Page> pages = new ArrayList<Page>();

		public SlidePageAdapter(List<Page> pages) {
			this.pages = pages;
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return false;
		}
	}
}
