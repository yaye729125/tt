package com.gyh.digou.shangjiamoshi;

import java.util.ArrayList;
import java.util.HashMap;

import com.gyh.digou.R;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

  
public class Zhanhuguanli extends Activity{
	ListView listView;
	
	String [] ss=new String[]{"地址管理","密码管理","资金详情","资金提现"};
	int [] im=new int[]{R.drawable.ic_arrow,R.drawable.ic_arrow,R.drawable.ic_arrow,R.drawable.ic_arrow};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhanhuguanli);
		listView=(ListView) findViewById(R.id.zhanhu_listView1);
		  ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		  for(int x=0;x<ss.length;x++){
		  HashMap<String, Object> map=new HashMap<String, Object>();
		  map.put("text", ss[x]);
		  map.put("image",  im[x]);
		  list.add(map);
		  
		  }
		SimpleAdapter adapter=new SimpleAdapter(Zhanhuguanli.this,list, R.layout.zhanhu_styl, new String[]{"text","image"}, new int[]{R.id.textView1,R.id.imageView1});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			}
		});
		
	}

}
