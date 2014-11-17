package com.gyh.digou.gouwuche;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.gyh.digou.R;

import com.gyh.digou.itemdetail.ItemDetailActivity;

public class CartInfoAdapter extends BaseExpandableListAdapter {

	
	//private Context mContext;
	private JSONObject jsonObject;
	private LayoutInflater inflater;
	private List<JSONObject> orderList;
	List<JSONObject> cartInfo;
	
	//private boolean[][] checked;
	//List<ArrayList<Boolean>> checkList=new ArrayList<ArrayList<Boolean>>();
	//List<ArrayList<>>
	//List<Boolean> groupCheckList=new ArrayList<Boolean>();
	private Context mContext;
	private Handler mHandler;
	FinalBitmap imageLoader;
	//private int childCount;
	public CartInfoAdapter(Context mContext,List<JSONObject> cartInfo)
	{
		this.cartInfo=cartInfo;
		this.mContext=mContext;
		inflater=LayoutInflater.from(mContext);
		imageLoader=FinalBitmap.create(mContext);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		try {
			initCartCheck(true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void setCheckList(List<ArrayList<Boolean>> checkList)
	{
		this.checkList=checkList;
	}*/
	
	
	public void initCartCheck(boolean check) throws JSONException
	{
		//checkList.clear();
		for(int i=0;i<cartInfo.size();i++)
		{
			//groupCheckList.add(false);
			JSONObject jsonObj=cartInfo.get(i);
			jsonObj.put("check",check);
			/*//jsonObj.put("autocheck",false);
			//ArrayList<Boolean> blist=new ArrayList<Boolean>();
			for(int j=0;j<getChildrenCount(i);j++)
			{
				//blist.add(check);
				JSONObject json=jsonObj.getJSONArray("goods").getJSONObject(j);
				json.put("check",check);
			}*/
			//checkList.add(blist);
		}
		
		
	}
	
	public void cancleOrAll(boolean check) throws JSONException
	{
		
		initCartCheck(check);
		
		notifyDataSetChanged();
		
		//refreshData();
	}
	
	public void deleteCart(int groupPosition,int childPosition)
	{
		
		
		
	}
	
	
	
	public void setCartData(List<JSONObject> cartInfo)
	{
		
		this.cartInfo=cartInfo;
		try {
			initCartCheck(true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notifyDataSetChanged();
		Log.d("cartInfoAdapter setcartData",cartInfo.toString());
		mHandler.sendEmptyMessage(0x1);
		//refreshData();
	}
	
	public List<JSONObject> getData()
	{
		return cartInfo;
	}
	/*public List<ArrayList<Boolean>> getCheckList()
	{
		return checkList;
	}*/
	@Override
	public int getGroupCount() {
		
		return cartInfo.size();
	}

	@Override
	public int getChildrenCount(int groupPosition){
		int childCount=0;
		try {
			
			childCount = cartInfo.get(groupPosition).getJSONArray("goods").length();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return childCount;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setHandler(Handler handler)
	{
		this.mHandler=handler;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		convertView=inflater.inflate(R.layout.cart_shopowner, null);
		
		TextView tv=(TextView) convertView.findViewById(R.id.cart_shopowner_tv);
		final CheckBox checkbox=(CheckBox) convertView.findViewById(R.id.cart_shopowner_checkbox);
		
		final int gp=groupPosition;
		
		String store_name="";
		final JSONObject json_w=cartInfo.get(groupPosition);
		
		try {
			
			checkbox.setChecked(json_w.getBoolean("check"));
		
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				try {
					json_w.put("check",arg1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkbox.setChecked(arg1);
					//notifyDataSetChanged();
				mHandler.sendEmptyMessage(0x1);
					//refreshData();
			}
		});
		
		store_name = json_w.getString("store_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv.setText(store_name);
		
		return convertView;
	}
	
	
	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		ViewHolderChild holder=null;
		if(convertView==null)
		{
			holder=new ViewHolderChild();
			convertView=inflater.inflate(R.layout.cart_item, null);
			
			holder.tv_cart_item_name=(TextView) convertView.findViewById(R.id.cart_item_name);
			//holder.checkbox_cart_item=(CheckBox) convertView.findViewById(R.id.cart_item_checkbox);
			holder.tv_cart_item_num=(TextView) convertView.findViewById(R.id.pop_num);
			holder.btn_cart_item_num_add=(Button) convertView.findViewById(R.id.pop_add);
			holder.btn_cart_item_num_del=(Button) convertView.findViewById(R.id.pop_reduce);
			holder.imv_cart_item=(ImageView) convertView.findViewById(R.id.cart_item_imv);	
			holder.tv_cart_item_price=(TextView) convertView.findViewById(R.id.cart_item_price);
			convertView.setTag(holder);
		}else
		{
			holder=(ViewHolderChild) convertView.getTag();
		}
		
		final JSONObject json_e=cartInfo.get(groupPosition);
		
		try {
		
		final JSONArray arry=json_e.getJSONArray("goods");
			
		
		final JSONObject json_w=arry.getJSONObject(childPosition);
		
		imageLoader.display(holder.imv_cart_item,json_w.getString("goods_image"));
		
			final String goods_id=json_w.getString("goods_id");
			final String goods_num=json_w.getString("quantity");
			holder.tv_cart_item_num.setText(goods_num);	
			holder.tv_cart_item_name.setText(json_w.getString("goods_name"));
			holder.tv_cart_item_price.setText(json_w.getString("price"));
			
			holder.imv_cart_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					Intent intent=new Intent(mContext,ItemDetailActivity.class);
					intent.putExtra("goods_id",goods_id);
					mContext.startActivity(intent);
					
				}
			});
			
			holder.btn_cart_item_num_add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int num=Integer.parseInt(goods_num);
					try {
						json_w.put("quantity",(num+1)+"");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					notifyDataSetChanged();
					mHandler.sendEmptyMessage(0x1);
					//refreshData();
				}
			});
			
			
			holder.btn_cart_item_num_del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int num=Integer.parseInt(goods_num);
					if(num>1)
					{
						try {
							json_w.put("quantity",(num-1)+"");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						notifyDataSetChanged();
						mHandler.sendEmptyMessage(0x1);
						
					}else
					{
						Toast.makeText(mContext,"�������ܵ���һ��",Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return convertView;
	}

	
	 class ViewHolderChild
	{
		TextView tv_cart_item_name;
		TextView tv_cart_item_num;
		TextView tv_cart_item_price;
		
		//CheckBox checkbox_cart_item;
		Button btn_cart_item_num_add;
		Button btn_cart_item_num_del;
		ImageView imv_cart_item;
		
	}
	 class ViewHolderParent
	{
		TextView tv_cart_store_name;
		CheckBox checkbox_cart_store;
		
		
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}

	
	//showMany showmany;
	
	
}
