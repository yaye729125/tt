package com.gyh.digou.fenlei;

import java.util.List;

import com.gyh.digou.R;
import com.gyh.digou.bean.GoodsCategory;
import com.gyh.digou.bean.GoodsCategoryInfo;

import net.tsz.afinal.FinalBitmap;
import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ChildrenListViewBaseAdapter extends BaseAdapter{
	@SuppressWarnings("unused")
	private Context mContext;
	private LayoutInflater inflater = null;
	GoodsCategoryInfo info;
	FinalBitmap imageLoader;
	List<GoodsCategory> list;
	int position;
	public ChildrenListViewBaseAdapter() {}
	public ChildrenListViewBaseAdapter(Context mContext, GoodsCategoryInfo info, int position){
		this.mContext = mContext;
		this.info = info;
		this.position = position;
		inflater = LayoutInflater.from(mContext);
		list = info.getData().get(position).getChildren();
		System.out.println("list========" + list);
		imageLoader = FinalBitmap.create(mContext);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
	}

	
	// 获取列表项的总数
	@Override
	public int getCount() {
		return list.size();
	}

	// 获取每一个列表项
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	// 获取每一个列表项的Id
	@Override
	public long getItemId(int position) {
		return position;
	}

	// 创建一个视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	   	ViewHolder viewHolder = new ViewHolder();
		if (null == convertView) {
	   		convertView = inflater.inflate(R.layout.listview_xiangxi, null);

			System.out.println("convertView============="+convertView);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
	   		viewHolder.textView= (TextView) convertView.findViewById(R.id.textView1);
	   		// 把viewHolder临时存放在Tag里面，可以提高效率
	   		convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		GoodsCategory category=list.get(position);
		// 显示从网络上下载下来的数据
		//imageLoader.display(viewHolder.imageView,);
		viewHolder.textView.setText(category.getCate_name());
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView textView;
	}
	
	
	public void setPosition(int position) {
		// 初始化list，即初始化adapter里面的数据
		this.list=info.getData().get(position).getChildren();
		System.out.println("*****************_____________"+ list);
		notifyDataSetChanged();
	}
	public int getPosition()
	{
		return position;
	}
	public List<GoodsCategory> getAdapterData() {
		return list;
	}
}
