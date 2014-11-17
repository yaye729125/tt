package com.gyh.digou.shangjiamoshi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

import com.gyh.digou.R;

public class Shangjia extends Activity{
	Button caigou_button;
	Button dingdan_button;
	Button zhuanghuguanli_button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.shangjiamoshi);
	caigou_button=(Button) findViewById(R.id.shangjia_caigou);
	caigou_button.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
			
		}
	});
	
	dingdan_button=(RadioButton) findViewById(R.id.dingdanguanli_button);
	dingdan_button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		   Intent intent=new Intent(Shangjia.this,Dingdangunali.class);
		   startActivity(intent);
			
		}
	});
	zhuanghuguanli_button=(RadioButton) findViewById(R.id.zhanghuguanli_button);
	zhuanghuguanli_button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			  Intent intent=new Intent(Shangjia.this, Zhanhuguanli.class);
			  startActivity(intent);
			  }
	});
	}
	

}
