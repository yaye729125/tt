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

	
	// ��ȡ�б��������
	@Override
	public int getCount() {
		return list.size();
	}

	// ��ȡÿһ���б���
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	// ��ȡÿһ���б����Id
	@Override
	public long getItemId(int position) {
		return position;
	}

	// ����һ����ͼ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	   	ViewHolder viewHolder = new ViewHolder();
		if (null == convertView) {
	   		convertView = inflater.inflate(R.layout.listview_xiangxi, null);

			System.out.println("convertView============="+convertView);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
	   		viewHolder.textView= (TextView) convertView.findViewById(R.id.textView1);
	   		// ��viewHolder��ʱ�����Tag���棬�������Ч��
	   		convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		GoodsCategory category=list.get(position);
		// ��ʾ����������������������
		//imageLoader.display(viewHolder.imageView,);
		viewHolder.textView.setText(category.getCate_name());
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView textView;
	}
	
	
	public void setPosition(int position) {
		// ��ʼ��list������ʼ��adapter���������
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
