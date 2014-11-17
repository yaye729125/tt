package com.gyh.digou.gouwuche;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.FinalDb;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Cart;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.bean.ShopOwner;
import com.gyh.digou.gouwuche.ShakeListener.OnShakeListener;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

public class GouWuCheFragement extends Fragment{

	
	Context mContext;
	FinalDb fb;
	List<ShopOwner> listShopOwner;
	List<Cart> listCart;
	List<JSONObject> cartInfoList=new ArrayList<JSONObject>();
	TextView tv_all_price;
	TextView btn_jiesuan;
	Vibrator mVibrator;	
	SoundPool sndPool;
	Activity activity;
	
	public static GouWuCheFragement gf;
	public static GouWuCheFragement getInstance()
	{
		if(gf==null)
		{
			gf=new GouWuCheFragement();
		}
		return gf;
	}
	
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(mShakeListener!=null)
			mShakeListener.stop();
	}
	
	
	@Override
	public void onPause() {
		
		super.onPause();
	
		
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	public void onStop() {
		
		super.onStop();

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mContext=activity=getActivity();
		
		mVibrator = (Vibrator)activity.getApplication().getSystemService(Activity.VIBRATOR_SERVICE);
		
		
        //mShakeListener.stop();
		/*fb=FinalDb.create(mContext,"digou.db");
		listShopOwner=fb.findAll(ShopOwner.class);
		listCart=fb.findAll(Cart.class);*/
		//fb.
		
	}
	private void loadSound() {

		sndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
		new Thread() {
			public void run() {
				try {
					soundPoolMap.put(
							0,
							sndPool.load(activity.getAssets().openFd(
											"shake_sound_male.mp3"), 1));

					soundPoolMap.put(
							1,
							sndPool.load(activity.getAssets().openFd(
											"shake_match.mp3"), 1));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	FrameLayout frameLayout ;
    ImageView guideImage;
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	
	private final static int refreshData=0x1;
	private final static int refreshComplete=0x2;
	 LoginInfo info = null;
	 Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what)
			{
			case refreshData:
				refreshData();
				break;
			case refreshComplete:
				pullToRefreshExpandableListView.onRefreshComplete();
				break;
			}
			//
			
		}
	};
	
	
	private static final int getcartInfoOk=0;
	private static final int getcartInfofail=-1;
	HashMap<String,JSONObject> cartMapInfo=new HashMap<String,JSONObject>();
	PullToRefreshExpandableListView pullToRefreshExpandableListView;
	ExpandableListView cartInfoListView;
	CartInfoAdapter cartInfoAdapter;
	String token;
	private String[] items = new String[] {"删除","取消" };
	AlertDialog cart_dialog;
	Resources res;
	ShakeListener mShakeListener;
	boolean is_refresh=false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cart, container, false);
		
		
		pullToRefreshExpandableListView=(PullToRefreshExpandableListView)view.findViewById(R.id.cart_expandlistview);
		cartInfoListView=pullToRefreshExpandableListView.getRefreshableView();
		
		pullToRefreshExpandableListView.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {

			@Override
			public void onRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				is_refresh=true;
				refreshCartInfo();
			}
		});
		tv_all_price=(TextView) view.findViewById(R.id.cart_bottom_heji_content);
		btn_jiesuan=(TextView) view.findViewById(R.id.cart_bottom_btn_jiesuan);
		res=getResources();//.getString(R.string.cart_jiesuan_btntv, formatArgs)
		//CheckBox cart_all_checkbox=(CheckBox) view.findViewById(R.id.cart_all_checkbox);
		
		
		loadSound();
		
		mShakeListener = new ShakeListener(mContext);
        mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				mShakeListener.stop();
				sndPool.play(soundPoolMap.get(0), (float) 1, (float) 1, 0, 0,(float) 1.2);
				MyApp app=(MyApp) activity.getApplication();
				final String token=app.getInfo().getData().getToken();
				//AjaxParams params=new AjaxParams("token",token);
				
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						List<NameValuePair> params=new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("token",token));
						final String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=cart&act=api_cart_shake", params);
						activity.runOnUiThread(new Runnable()
						{
							@Override
							public void run() {
								frameLayout.removeView(guideImage);
								Toast.makeText(mContext,result, Toast.LENGTH_SHORT).show();
								shakeRefreshData();
							}
							
						});
						
					}
					
				}).start();
				
			}
        });
		
		
		
		Button btn_shake=(Button) view.findViewById(R.id.cart_btn_shake);
		btn_shake.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				View myView=activity.getWindow().getDecorView().findViewById(R.id.cart_content);
				ViewParent viewParent=myView.getParent();
				if(viewParent instanceof FrameLayout)
				{
					 frameLayout = (FrameLayout)viewParent;
			         guideImage = new ImageView(mContext);
		             FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		             guideImage.setLayoutParams(params);
		             guideImage.setScaleType(ScaleType.FIT_XY);
		             guideImage.setImageResource(R.drawable.shake);
		             guideImage.setOnClickListener(new View.OnClickListener() {
		                 @Override
		                 public void onClick(View v) {
		                     frameLayout.removeView(guideImage);
		                     mShakeListener.stop();
		                 }
		             });
		             frameLayout.addView(guideImage);
				}
				mShakeListener.start();
			}
		});
		/*cartInfoListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				cartInfoList.clear();
				initData();
				cartInfoListView.onRefreshComplete();
			}
		});*/
		cartInfoListView.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					final int arg2, final int arg3, long arg4) {
				
				Toast.makeText(mContext,"child click",Toast.LENGTH_SHORT).show();
				
				
				cart_dialog=new AlertDialog.Builder(mContext)
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							
							JSONObject good;
							try {
								good = cartInfoList.get(arg2).getJSONArray("goods").getJSONObject(arg3);
							
								final String rec_id=good.getString("rec_id");
							
								deleteItem(rec_id, arg2, arg3);
							} catch (JSONException e) {
							
								e.printStackTrace();
							}
							break;
						case 1:
							activity.runOnUiThread(new Runnable()
							{

								@Override
								public void run() {
									cart_dialog.dismiss();
									
								}
								
							});
							break;
						}
					}
				}).create();
				
				
				Window window=cart_dialog.getWindow();
				window.setGravity(Gravity.BOTTOM);
				cart_dialog.show();
				
				
				return true;
			}
			
		});
				
		if(cartInfoList.size()<1)
		{
			initData();
		}else
		{
			cartInfoListView.setAdapter(cartInfoAdapter);
			cartInfoAdapter.setHandler(handler);
			
			for(int i=0;i<cartInfoList.size();i++)
			{
				cartInfoListView.expandGroup(i);
			}
			refreshData();
		}
		
		return view;
	}
	
	protected void refreshCartInfo() {
		cartInfoList.clear();
		initData();
		//pullToRefreshExpandableListView.onRefreshComplete();
		
	}


	public void shakeRefreshData()
	{
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				
				
				MyApp myApp=(MyApp) activity.getApplication();
				token=myApp.getInfo().getData().getToken();
				params2.add(new BasicNameValuePair("token",token));
				String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=cart&act=api_cart", params2);
				System.out.println(result);
					
					try{
						
						cartInfoList.clear();
						JSONObject cartInfo=new JSONObject(result);
						
						JSONObject data=cartInfo.getJSONObject("data");
					
						Iterator<String> itr=data.keys();
						
						while(itr.hasNext())
						{
							
							String key=itr.next();
							JSONObject jsonObject=data.getJSONObject(key);
							cartInfoList.add(jsonObject);
							
						}
					
						activity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								cartInfoAdapter.setCartData(cartInfoList);
							}
						});
					}catch (Exception e) {
						// TODO: handle exception
					}
			}
		}).start();
		
	}
	
	public void initData()
	{
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				
				MyApp myApp=(MyApp) activity.getApplication();
				token=myApp.getInfo().getData().getToken();
				params2.add(new BasicNameValuePair("token",token));
				String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=cart&act=api_cart", params2);
				System.out.println(result);
					
					try{
						
						JSONObject cartInfo=new JSONObject(result);
						
						JSONObject data=cartInfo.getJSONObject("data");
					
						Iterator<String> itr=data.keys();
						
						while(itr.hasNext())
						{
							
							String key=itr.next();
							JSONObject jsonObject=data.getJSONObject(key);
							cartInfoList.add(jsonObject);
							
						}
						if(is_refresh)
						{
							handler.sendEmptyMessage(refreshComplete);
							//pullToRefreshExpandableListView.onRefreshComplete();
							is_refresh=false;
						}
						activity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								
								//cartInfoAdapter.setCartData(cartInfoList);
								cartInfoAdapter=new CartInfoAdapter(mContext, cartInfoList);
								cartInfoListView.setAdapter(cartInfoAdapter);
								cartInfoAdapter.setHandler(handler);
								
								for(int i=0;i<cartInfoList.size();i++)
								{
									cartInfoListView.expandGroup(i);
								}
								refreshData();
							}
						});		
						
				}catch (Exception e) {
					if(is_refresh)
					{
						handler.sendEmptyMessage(refreshComplete);
						//pullToRefreshExpandableListView.onRefreshComplete();
						is_refresh=false;
					}
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		
	}
	public void deleteItem(final String rec_id,final int arg2,final int arg3)
	{
		
		new Thread(new Runnable()
		{

			@Override
			public void run() {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				
				
				params.add(new BasicNameValuePair("token",token));
				params.add(new BasicNameValuePair("rec_id",rec_id));
				
				
				
				String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=cart&act=api_drop", params);
				
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
							List<JSONObject> list=new ArrayList<JSONObject>();
							
							JSONArray jsonArray;
							try {
								jsonArray = (JSONArray)cartInfoList.get(arg2).getJSONArray("goods");
							
								//cartInfoAdapter.getCheckList().get(arg2).remove(arg3);
								JSONArray n_jsonArray = new JSONArray(); 
								int len = jsonArray.length();
								if (jsonArray != null) { 
								 for (int i=0;i<len;i++)
								 { 
								
									  if (i != arg3) 
									  {
										  n_jsonArray.put(jsonArray.get(i));
									  }
								 } 
								}
								
								cartInfoList.get(arg2).remove("goods");
								if(n_jsonArray.length()==0)
								{
									cartInfoList.remove(arg2);
								}else
								{
									cartInfoList.get(arg2).put("goods",n_jsonArray);
								}
							
							} catch (JSONException e) {
								
								e.printStackTrace();
							} 
						cartInfoAdapter=new CartInfoAdapter(mContext, cartInfoList);
						cartInfoListView.setAdapter(cartInfoAdapter);
						refreshData();
						/*cartInfoAdapter.setCartData(cartInfoList);
						System.out.println("after setadapter");*/
						
						
						for(int i=0;i<cartInfoList.size();i++)
						{
							cartInfoListView.expandGroup(i);
						}
						cart_dialog.dismiss();
					}
				});	
				
				
				
			}
			
		}).start();
		
	}
	public void refreshData() {
		
		List<JSONObject> benList=cartInfoAdapter.getData();
		
		int count=0;
		double prices=0.00;
		for(int i=1;i<=benList.size();i++)
		{
			try {
				
				
			JSONObject jsonObj_w=benList.get(i-1);
			JSONArray arry=jsonObj_w.getJSONArray("goods");
			if(jsonObj_w.getBoolean("check"))
			{
				for(int j=0;j<arry.length();j++)
				{
					
						JSONObject json=arry.getJSONObject(j);
						int num=json.getInt("quantity");
						double price=json.getDouble("price");
						count+=num;
						prices+=num*price;
				
				}
			}
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		String pricesStr=prices+"000";
		String pricesSub=pricesStr.substring(0,pricesStr.indexOf(".")+3);
		tv_all_price.setText(pricesSub);
		btn_jiesuan.setText("结算("+count+")");
		
	}

	public static final String removeBOM(String data) {
		if (TextUtils.isEmpty(data)) {
		return data;
		}

		if (data.startsWith("\ufeff")) {
			return data.substring(1);
		} 
		else
		{
			return data;
		}
	}
	
	
	
	
	
	
}
