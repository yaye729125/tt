package com.gyh.digou.shouye;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.gyh.digou.shouye_search.ShouyeSearchActivity;
import com.gyh.digou.util.ACache;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.view.AbOnItemClickListener;
import com.gyh.digou.view.AbSlidingPlayView;
import com.gyh.digou.wode.ViewPagerAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;



public class ShouyeFragment extends Fragment{
	
	//private View mParent; 
	//public ImageView IVdicator[]; 
	ListView listView;
	public final int PICTURE = 0x1;
	public final int initRecommandFin=0x2;
	public final int initAdsFin=0x3;
	public static int mTimerNum = 0; 
    public Timer mTimer; 
    public TimerTask mTimerTask;  
   // List<HashMap<String,String>> ads=new ArrayList<HashMap<String,String>>();
    
    JSONArray list=new JSONArray();
    //ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	LayoutInflater inflater;
	Context mContex;
	MainActivity mainActivity;
	FinalBitmap imageLoader;
	
	
	PullToRefreshListView pullToRefreshListView;
	
	
	
   Handler mHandler = new Handler() {  
        public void handleMessage(Message msg) {  
            switch(msg.what){  
            case PICTURE:  
                   
                break; 
            case initRecommandFin:
            	if(is_refresh)
            		pullToRefreshListView.onRefreshComplete();
            	is_refresh=false;
            	break;
            	
            }  
            super.handleMessage(msg);  
        }  
    };
	  
    
    View headerView;
    LinearLayout noNetWorkLayout;
    AbSlidingPlayView viewPager ;

    public static ShouyeFragment sf;
    
    public static ShouyeFragment getInstance()
    {
    	if(sf==null)
    	{
    		sf=new ShouyeFragment();
    	}
    	return sf;
    }
    ACache  cache;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
	
		View fragmentView = inflater.inflate(R.layout.shouye, container, false);
		
		
		LinearLayout searchLayout=(LinearLayout) fragmentView.findViewById(R.id.shouye_search_layout);
		//editSearch.requestFocus();
		searchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Intent intent=new Intent(mContex,ShouyeSearchActivity.class);
				startActivity(intent);
				
			}
		});
		pullToRefreshListView = (PullToRefreshListView) fragmentView.findViewById(R.id.listView1);
		listView=pullToRefreshListView.getRefreshableView();
		noNetWorkLayout=(LinearLayout) fragmentView.findViewById(R.id.shouye_nonetwork);
		//pullToRefreshListView.setMode(Mode.MANUAL_REFRESH_ONLY);
		//listView=pullToRefreshListView.getRefreshableView();
		headerView=getActivity().getLayoutInflater().inflate(R.layout.list_header, null);
		viewPager = (AbSlidingPlayView)headerView.findViewById(R.id.list_header_viewPager_ad);
		//viewPager.setParentListView(listView);
		
		//MyListView listView=
		
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				//list.;
				is_refresh=true;
				initRecommand();
			}
		});
		//listView.seton
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(getActivity(),ItemDetailActivity.class);
				try {
					intent.putExtra("goods_id", list.getJSONObject(position-2).getString("goods_id"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//System.out.println(list.get(position-1).get("goods_id"));
				startActivity(intent);
			}
			
		});
		
		cache=ACache.get(mContex);
		
		
		if(list.length()<1)
		{
			
			if(NetworkUtil.isNetworkAvailable(mContex))
			{
				initAds();
				initRecommand();
			}else{
				
				list=cache.getAsJSONArray("shouye");
				initAdPager(cache.getAsString("shouyead"));
				//noNetWorkLayout.setVisibility(View.VISIBLE);
			}
		}else{
			
			viewPager.addViews(allListView);
			viewPager.startPlay();
		}
		listView.addHeaderView(headerView);
		listBaseAdapter=new MyListViewBaseAdapter(mContex);
		listBaseAdapter.setData(list);
		listView.setAdapter(listBaseAdapter);
		return fragmentView;
	}  
  
	

	public void initRecommand()
	{

		list=new JSONArray();
		AjaxParams params=new AjaxParams();
		params.put("recommend_id","20");
		FinalHttp fh=new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=search&act=api_recommend", params,new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				
				System.out.println(t);
				
				JSONObject jsonObject = null;
				
				try {
					
					jsonObject = new JSONObject(t);
					JSONObject dataObject = jsonObject.getJSONObject("data"); 
					
					String total = dataObject.getString("total"); 
					int count=Integer.parseInt(total);
					
					JSONObject listObject= (JSONObject) dataObject.get("list"); 
					Iterator itr=listObject.keys();
					while(itr.hasNext())
					{
						String key=(String) itr.next();
						JSONObject numObject=(JSONObject) listObject.get(key);
						/*String goods_name = numObject.getString("goods_name");
						String default_image = numObject.getString("default_image");
						String goods_id=numObject.getString("goods_id");
						System.out.println(numObject.get("goods_id"));
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("goods_name", goods_name);
						map.put("goods_id", goods_id);
						map.put("default_image", default_image);*/
						//list.add(numObject);
						list.put(numObject);
		
					}
					
					listBaseAdapter.setData(list);
					//JSONArray arry=new JSONArray(list);
					//arry.get
					//arry.
					
					//cache.put(key, value)
					
					mHandler.sendEmptyMessage(initRecommandFin);
					
					cache.put("shouye",list);//加入缓存
				} catch (JSONException e) {
					e.printStackTrace();
				}catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	
	MyListViewBaseAdapter listBaseAdapter;
	ArrayList<View>  allListView = new ArrayList<View>();
	
	boolean is_refresh=false;
	
	
	public void initAdPager(String t)
	{
		
		System.out.println(t);
		try {
			
			JSONObject json=new JSONObject(t);
			JSONObject data=json.getJSONObject("data");
			
			Iterator<String> itr=data.keys();
			
			if (allListView != null) {
				allListView.clear();
				//allListView = null;
			}else
			{
				allListView = new ArrayList<View>();
			}
			
			while(itr.hasNext())
			{
				String key=itr.next();
				JSONObject json_ad=data.getJSONObject(key);
				/*HashMap<String,String> map=new HashMap<String,String>();
				map.put("pic",json_ad.getString("pic"));
				ads.add(map);*/
				ImageView imageView=new ImageView(mContex);
				//imageView.setImageResource(resId[i]);
				//Bitmap image;
				
					//imageView.setImageBitmap(imageLoader.getBitmapFromMemoryCache(json_ad.getString("pic")));
				imageLoader.display(imageView,json_ad.getString("pic"));
				
				allListView.add(imageView);
				
			}
			
			viewPager.addViews(allListView);
			viewPager.startPlay();
			
			viewPager.setOnItemClickListener(new AbOnItemClickListener() {
				@Override
				public void onClick(int position) {
				
				}
			});
			
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		
	}
	public void initAds()
	{
		
		
		AjaxParams params2=new AjaxParams();
		
		
		FinalHttp fh2=new FinalHttp();
		fh2.post("http://www.cddego.com/api.php?app=goods&act=api_ads", params2,new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) {
				
			}

			@Override
			public void onSuccess(String t) {
				System.out.println(t);
				initAdPager(t);
				cache.remove("shouyead");
				cache.put("shouyead",t);
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
			
		});
		
	}
	
	
	ViewPagerAdapter adAdapter;
	
	/*int count;
	public void buildSmallCircle(View headerView)
	{
		
		
		count=ads.size();
		
		IVdicator = new ImageView[count]; 
        
		LayoutParams  mLayoutParams = new LinearLayout.LayoutParams(20, 20);  
          
      
        mLayoutParams.setMargins(7, 10, 7, 10);  
          
      
        LinearLayout Vdicator = (LinearLayout) headerView.findViewById(R.id.viewpager_indicator); 
        for(int i = 0; i <count; i++){  
             
        
            ImageView nowImageView = new ImageView(mContex);  
             
            nowImageView.setLayoutParams(mLayoutParams); 
            if(i==0)
            {
            	nowImageView.setBackgroundResource(R.drawable.indicator_focused);  
            }else {
            	nowImageView.setBackgroundResource(R.drawable.indicator);  
			}
            IVdicator[i] = nowImageView;  
              
           
            ((LinearLayout) Vdicator).addView(IVdicator[i]);  
        }
		
	}*/
	
	/*boolean timer_start=false;
	public void startTimer(){  
	    mTimer = new Timer();  
	    mTimerTask = new TimerTask(){  
	        @Override  
	        public void run() {  
	           // MyApp.ad_position++; 
	            mTimerNum++;
	          
	            mHandler.sendEmptyMessage(PICTURE);  
	        }  
	    };  
	      
	   
	    mTimer.schedule(mTimerTask, 3 * 1000, 3 * 1000); 
	    
	    timer_start=true;
	}*/  
	
	
	
	
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		mainActivity=(MainActivity)getActivity();
		imageLoader=mainActivity.getImageLoader();
		mContex=mainActivity;
	}
	
	@Override
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("myfragment","onactivitycreated");
	}	
	
	@Override

	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//mTimer.schedule(mTimerTask, 3 * 1000, 3 * 1000);
		Log.d("myfragment","onStart");
	}

	@Override
	
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		viewPager.startPlay();
		//mTimer.
		//startTimer();
		// mTimer.schedule(mTimerTask, 3 * 1000, 3 * 1000);
		Log.d("myfragment","onResume");
	}
	
	@Override
	
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		viewPager.stopPlay();
		//mTimer.cancel();
		Log.d("myfragment","onPause");
	}

	@Override
	
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//mTimer.cancel();
		Log.d("myfragment","onStop");
	}
	
	@Override
	
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		//mTimer.cancel();
		Log.d("myfragment","onDestroyView");
	}

	@Override
	
	public void onDestroy() {
	
		super.onDestroy();
		
	}
	
	@Override
	
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}  
	
}
