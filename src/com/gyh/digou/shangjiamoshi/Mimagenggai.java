package com.gyh.digou.shangjiamoshi;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Mimagenggai extends Activity{
	
	EditText editText1,editText2;
	Button button1,button2;
	String	 moble;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mimagenghuan);
		
		editText1=(EditText) findViewById(R.id.shouji_editText1);
		editText2=(EditText) findViewById(R.id.yanzhengma_editText2);
		button1=(Button) findViewById(R.id.huoquyanzhengma_button1);
		button2=(Button) findViewById(R.id.xiayibu_button2);
	
	// Toast.makeText(Mimagenggai.this, moble, 0).show();
			button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 moble=editText1.getText().toString();
				
					AjaxParams params = new AjaxParams();
					MyApp myApp = (MyApp)Mimagenggai. this.getApplication();
					String token = myApp.getInfo().getData().getToken();
					MyApp app = (MyApp) Mimagenggai.this.getApplication();
					
					//params.put("token", token);
	              params.put("mobile", moble);
                    
					FinalHttp fh = new FinalHttp();
					fh.post(Tools.getBaseUrl() +"?app=member&act=api_findpass_send_msg",
							params, new AjaxCallBack<String>() {

								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									Toast.makeText(Mimagenggai.this, strMsg,
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onSuccess(String t) {
									 System.out.println("---9-------"+t);
									 System.out.println(moble+"-------------55");
									 if(t.equals("0")){
											Toast.makeText(Mimagenggai.this, "发送成功", 0).show();
										}else{
											Toast.makeText(Mimagenggai.this, "发送失败", 0).show();
										}
							
								
								}
					
								});
				}
			});
			 
		}
		
}
