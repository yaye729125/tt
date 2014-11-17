package com.gyh.digou.shangjiamoshi;

import java.util.ArrayList;
import java.util.HashMap;

import com.gyh.digou.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Dingdangunali extends Activity{
	 
	       String[] hao=new String[]{"22"};
	       String[] jine=new String[]{"ff"};
	       String[] shijian=new String[]{"ffs"};
	       int [] image=new int[]{R.drawable.ic_launcher};
	       String [] miaoshu=new String[]{"ddsds"};
	       String [] zhuangtai=new String[]{"fdfd"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdan);
		ListView listView=(ListView) findViewById(R.id.dingdan_listView1);
		ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		for(int x=0;x<=7;x++){
		HashMap<String, Object> map=new HashMap<String, Object>();
		
	   map.put("dingdanhao",hao[0]);
	   map.put("dingdanjine", jine[0]);
	   map.put("xiadangshijian", shijian[0]);
	   map.put("image",image[0]);
	   map.put("mioashu",miaoshu[0]);
	   map.put("zhuangtai", zhuangtai[0]);
		list.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(Dingdangunali.this, list, R.layout.shangjiadingdanguanli_styl, new String[]{"dingdanhao","dingdanjine","xiadangshijian","image","mioashu","zhuangtai"}, new int[]{R.id.dingdan_text,R.id.jine_text,R.id.shijian_text,R.id.miaoshu_im,R.id.miaoshu_text,R.id.zhuangtai_text});
		
		listView.setAdapter(adapter);
	}

}
