package com.gyh.digou.fenlei;


import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.gson.Gson;
import com.gyh.digou.R;
import com.gyh.digou.bean.GoodsCategory;
import com.gyh.digou.bean.GoodsCategoryInfo;


public class FenLeiFragement extends Fragment{

	ListView listViewItem ;
	ListView listViewXiangXi;
	
	List<GoodsCategory> list;
	GoodsCategoryInfo info;
	ChildrenListViewBaseAdapter childrenListViewBaseAdapter;
	RadioGroup group;
	int cur_position;
	public static  FenLeiFragement ff;
	public static  FenLeiFragement getInstance()
	{
		if(ff==null)
		{
			ff=new FenLeiFragement();
		}
		return ff;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fenlei_fragment, container, false);
		listViewItem = (ListView) view.findViewById(R.id.listView_item);
		listViewXiangXi = (ListView) view.findViewById(R.id.listView_xiangxi);
		goodsCategory();
		listViewItem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				childrenListViewBaseAdapter.setPosition(arg2);
				listViewItem.setSelection(arg2);
				parentAdapter.setPosition(arg2);
			}
		});
		
		
		listViewXiangXi.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				//horizatal expandableListview
				
				Intent intent = new Intent();
				
				GoodsCategory category=((ChildrenListViewBaseAdapter)listViewXiangXi.getAdapter()).getAdapterData().get(arg2);
				
				//int positon=((ChildrenListViewBaseAdapter)listViewXiangXi.getAdapter()).getPosition();
				//intent.putParcelableArrayListExtra("list",(ArrayList<? extends Parcelable>) info.getData().get(positon).getChildren().get(arg2).getChildren());
				intent.setClass(getActivity(), FenLeiChooseResultActivity.class);
				intent.putExtra("fenlei_search_cate_id",category.getCate_id());
				 
				startActivity(intent);
				
				
			}
		});
		
		return view;
	}

	FenLeiListItemBaseAdapter parentAdapter;	
	public void goodsCategory(){
		AjaxParams params=new AjaxParams();
		FinalHttp fh=new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=category&act=api_goods", params,new AjaxCallBack<String>() {
			
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				int position = 0;
				Gson gson=new Gson();
				// �����ӵ�json���
			    info = gson.fromJson(t, GoodsCategoryInfo.class);
				System.out.println(t==null?"null":t);
				System.out.println("info------------------------------>>>>"+info);
				System.out.println("================..............."+ info.getData().get(position).getChildren());
				parentAdapter=new FenLeiListItemBaseAdapter(getActivity(),info);
				listViewItem.setAdapter(parentAdapter);
				listViewItem.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				childrenListViewBaseAdapter = new ChildrenListViewBaseAdapter(getActivity(), info,0);
				listViewXiangXi.setAdapter(childrenListViewBaseAdapter);
				//listViewXiangXi.setAdapter(new ChildListViewBaseAdapter(getActivity(), info));
				
			}
		});
	}
}
