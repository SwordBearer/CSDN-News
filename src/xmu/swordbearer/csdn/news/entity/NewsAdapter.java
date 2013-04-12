package xmu.swordbearer.csdn.news.entity;

import java.util.List;

import xmu.swordbearer.csdn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<News> dataList;

	private class ListViewItem {
		public TextView title;
		// public TextView date;
		public TextView desc;
	}

	public NewsAdapter(Context context, List<News> list) {
		inflater = LayoutInflater.from(context);
		this.dataList = list;
	}

	public int getCount() {
		return dataList.size();
	}

	public Object getItem(int position) {
		return dataList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ListViewItem listViewItem = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_news, null);
			listViewItem = new ListViewItem();
			listViewItem.title = (TextView) convertView
					.findViewById(R.id.news_item_title);
			// listViewItem.date = (TextView) convertView
			// .findViewById(R.id.news_item_date);
			listViewItem.desc = (TextView) convertView
					.findViewById(R.id.news_item_desc);
			// 设置控件集
			convertView.setTag(listViewItem);
		} else {
			listViewItem = (ListViewItem) convertView.getTag();
		}
		News news = dataList.get(position);
		listViewItem.title.setText(news.getTitle());
		// listViewItem.date.setText(news.getPubDate());
		listViewItem.desc.setText(news.getDescription().trim());
		// listViewItem.desc.setTag(news);// 绑定数据
		return convertView;
	}
}
