package com.gyh.digou.shangjiamoshi;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gyh.digou.R;

public class DizhiAdapter extends BaseAdapter {

	
	
	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	public DizhiAdapter(Context mContext,List<JSONObject> list)
	{
		
		System.out.println(list.get(0).toString());
		this.mContext=mContext;
		this.list=list;
		inflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		//System.out.println(list.get(arg0).toString());
		arg1=inflater.inflate(R.layout.dizhi_styl, null);
		
		TextView t2=(TextView) arg1.findViewById(R.id.xingming_textView1);
		
		try {
			t2.setText(list.get(arg0).getString("consignee"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		TextView tv=(TextView) arg1.findViewById(R.id.dianhua_textView2);
		try {
			tv.setText(list.get(arg0).getString("phone_tel"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView t3=(TextView) arg1.findViewById(R.id.youbian_textView3);
		try {
			t3.setText(list.get(arg0).getString("zipcode"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView t4=(TextView) arg1.findViewById(R.id.dizhi_textView4);
		try {
			t4.setText(list.get(arg0).getString("address"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return arg1;
	}

}
