package xmu.swordbearer.csdn.news.ui.acitvity;

import xmu.swordbearer.csdn.R;
import xmu.swordbearer.csdn.news.ui.widget.BaseChannelFrag;
import xmu.swordbearer.csdn.news.ui.widget.CloudChannelFrag;
import xmu.swordbearer.csdn.news.ui.widget.MobileChannelFrag;
import xmu.swordbearer.csdn.news.ui.widget.SDChannelFrag;
import xmu.swordbearer.csdn.news.ui.widget.YejieChannelFrag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private int curChannel = -1;

	private Button btnYejie;// 业界
	private Button btnMobile;// 移动
	private Button btnCloud;// 云计算
	private Button btnPD;// 软件研发

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		btnYejie = (Button) findViewById(R.id.main_channel_yejie);
		btnMobile = (Button) findViewById(R.id.main_channel_mobile);
		btnCloud = (Button) findViewById(R.id.main_channel_cloud);
		btnPD = (Button) findViewById(R.id.main_channel_pd);

		btnYejie.setOnClickListener(this);
		btnMobile.setOnClickListener(this);
		btnCloud.setOnClickListener(this);
		btnPD.setOnClickListener(this);
		//
		changeChannel(BaseChannelFrag.CHANNEL_YEJIE);
	}

	private void changeChannel(int channel) {
		if (curChannel == channel) {
			return;
		}
		curChannel = channel;
		BaseChannelFrag frag = null;
		switch (channel) {
		case BaseChannelFrag.CHANNEL_YEJIE:
			frag = new YejieChannelFrag();
			break;
		case BaseChannelFrag.CHANNEL_MOBILE:
			frag = new MobileChannelFrag();
			break;
		case BaseChannelFrag.CHANNEL_CLOUD:
			frag = new CloudChannelFrag();
			break;
		case BaseChannelFrag.CHANNEL_PD:
			frag = new SDChannelFrag();
			break;
		default:
			break;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.main_frag_container, frag);
		ft.commit();
	}

	public void onClick(View v) {
		if (v == btnYejie) {
			changeChannel(BaseChannelFrag.CHANNEL_YEJIE);
		} else if (v == btnMobile) {
			changeChannel(BaseChannelFrag.CHANNEL_MOBILE);
		} else if (v == btnCloud) {
			changeChannel(BaseChannelFrag.CHANNEL_CLOUD);
		} else if (v == btnPD) {
			changeChannel(BaseChannelFrag.CHANNEL_PD);
		}
	}
}
