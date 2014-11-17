package com.gyh.digou.app;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.gyh.digou.bean.LoginInfo;


public class MyApp extends Application {

	
	
	//public static int ad_position=0;
	//private String token;
	//private LoginInfo info;
	LoginInfo info=null;
	public boolean isLogin()
	{
		
		ObjectInputStream ois;
		
		try {
			ois = new ObjectInputStream(openFileInput("login"));
			info=(LoginInfo) ois.readObject();
			ois.close();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public LoginInfo getInfo() {
		/*ObjectInputStream ois;
		LoginInfo info=null;
		try {
			ois = new ObjectInputStream(openFileInput("login"));
			info=(LoginInfo) ois.readObject();
			ois.close();
			
			
			
			
		} catch (StreamCorruptedException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}*/
		
		
		return info;
	}

	
	public Uri cameraUri;
	public void setUri(Uri cameraUri)
	{
		this.cameraUri=cameraUri;
	}
	public Uri getUri()
	{
		return cameraUri;
	}
	public void setInfo(LoginInfo info) {
		
		//this.info = info;
		
		try {
			ObjectOutputStream oos=new ObjectOutputStream(openFileOutput("login",Context.MODE_PRIVATE));
			oos.writeObject(info);
			oos.flush();
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
	
	}

	
	
	
	
	
	
}
