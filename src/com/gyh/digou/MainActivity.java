package com.gyh.digou;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.gyh.digou.app.MyApp;
import com.gyh.digou.faxian.FaXianFragement;
import com.gyh.digou.fenlei.FenLeiFragement;
import com.gyh.digou.gouwuche.GouWuCheFragement;
import com.gyh.digou.shouye.ShouyeFragment;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.LoginFragment;
import com.gyh.digou.wode.WoDeFragement;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{
	private RadioGroup group;
	
	ShouyeFragment myFragment;
	FenLeiFragement fenLeiFragement;
	FaXianFragement faXianFragement;
	GouWuCheFragement gouWuCheFragement;
	WoDeFragement woDeFragement;
	LoginFragment loginFragment;
	FinalBitmap imageLoader;
	private String[] items = new String[]{"从文件中选择","拍照","取消"};
	
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";


	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	MyApp app;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
//      requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		app=(MyApp) getApplication();
		imageLoader=FinalBitmap.create(this);
		imageLoader.configMemoryCachePercent(30);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		initFragment();
		changeFragment(tag_shouye);
		group = (RadioGroup)findViewById(R.id.radioGroup);
		group.setOnCheckedChangeListener(this);
    }
	
	
	
	public FinalBitmap getImageLoader() {
		return imageLoader;
	}


	
	
	
	

	public void setImageLoader(FinalBitmap imageLoader) {
		this.imageLoader = imageLoader;
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		FragmentManager fragementManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragementManager.beginTransaction();
		switch (checkedId) {
		case R.id.first:
			
			
			beginTransaction.replace(R.id.frame,ShouyeFragment.getInstance());
			beginTransaction.commit(); 
			break;
		case R.id.second:
			beginTransaction.replace(R.id.frame,FenLeiFragement.getInstance());
			beginTransaction.commit(); 
			//changeFragment(tag_fenlei);
			
			break;
		case R.id.third:
			//changeFragment(tag_faxian);
			beginTransaction.replace(R.id.frame,FaXianFragement.getInstance());
			beginTransaction.commit(); 
			break;
		case R.id.fourth:
			if(app.isLogin())
			{
				beginTransaction.replace(R.id.frame,GouWuCheFragement.getInstance());
				beginTransaction.commit(); 
			}else
			{
				beginTransaction.replace(R.id.frame,new LoginFragment());
				beginTransaction.commit(); 
			}
			
			//changeFragment(tag_gouwuche);
			break;
		case R.id.fifth:
			if(app.isLogin())
			{
				beginTransaction.replace(R.id.frame,WoDeFragement.getInstance());
				beginTransaction.commit(); 
			}else
			{
				beginTransaction.replace(R.id.frame,new LoginFragment());
				beginTransaction.commit(); 
			}
			break;
		}
	}
	 
	String tag_shouye="shouye";
	String tag_fenlei="fenlei";
	String tag_faxian="faxian";
	String tag_gouwuche="gouwuche";
	String tag_wode="wode";
	String tag_login="login";
	
	public void changeFragment(String tag)
	{
		FragmentManager fragementManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragementManager.beginTransaction();
		if(tag_faxian.equals(tag))
		{
			beginTransaction.replace(R.id.frame, faXianFragement);
			beginTransaction.commit(); 
		}else if(tag_fenlei.equals(tag))
		{
			beginTransaction.replace(R.id.frame,fenLeiFragement);
			
			beginTransaction.commit();
		}else if(tag_gouwuche.equals(tag))
		{
			beginTransaction.replace(R.id.frame, gouWuCheFragement);
			beginTransaction.commit(); 
		}else if(tag_shouye.equals(tag))
		{
			beginTransaction.replace(R.id.frame, myFragment);
			beginTransaction.commit(); 
		}else if(tag_wode.equals(tag))
		{
			if(isLogin()){
				
				beginTransaction.replace(R.id.frame, woDeFragement);
				beginTransaction.commit(); 
			}else
			{
				
				beginTransaction.replace(R.id.frame, loginFragment);
				beginTransaction.commit();
			}
		}
		
	}
	
	
	//fragment singlestone
	public void initFragment()
	{
		FragmentManager fragementManager = getSupportFragmentManager();
		FragmentTransaction trans= fragementManager.beginTransaction();
		woDeFragement = new WoDeFragement();
		loginFragment=new LoginFragment();
		gouWuCheFragement = new GouWuCheFragement();
		faXianFragement = new FaXianFragement();
		fenLeiFragement = new FenLeiFragement();
		myFragment = new ShouyeFragment();
		trans.commit();
	}
	
	private boolean isLogin()
	{
		if(((MyApp)getApplication()).getInfo()!=null){
			
			return true;
		}else
		{
			return false;
		}
	}
	
    @Override  
    protected void onResume() {  
        super.onResume();  
    }  
      
    @Override  
    protected void onPause() {  
        super.onPause();  
    }
    @Override
	protected void onDestroy() {
		
		super.onDestroy();
	} 
    
    public String getImagePathFromUri(Uri fileUrl)
    {
       String fileName = null;
       Uri filePathUri = fileUrl;
       if (fileUrl != null)
       {
           if (fileUrl.getScheme().toString().compareTo("content") == 0)
           {
               // content://开头的uri
              Cursor cursor = getContentResolver().query(fileUrl, null, null, null, null);
              if (cursor != null && cursor.moveToFirst())
              {
                  int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                  fileName = cursor.getString(column_index); // 取出文件路径
 
                  // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                  if (!fileName.startsWith("/storage") && !fileName.startsWith("/mnt"))
                  {
                     // 检查是否有”/mnt“前缀
                     fileName = "/mnt" + fileName;
                  }
                  cursor.close();
              }
           }
           else if (fileUrl.getScheme().compareTo("file") == 0) // file:///开头的uri
           {
              fileName = filePathUri.toString();// 替换file://
              fileName = filePathUri.toString().replace("file://", "");
				int index = fileName.indexOf("/sdcard");
				fileName  = index == -1 ? fileName : fileName.substring(index);
 
             
              if (!fileName.startsWith("/mnt"))
              {
                  // 加上"/mnt"头
                  fileName = "/mnt"+fileName;
              }
           }
       }
       return fileName;
    }
    
    
    
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//RESULT_CANCELED
		if (resultCode != Activity.RESULT_CANCELED){

			switch (requestCode) {
			
			case IMAGE_REQUEST_CODE:
				//data.get
				imageFile=new File(getImagePathFromUri(data.getData()));
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				//Log.d("imageFile---",imageFile.toString());
				if (Tools.hasSdcard()) {

					
					System.out.println("app.getUri()"+app.getUri());
					startPhotoZoom(app.getUri());
					
					//Log.d("onactivityresult", "camera");
				
				}else {
					Toast.makeText(MainActivity.this,"未找到sd卡",
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				//Log.d("imageFile---",imageFile.toString());
				if (data != null) {
					getImageToView(data);
				}
				break;
				
			
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	Bitmap headImage;
	
	
	/**
	 *
	 * 
	 * @param picdata
	 */
	public  void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			headImage = extras.getParcelable("data");
			
			try {
			if (Tools.hasSdcard()) {
				imageFile=new File(Environment
						.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);
				
				FileOutputStream fos=new FileOutputStream(imageFile);
				headImage.compress(Bitmap.CompressFormat.PNG,50,fos);
				
			}/*else{
				
					fos=openFileOutput("image",0);
			}*/
			
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			
			
			Drawable drawable = new BitmapDrawable(headImage);
			WoDeFragement wd=WoDeFragement.getInstance();
			wd.imv_headImage.setImageDrawable(drawable);
			wd.imv_headImage.setPadding(10, 10, 10, 10);
			uploadPic();
			
			/*new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					 final Map<String, String> params = new HashMap<String, String>();
					 MyApp myApp=(MyApp)MainActivity.this.getApplication();
					 String token=myApp.getInfo().getData().getToken();
			         params.put("token",token);
			      
			        final Map<String, File> files = new HashMap<String, File>();
			        //File file=activity.getImageFile();
			         files.put("image",imageFile);
			         Log.d("imageFile",imageFile.getName());
					try {
						String result=mypost(Tools.getBaseUrl()+"?app=apply&act=api_upload_image", params, files);
						System.out.println(result);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).start();*/
			
		}
	}
	
	
	
	
	
	private  void  uploadPic() {
		AjaxParams params=new AjaxParams();
		
		MyApp myApp=(MyApp)MainActivity.this.getApplication();
		String token=myApp.getInfo().getData().getToken();
		params.put("token",token);
		try {
		params.put("image",imageFile);
		//params.p
		FinalHttp fh=new FinalHttp();
		fh.post(Tools.getBaseUrl()+"?app=apply&act=api_upload_image",params,new AjaxCallBack<String>()
				{

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						
					}
					@Override
					public void onSuccess(String t) {
						Toast.makeText(MainActivity.this,t, Toast.LENGTH_SHORT).show();
						System.out.println(t);
					}
			
				});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setHandler(Handler handler)
	{
		this.handler=handler;
	}
	
	Handler handler;
	public void showGetImageDialog() {

		AlertDialog dialog=new AlertDialog.Builder(this)
			
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*");
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							//
							if (Tools.hasSdcard()) {
								imageFile=new File(Environment
										.getExternalStorageDirectory(),
										IMAGE_FILE_NAME);
								cameraUri=Uri.fromFile(imageFile);
								app.setUri(cameraUri);
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,cameraUri
										//URI.
										);
							Log.d("imageFile---",imageFile.toString());
							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							}else{
								Toast.makeText(MainActivity.this,"未找到sd卡",Toast.LENGTH_SHORT).show();
							}
							break;
						case 2:
							dialog.cancel();
							break;
						}
					}
				}).create();
		Window window=dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		dialog.show();	
				

	}
	
	File imageFile;
	Uri  cameraUri;
	/**
	 *
	 * 
	 * @param uri
	 */
	public  void startPhotoZoom(Uri uri) {

		//Log.d("imageFile---",imageFile.toString());
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	
	
	
	
	
	
	
	/**
	  
	  * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	   
	  * @param actionUrl
	   
	  * @param params
	   
	  * @param files
	   
	  * @return
	   
	  * @throws IOException
	   
	  */
	   
	  public  String mypost(String actionUrl, Map<String, String> params, 
	  Map<String, File> files) throws IOException 
	  { 

	  String BOUNDARY = java.util.UUID.randomUUID().toString();
	   
	  String PREFIX = "--" , LINEND = "\r\n";
	   
	  String MULTIPART_FROM_DATA = "multipart/form-data"; 
	  String CHARSET = "UTF-8";
	   

	  URL uri = new URL(actionUrl); 
	  HttpURLConnection conn = (HttpURLConnection) uri.openConnection(); 
	  conn.setReadTimeout(5 * 1000); // 缓存的最长时间 
	  conn.setDoInput(true);// 允许输入 
	  conn.setDoOutput(true);// 允许输出 
	  conn.setUseCaches(false); // 不允许使用缓存 
	  conn.setRequestMethod("POST"); 
	  conn.setRequestProperty("connection", "keep-alive"); 
	  conn.setRequestProperty("Charsert", "UTF-8"); 
	  conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY); 

	  // 首先组拼文本类型的参数 
	  StringBuilder sb = new StringBuilder(); 
		  for (Map.Entry<String, String> entry : params.entrySet()) { 
				  sb.append(PREFIX); 
				  sb.append(BOUNDARY); 
				  sb.append(LINEND); 
				  sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
				   
				  sb.append("Content-Type: text/plain; charset=" + CHARSET+LINEND);
				   
				  sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				   
				  sb.append(LINEND);
				   
				  sb.append(entry.getValue()); 
				  sb.append(LINEND); 
		  } 

	  DataOutputStream outStream = new DataOutputStream(conn.getOutputStream()); 
	  outStream.write(sb.toString().getBytes()); 
	  
	  StringBuilder sb2 = new StringBuilder(); 
	  // 发送文件数据 
	  if(files!=null)
	  {
			  for (Map.Entry<String, File> file: files.entrySet()) { 
				  StringBuilder sb1 = new StringBuilder(); 
				  sb1.append(PREFIX); 
				  sb1.append(BOUNDARY); 
				  sb1.append(LINEND); 
				  sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getKey()+"\""+LINEND);
				   
				  sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
				   
				  sb1.append(LINEND);
				   
				  outStream.write(sb1.toString().getBytes()); 
			
				  InputStream is = new FileInputStream(file.getValue());
				   
				  byte[] buffer = new byte[1024]; 
				  int len = 0; 
				  while ((len = is.read(buffer)) != -1) { 
				  outStream.write(buffer, 0, len); 
				  } 
		
			  is.close(); 
			  outStream.write(LINEND.getBytes()); 
			  } 

	  //请求结束标志
	   
	  byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes(); 
	  outStream.write(end_data); 
	  outStream.flush();
	  }
	  // 得到响应码 
	  int res = conn.getResponseCode(); 
		  if (res == 200) {
		   
			  InputStream in = conn.getInputStream(); 
			  int ch; 
			 
			  while ((ch = in.read()) != -1) { 
			  sb2.append((char) ch); 
			  } 
		  }
	   
	  outStream.close(); 
	  conn.disconnect(); 
	  
	  
	  return sb2.toString(); 
	 
	  
	  
	  }
	
	
	
	
	
	
	
	
}