package com.jackson.ccc.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackson.ccc.test.MainActivity;
import com.jackson.ccc.test.R;

import java.util.List;

import static com.jackson.ccc.test.R.mipmap.myhome;

/**
 * 组合方式去实现自定义控件
 * 在自定义控件中选择合适的方法进行合适的操作
 * Canvas.tranlate  Path
 * 自定义控件中使用了某个接口，那么额外写一个接口提供给用户
 */

public class ViewPagerIndicator extends LinearLayout {

    private ViewPager mViewPager;
    private ImageButton myhome, circle, messenger, personal;
    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeihght;
    private Context context;
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;
    private int mInitTranslationX;
    private int mTranslationX;
    private int mTabVisibleCount=4;
    private static final int COUNT_DEFAULT_TAB=4;
    private List<String> mTitles;
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    private static final int COLOR_TEXT_HIGHLIGHT = 0xFFFFFFFF;
    //三角形底边的最大宽度
    private static  int DIMENSION_TRIANGLE_MAX=0;


    public ViewPagerIndicator(Context context, AttributeSet attrs) {

        super(context, attrs);
        //获取可见Tab的数量
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count,COUNT_DEFAULT_TAB);
        if(mTabVisibleCount<0){
            mTabVisibleCount=COUNT_DEFAULT_TAB;
        }
        a.recycle();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffe4e1"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    public ViewPagerIndicator(Context context) {

        this(context, null);
        this.context=context;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();

        super.dispatchDraw(canvas);
    }

    //控件宽高发生变化就会回调这个方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //三角形底边的宽度
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        //初始值的一个偏移量 w/mTabVisibleCount——一个屏幕分成n个tab ， /2——每个tab的一半
        DIMENSION_TRIANGLE_MAX=(int)(getScreenWidth()/3*RADIO_TRIANGLE_WIDTH);
        mTriangleWidth = Math.min(mTriangleWidth,DIMENSION_TRIANGLE_MAX);
        //mTriangleWidth/2——控件文字的一半
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        initTriangle();

    }

    public void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeihght);
        mPath.close();

    }

    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / 3;
        mTranslationX = (int) (tabWidth * (offset + position));
        //容器移动，当tab移动至最后一个时
        if(position>=(mTabVisibleCount - 2 )&&offset > 0&& getChildCount()>0){
        if(mTabVisibleCount!=1){
        this.scrollTo((position - (mTabVisibleCount-2)) * (int)(tabWidth+offset),0);}
        else {this.scrollTo(position * tabWidth + (int)(tabWidth * offset),0);}
        }
        invalidate();
    }

    public void setTabItemTitles(List<String> titles){
        if(titles!=null&&titles.size()>0){
            this.removeAllViews();
            mTitles = titles;
            for(String title :mTitles){
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    //当你自定义View时占用了某个接口时，你必须创建一个接口对其设置回调，然后提供用户进行使用
    public interface PageOnChangeListener{

        public void onPageSelected(int arg0);
        public void onPageScrolled(int position, float positionOffset, int arg2);
        public void onPageScrollStateChanged(int arg0);
    }

    public PageOnChangeListener mListener;
    public void addOnPageChangeListener(PageOnChangeListener listener){
        this.mListener = listener;
    }

    public void setViewPager(ViewPager viewPager,int pos){
        mViewPager = viewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {

                int currentItem = mViewPager.getCurrentItem();
               if(mListener != null){
                   mListener.onPageSelected(arg0);
               }

                switch (currentItem) {
                    case 0:
                        Log.e("222","myhome");
                        myhome.setImageResource(R.mipmap.home156);
                        break;
                    case 1:
                        circle.setImageResource(R.mipmap.circle156);
                        Log.e("222","circle");
                        break;
                    case 2:
                        messenger.setImageResource(R.mipmap.messenger156);
                        Log.e("222","messenger");
                        break;
                    case 3:
                        personal.setImageResource(R.mipmap.person156);
                        Log.e("222","personal");
                        break;
                    default:
                        break;
                }
                hightLightTextView(arg0);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int arg2) {
                scroll(position,positionOffset);
                if(mListener != null){
                    mListener.onPageScrolled(position,positionOffset,arg2);
                }

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if(mListener != null){
                    mListener.onPageScrollStateChanged(arg0);
                }

            }
        });
        mViewPager.setCurrentItem(pos);
        hightLightTextView(pos);
}
    //设置可见的tab数量

    public void setVisibleTabCount(int count){
        mTabVisibleCount = count;
    }

    //根据title创建tab
    public View generateTextView(String title){
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp.width = getScreenWidth()/mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    //重置tab文本颜色
    public void resetTextViewColor(){
        for(int i= 0,length= getChildCount() ;i<length;i++ ){
            View view = getChildAt(i);
            if(view instanceof TextView){
                ((TextView)view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    //设置tab的点击事件
    public void setItemClickEvent(){
        int cCount = getChildCount();
        for(int i= 0;i<cCount;i++){
            final  int j=i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                    Log.e("222","                    mViewPager.setCurrentItem(j);\n");
                }
            });
        }
    }

    //高亮某个Tab的文本
    public void hightLightTextView(int pos){
        resetTextViewColor();
        View view = getChildAt(pos);
        if(view instanceof TextView){
            ((TextView)view).setTextColor(COLOR_TEXT_HIGHLIGHT);
        }
    }


    //在xml加载完后会回掉这个方法
    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        int cCount = getChildCount();
        if (cCount == 0) return;
        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.width= getScreenWidth()/mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();
    }

    //获得屏幕的宽度
    public  int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
