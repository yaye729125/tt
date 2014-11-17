package com.gyh.digou.fenlei;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class PullListView extends ListView implements Runnable { 
    
	
	private float mLastDownY = 0f;  
    private int mDistance = 0;  
    private int mStep = 0;  
    private boolean mPositive = false;  
    private String Tag="PullListview"; 
  
 
    public PullListView (Context context, AttributeSet attrs) {  
          super(context, attrs);  
    }  
  
    public PullListView (Context context, AttributeSet attrs, int defStyle) {  
          super(context, attrs, defStyle);  
    }  
  
    public PullListView (Context context) {  
          super(context);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
         switch (event.getAction()) {  
              case MotionEvent.ACTION_DOWN: //��ָ����ʱ���� 
                  Log.d(Tag,"ActionDown"); 
                   if (mLastDownY == 0f && mDistance == 0) {                        
                         mLastDownY = event.getY();  
                         Log.d(Tag,"mLastDownY��ֵ"+mLastDownY); 
                   return true;  
              }  
              break;  
    case MotionEvent.ACTION_CANCEL:  
           break; 
 
    case MotionEvent.ACTION_UP: //��ָ̧��֮�󴥷� 
      Log.d(Tag,"ActionUP"); 
          if (mDistance != 0) {  
              System.out.println("---post"); 
           mStep = 1;  
           mPositive = (mDistance >= 0);  
           this.postDelayed(this, 20);          
           return true;  
        }  
        mLastDownY = 0f;  
        mDistance = 0;  
        break;  
  
    case MotionEvent.ACTION_MOVE:  //��ָ����֮�󻬶�����
      Log.d(Tag,"ActionMove"); 
        if (mLastDownY != 0f) {               
              mDistance = (int) (mLastDownY - event.getY());  
              Log.d(Tag,"mLastDownY��Ϊ0��view���滬��"+"mDistance"+mDistance); 
              if ((mDistance < 0 && getFirstVisiblePosition() == 0 && 
                getChildAt(0).getTop() == 0) || (mDistance > 0 &&
                 getLastVisiblePosition() == getCount() - 1)) {  
                  //��һ��λ�ò��������������ͻ����������һ��λ��������  
                  //����жϵ��������ڷǶ��˵Ĳ��ֲ����д˹��� 
                  mDistance /= 2; //������Ϊ�˼��ٹ����ľ��� 
                   scrollTo(0, mDistance); //���� 
                   return true;  
               }  
        }  
        mDistance = 0;  
        break;  
        }  
        return super.onTouchEvent(event);  
    }  
  
    public void run() {  
          Log.d(Tag,"ActionUP����post"); 
          mDistance += mDistance > 0 ? -mStep : mStep;  
          scrollTo(0, mDistance);  
          if ((mPositive && mDistance <= 0) || (!mPositive && mDistance >= 0)) {  
                scrollTo(0, 0);  
                mDistance = 0;  
                mLastDownY = 0f;  
                Log.d(Tag,"post����0"); 
                return;  
           }  
          mStep += 1;  
        // this.postDelayed(this, 10);  
          //this.post(this); 
          this.post(this);
     }  
}