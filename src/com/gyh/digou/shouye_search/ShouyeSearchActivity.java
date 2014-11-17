package com.gyh.digou.shouye_search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.gyh.digou.R;
import com.gyh.digou.R.id;
import com.gyh.digou.R.layout;
import com.gyh.digou.fenlei.BounceListView;
import com.gyh.digou.fenlei.PullListView;

public class ShouyeSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shouye_search);
		//ListView list=(ListView) findViewById(R.id.shouye_search_list);
		
		AutoCompleteTextView search=(AutoCompleteTextView) findViewById(R.id.shouye_edit_search);
		//PullListView mBounceLv = (PullListView)findViewById(R.id.shouye_search_list);
			search.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getData()));
	    }
	    private List<String> getData(){
			
			List<String> data = new ArrayList<String>();
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			
			return data;
		}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		return super.onKeyDown(keyCode, event);
	}

	
	
	
}
