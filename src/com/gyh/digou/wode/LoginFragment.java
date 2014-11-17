package com.gyh.digou.wode;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.google.gson.Gson;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.util.Tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.login,container,false);
		
		Button login_btn=(Button) view.findViewById(R.id.login_btn);
		final EditText edit_username=(EditText) view.findViewById(R.id.login_username);
		final EditText edit_password=(EditText) view.findViewById(R.id.login_password);
		
		login_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String password=edit_password.getText().toString();
				String user_name=edit_username.getText().toString();
				
				AjaxParams params=new AjaxParams();
				params.put("user_name","zertul999");
				params.put("password","123456");
				FinalHttp fh=new FinalHttp();
				fh.post(Tools.getBaseUrl()+"?app=member&act=api_login",params,new AjaxCallBack<String>(){

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(getActivity(),strMsg,Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						System.out.println(t);
						Gson gson=new Gson();
					
						LoginInfo info=gson.fromJson(t, LoginInfo.class);
						
						MyApp app=((MyApp)getActivity().getApplication());
						
						app.setInfo(info);
						Toast.makeText(getActivity(),info.getErrMsg(),Toast.LENGTH_SHORT).show();
						
					}
					
				});
			}
		});
		return view;
	}

	
	
	
	
}
