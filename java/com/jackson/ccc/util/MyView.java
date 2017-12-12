package com.jackson.ccc.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;

/**笔记类
 * Created by LXP on 17-3-10.
 */

public class MyView  extends  View{

    public MyView(Context context) {
        super(context);
    }

    /**
    测量 onMeasureA决定自己到底用多大范围
     1、测量的模式 EXACTLY(设置一个明确的值)  AT_MOST(至多不能超过某个值)
        UNSPECIFIED(没有限制，适用于scrollview，listView)
     2、MeasureSpec 封装一些值
     3、setMeasuredDimension
     4、requestLayout



    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        int mode =MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

       if(mode== MeasureSpec.EXACTLY){
           result = size;
       }
        else{
           //自身去计算长宽 getNeedHeight()得到自己想要的宽度
           result = getNeedHeight()+ getPaddingTop()+getPaddingBottom();
           if(mode == MeasureSpec.AT_MOST){
               // 相当于wrapcontent
               result= Math.min(result,size);
           }

       }
        return result;
}
     */





    /**
     * 布局onlayout(viewgroup) 父控件决定子控件的位置 如果是View就不需要考虑
     * 尽可能将onMeasure中的一些操作移动到此方法中
     * requestLayout()触发

    @Override
    protected void onLayout(boolean changed , int l,int t,int r,int b){
        final  int childCount = getChildCount();
        for(int i= 0 ; i<childCount;i++){
            final View child = getChildAt(i);
            if(child.getVisibility()==GONE){
                continue;
            }
            //计算childView Layout的左上角X坐标
            left = caculateChildLeft();
            //计算childview layout的左上角y坐标
            top = caculateChildTop();
            child.layout(left,top,left+cWidth,top+cWidth);
        }
    }

    */


    /**自定义控件的各种方法笔记
     * 1、viewconfiguration，mTouchSlop
     * 2、ScaleGestureDetector
     * Created by LXP on 17-3-10.

    @Override
    protected Parcelable onSaveInstanceState(){
        //可在此方法中保存progressbar的进度
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS,super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA,mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state){
        //可在此方法中重新获取progressbar的进度

        if(state instanceof Bundle){
            Bundle bundle = new Bundle();
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
        return;
        }
       super.onRestoreInstanceState(state);
    }
    */

    /**
     * onInterceptTouchEvent(ViewGroup)
     * 决定是否拦截该手势

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_MOVE: {
                final int activePointId = mActivePointId;
                if(activePointId==INVALID_POINTER){
                    break;
                }
                final int pointerIndex = ev.findPointerIndex(activePointId);
                final int y = (int)ev.getY(pointerIndex);
                final int yDiff = Math.abs(y-mLastMotionY);
                if(yDiff>mTouchSlop){
                    mIsBeingDragged = true;
                    mLastMotionY =y;
                }
                break;
            }
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            return mIsBeingDragged;

        }

    }
    */
}
