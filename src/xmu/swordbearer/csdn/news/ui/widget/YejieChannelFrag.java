package xmu.swordbearer.csdn.news.ui.widget;

import xmu.swordbeare.alivelistview.AliveListView;
import xmu.swordbearer.csdn.R;
import xmu.swordbearer.csdn.common.net.URLs;
import xmu.swordbearer.csdn.news.entity.NewsAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;

public class YejieChannelFrag extends BaseChannelFrag implements
		View.OnClickListener {

	private RadioButton btnEnrance;// 初始界面，入口
	private RadioButton btnProduct;// 产品
	private RadioButton btnChuangye;// 创业
	private RadioButton btnCareer;// 职场
	private RadioButton btnRenwu;// 人物
	private RadioButton btnDesign;// 设计

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.frag_yejie, container,
				false);
		initViews(contentView);
		return contentView;
	}

	@Override
	public void initViews(View v) {
		btnEnrance = (RadioButton) v.findViewById(R.id.frag_yejie_tag_entrance);
		btnProduct = (RadioButton) v.findViewById(R.id.frag_yejie_tag_product);
		btnChuangye = (RadioButton) v
				.findViewById(R.id.frag_yejie_tag_chuangye);
		btnCareer = (RadioButton) v.findViewById(R.id.frag_yejie_tag_career);
		btnRenwu = (RadioButton) v.findViewById(R.id.frag_yejie_tag_renwu);
		btnDesign = (RadioButton) v.findViewById(R.id.frag_yejie_tag_design);
		lv = (AliveListView) v.findViewById(R.id.news_list);

		newsAdapter = new NewsAdapter(getActivity(), newsList.getNews());
		lv.setAdapter(newsAdapter);
		lv.setAliveListViewListener(this);

		btnEnrance.setOnClickListener(this);
		btnProduct.setOnClickListener(this);
		btnChuangye.setOnClickListener(this);
		btnCareer.setOnClickListener(this);
		btnRenwu.setOnClickListener(this);
		btnDesign.setOnClickListener(this);
		lv.setOnItemClickListener(this);

		loadDataList(TAG_YEJIE_ENTRANCE, LOAD_TYPE_CACHE);
	}

	public void onClick(View v) {
		if (v == btnEnrance) {
			loadDataList(TAG_YEJIE_ENTRANCE, LOAD_TYPE_CACHE);
		} else if (v == btnProduct) {
			loadDataList(TAG_YEJIE_PRODUCT, LOAD_TYPE_CACHE);
		} else if (v == btnChuangye) {
			loadDataList(TAG_YEJIE_CHUANGYE, LOAD_TYPE_CACHE);
		} else if (v == btnCareer) {
			loadDataList(TAG_YEJIE_CAREER, LOAD_TYPE_CACHE);
		} else if (v == btnRenwu) {
			loadDataList(TAG_YEJIE_RENWU, LOAD_TYPE_CACHE);
		} else if (v == btnDesign) {
			loadDataList(TAG_YEJIE_DESIGN, LOAD_TYPE_CACHE);
		}
	}

	@Override
	public String getUri() {
		String uri = "";
		switch (currentTag) {
		case TAG_YEJIE_ENTRANCE:
			uri = URLs.NEWS_ENTRANCE;
			break;
		case TAG_YEJIE_PRODUCT:
			uri = URLs.TAG_YEJIE_PRODUCT;
			break;
		case TAG_YEJIE_CHUANGYE:
			uri = URLs.TAG_YEJIE_CHUANGYE;
			break;
		case TAG_YEJIE_CAREER:
			uri = URLs.TAG_YEJIE_CAREER;
			break;
		case TAG_YEJIE_RENWU:
			uri = URLs.TAG_YEJIE_RENWU;
			break;
		case TAG_YEJIE_DESIGN:
			uri = URLs.TAG_YEJIE_DESIGN;
			break;
		}
		return uri;
	}
}
