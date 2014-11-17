package com.gyh.digou.shangjiamoshi;

import com.gyh.digou.R;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Wodeshoucang extends Activity {
	ListView listView1,listView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wodeshoucang);
		
		listView1=(ListView) findViewById(R.id.list_shoucang_1);
	     listView2=(ListView) findViewById(R.id.list_shoucang_1);
	     
	     
	     
	     
	     
	     
	     
		
	}
           
	public void on1(View v) {
		listView1.setVisibility(v.VISIBLE);
		listView2.setVisibility(v.INVISIBLE);
		

	}
	public void on2(View v) {
		listView1.setVisibility(v.INVISIBLE);
		listView2.setVisibility(v.VISIBLE);
		
		
	}

	
	
}
