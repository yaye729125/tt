package com.gyh.digou.wode;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.R.id;
import com.gyh.digou.R.layout;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.shangjiamoshi.MessageAdapter;
import com.gyh.digou.shangjiamoshi.Wodexiaoxi;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DingDanGuanLiActivity extends Activity{
	
	/**
	 * @author Administrator
	 * @param savedInstanceState
	 */

	ListView listView1,listView2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdanguanli);
		
		listView1=(ListView) findViewById(R.id.list_dingdan_1);
	     listView2=(ListView) findViewById(R.id.list_dingdan_2);
	     
	     AjaxParams params = new AjaxParams();
			MyApp myApp = (MyApp) this.getApplication();
			String token = myApp.getInfo().getData().getToken();
			MyApp app = (MyApp) DingDanGuanLiActivity.this.getApplication();
			params.put("token", token);
			params.put("type", "all");

			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=buyer_order&act=api_orders",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							Toast.makeText(DingDanGuanLiActivity.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {
							 System.out.println("---87-------"+t+"--");
							 
							try {
//
//								JSONObject jsonObject = new JSONObject(t);
//								String ErrNum = jsonObject.getString("ErrNum");
//								String ErrMsg = jsonObject.getString("ErrMsg");
//								JSONObject datajson = jsonObject
//										.getJSONObject("data");
//								// String total= datajson.getString("total");
//
//								JSONObject listJson = datajson
//										.getJSONObject("list");
//								ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
//								// warrlist.add((JSONObject) object);
//								Iterator<String> it = listJson.keys();
//								while (it.hasNext()) {
//									String key = it.next();
//									JSONObject object = listJson.getJSONObject(key);
//									warrlist.add(object);
//								}
//
//								MessageAdapter adapter = new MessageAdapter(
//										DingDanGuanLiActivity.this, warrlist);
//
//								listview1.setAdapter(adapter);

							} catch (Exception e) {
								// TODO: handle exception

							}

						}

					});	
	     
	     
	     
	     
	     
	     
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
