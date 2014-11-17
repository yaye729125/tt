package com.gyh.digou.shangjiamoshi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;


import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dizhiguanli extends Activity {
	Button tianjiadizhi_button;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dizhiguanli);

		tianjiadizhi_button = (Button) findViewById(R.id.tianjiadizhi_button);
		tianjiadizhi_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(Dizhiguanli.this, DizhiBianji.class);
				startActivity(intent);
			}
		});
		 listView = (ListView) findViewById(R.id.dizhiguanli_listview);
		AjaxParams params = new AjaxParams();
		MyApp myApp = (MyApp) this.getApplication();
		String token = myApp.getInfo().getData().getToken();
		MyApp app = (MyApp) Dizhiguanli.this.getApplication();
		params.put("token", token);

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=my_address&act=api_address", params,
				new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Dizhiguanli.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {

						try {

							JSONObject jsonObject = new JSONObject(t);
							String ErrNum = jsonObject.getString("ErrNum");
							String ErrMsg = jsonObject.getString("ErrMsg");
							// String data= jsonObject.getString("data");

							JSONObject listobject = jsonObject
									.getJSONObject("data");
							ArrayList<JSONObject> warrlists = new ArrayList<JSONObject>();

							Iterator<String> it = listobject.keys();
							while (it.hasNext()) {
								String key = it.next();
								JSONObject object = listobject
										.getJSONObject(key);
								warrlists.add(object);
							}

							
							DizhiAdapter adapter = new DizhiAdapter(
									Dizhiguanli.this, warrlists);

							listView.setAdapter(adapter);

						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				});
		
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				
				
				AlertDialog.Builder builde = new Builder(Dizhiguanli.this);
				
				builde.setTitle("´ð°¸");
				builde.setMessage("±à¼­");
				AlertDialog alertDialog00a2 = builde.create();
				alertDialog00a2.show();
				
				
				
				return false;
			}
		});
		
		
		
		
		
		
		

	}

}
