package xmu.swordbearer.csdn.news.widget;

import xmu.swordbearer.csdn.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;

public class MobileChannelFrag extends BaseChannelFrag {
	private RadioButton btnIOS;
	private RadioButton btnAndroid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.frag_mobile, container,
				false);
		initViews(contentView);
		return contentView;
	}

	public void onClick(View v) {
	}

	@Override
	public void initViews(View contentView) {
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}

	@Override
	public String getUri() {
		return null;
	}

}
