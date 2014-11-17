package com.gyh.digou.faxian;

import com.gyh.digou.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FaXianFragement extends Fragment{

	
	
	
	public static FaXianFragement ff;
	
	public static FaXianFragement getInstance()
	{
		if(ff==null)
		{
			ff=new FaXianFragement();
		}
		return ff;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.faxian_fragment, container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	
		super.onActivityCreated(savedInstanceState);
	}	

	@Override
	public void onStart() {
		
		super.onStart();
	}

	@Override
	public void onResume() {
	
		super.onResume();
	}
	
	@Override
	public void onPause() {
		
		super.onPause();
	}

	@Override
	public void onStop() {
	
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		
		super.onDetach();
	}

	@Override
	public void onAttach(Activity activity) {
	
		super.onAttach(activity);
	}
}
