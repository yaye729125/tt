package com.gyh.digou.shangjiamoshi;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DizhiBianji extends Activity{
	Button baocun_button;
	FinalDb db;
	EditText xingming_editext,dianhua_editext,dizhi_editext,youbian_editext;
	Spinner diqu_spinner;
	Button baocun;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiugaishouhuodizhi);
		
	 //	db=FinalDb.create(this);		
		baocun=(Button) findViewById(R.id.baocun_button);
		xingming_editext=(EditText) findViewById(R.id.xingming_EditText);
		dianhua_editext=(EditText) findViewById(R.id.dianhua_EditText);
		diqu_spinner=(Spinner) findViewById(R.id.diqu_spinner);
		dizhi_editext=(EditText) findViewById(R.id.dizhi_EditText);
		youbian_editext=(EditText) findViewById(R.id.youbian_EditText);
		baocun_button=(Button) findViewById(R.id.baocun_button);
		Spinner spinner=(Spinner) findViewById(R.id.diqu_spinner);
		
		String[] list=new String[]{"北京","上海","成都"};
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
		
		spinner.setAdapter(adapter);
	
		
		   AjaxParams params=new AjaxParams();
			MyApp myApp=(MyApp)DizhiBianji.this.getApplication();
			String token=myApp.getInfo().getData().getToken();
			MyApp app=(MyApp) DizhiBianji.this.getApplication();

		params.put("region_id","0");
			
		FinalHttp fh=new FinalHttp();
		
fh.post(Tools.getBaseUrl()+"?app=mlselection&act=api_region",params,new AjaxCallBack<String>(){	       
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
		//	Toast.makeText(DizhiBianji.this,strMsg,Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onSuccess(String t) {	
				System.out.println("===23=="+t+"====");	
				
			
				
			}
		
			});

		
		
		
		
		
		
		
		
		
		
		
		
		baocun_button.setOnClickListener(new OnClickListener() {				
			public void onClick(View arg0) {
			String xingming=xingming_editext.getText().toString();
			String dianhua=dianhua_editext.getText().toString();
			//String diqu=diqu_spinner.getText().toString();
			String dizhi=dizhi_editext.getText().toString();
			String youbian=youbian_editext.getText().toString();	
			   AjaxParams params=new AjaxParams();
				MyApp myApp=(MyApp)DizhiBianji.this.getApplication();
				String token=myApp.getInfo().getData().getToken();
				MyApp app=(MyApp) DizhiBianji.this.getApplication();
	System.out.println(xingming+"333333333333333333333");
	  
	
			params.put("token",token);
			params.put("consignee",xingming );
			params.put("region_id", "4");
			params.put("region_name", "成都");
			params.put("address", dizhi);
			params.put("zipcode", youbian);
			params.put("phone_mob", dianhua);
			params.put("phone_tel", dianhua);		
			FinalHttp fh=new FinalHttp();
			
			fh.post(Tools.getBaseUrl()+"?app=my_address&act=api_add",params,new AjaxCallBack<String>(){	       
				public void onFailure(Throwable t, int errorNo,
						String strMsg) {
			//	Toast.makeText(DizhiBianji.this,strMsg,Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onSuccess(String t) {	
					System.out.println("====="+t+"====");	
					
					
					JSONObject jsonObject = null;
					String 	ErrNum = null;
					try {
						jsonObject = new JSONObject(t);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		     	try {
						ErrNum=	jsonObject.getString("ErrNum");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     	
		     int err=Integer.parseInt(ErrNum);
		     	
					if(err==0){
						 Toast.makeText(DizhiBianji.this, "保存成功", 0).show(); 
		
						 
								Intent intent=new Intent(DizhiBianji.this, Dizhiguanli.class);
							startActivity(intent);
							overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
							
				
						 
						 
					}else {
						Toast.makeText(DizhiBianji.this, "失败", 0).show(); 	
					}
					
					
				}
			
				});

	           
	
	
	
		      
			
				
			}
		});
	
	}

}
