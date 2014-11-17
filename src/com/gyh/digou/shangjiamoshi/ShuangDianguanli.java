package com.gyh.digou.shangjiamoshi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.google.gson.Gson;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ShuangDianguanli extends Activity{
	
	String jieguo;
	TableRow tabrow;
	TextView shuangdianshengyu_text;
	EditText tuisong_editText1;
	Button baocun_button1;
	String tuisongxinxi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shuangdianguanli);
		tabrow=(TableRow) findViewById(R.id.shuangdianchongzhi_tab);
		tabrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(ShuangDianguanli.this, Shuangdianchongzhi.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
	
		shuangdianshengyu_text=(TextView) findViewById(R.id.shuangdianshengyu_text);
		
			
		
//				AjaxParams params=new AjaxParams();
//				params.put("token","token");
//				FinalHttp fh=new FinalHttp();
//				fh.post(Tools.getBaseUrl()+"?app=my_point&act=api_point",params,new AjaxCallBack<String>(){
//		       
//					public void onFailure(Throwable t, int errorNo,
//							String strMsg) {
//						Toast.makeText(ShuangDianguanli.this,strMsg,Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void onSuccess(String t) {
//						
//						System.out.println("----------"+t+"----------------------");
//						  Toast.makeText(ShuangDianguanli.this, t, 0).show();
//						
//					}
//					
//				});
//			       
		
		
		
		
		tuisong_editText1=(EditText) findViewById(R.id.tuisong_editText1);
		 tuisongxinxi=tuisong_editText1.getText().toString();
		 
		baocun_button1=(Button) findViewById(R.id.baocun_button1);
		baocun_button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				
				Toast.makeText(ShuangDianguanli.this, "推送成功", 0).show();
				tuisong_editText1.setText("");
				}
			
		});	
		
		
		MyApp app=(MyApp) this.getApplication();
		final String token=app.getInfo().getData().getToken();
		
		new Thread(new Runnable(){
			JSONObject dataObject;
			@Override
			public void run() {
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("token",token));
				final String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=my_point&act=api_point", params);
				System.out.println(result+"------------100"); 
				
				JSONObject jsonObject = null;
				try{
				jsonObject = new JSONObject(result);
				dataObject = jsonObject.getJSONObject("data"); // ��ö����е�data����
				ShuangDianguanli.this.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						try {
							shuangdianshengyu_text.setText(dataObject.getString("point"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}});	
//				String total = dataObject.getString("total"); // ���data�����е�һ��ֵ
//				int count=Integer.parseInt(total);
//				String point = dataObject.getString("point"); // ���data�����е�һ��ֵ
//				int count2=Integer.parseInt(point);
//				
				
//				JSONObject listObject= (JSONObject) dataObject.get("list");
//				Iterator itr=listObject.keys();
//				
//			
//				while(itr.hasNext())
//				{
//					String key=(String) itr.next();
//					JSONObject numObject=(JSONObject) listObject.get(key);
//					String point_id = numObject.getString("point_id");
//					String user_id = numObject.getString("user_id");
//					String type=numObject.getString("type");
//					String target_id=numObject.getString("target_id");
//					String points=numObject.getString("point");
//					String time=numObject.getString("time");
//					String memo=numObject.getString("memo");
//				System.out.println(point_id+"--------"+user_id+"==");
//				}
				
				
	
				
				
//				System.out.println(count+"---"+count2+"---------------");
//				 String shuandian=  String.valueOf(count2);
//				          
				      
				}catch (JSONException e) {
					e.printStackTrace();
				}catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		
		
		
//		 AjaxParams params=new AjaxParams();
//			MyApp myApp=(MyApp)this.getApplication();
//			String token=myApp.getInfo().getData().getToken();
//		//	String id=myApp.getInfo().getData().msg_id;
//		
//			MyApp app=(MyApp) this.getApplication();
//			
//			params.put("token",token);
//		//	params.put("msg_id", "id");
//			FinalHttp fh=new FinalHttp();
//			fh.post(Tools.getBaseUrl()+"?app=my_point&act=api_point",params,new AjaxCallBack<String>(){
//	       
//				public void onFailure(Throwable t, int errorNo,
//						String strMsg) {
//					Toast.makeText(ShuangDianguanli.this,strMsg,Toast.LENGTH_SHORT).show();
//				}
//
//				@Override
//				public void onSuccess(String t) {
//					
//					System.out.println("---8-------"+t+"-----8-----8------------");
//					  Toast.makeText(ShuangDianguanli.this, t, 0).show();
//					
//				}
//				
//			});
//
//			
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
		
		
		
		
		
		
//	new Thread(new Runnable() {
//		
//		@Override
//		public void run() {
//			
//			 String url = "http://www.cddego.com/api.php?app=my_point&act=api_point";
//			  HttpPost httpPost = new HttpPost(url); 
//			  List<NameValuePair> params = new ArrayList<NameValuePair>(); 
//		        params.add(new BasicNameValuePair("token", "token"));
//		        HttpResponse httpResponse = null; 
//		        try {
//					httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//	            try {
//					httpResponse = new DefaultHttpClient().execute(httpPost);
//				} catch (ClientProtocolException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//	            if (httpResponse.getStatusLine().getStatusCode() == 200) { 
//			
//			
//	                String result = null;
//					try {
//						result = EntityUtils.toString(httpResponse.getEntity());
//						jieguo=result;
//						
//						System.out.println("result"+"-----------------------------------8");
//						
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//	                System.out.println("------------------result-----------------------" + result); 
//	                
//			
//			
//	            }
//			
//		}
//	}).start();
	

	}   
}
