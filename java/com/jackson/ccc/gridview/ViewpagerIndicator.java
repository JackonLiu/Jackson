package com.jackson.ccc.gridview;

        import android.util.Log;
        import android.view.View;
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
        import android.view.Gravity;
        import android.view.WindowManager;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.jackson.ccc.test.R;

        import org.androidannotations.annotations.res.ColorRes;

        import java.util.List;

/**
 * 2. 绘制三角形
 *
 */
public class ViewpagerIndicator extends LinearLayout {

    private Paint mPaint; //画笔
    private Path mPath; //path类 可以定义3条线 绘制出三角形
    protected Context mContext;
    private int mTriangleWidth; //三角形的宽度
    private int mTriangleHeight; //三角形的高度

    //为了适应屏幕的大小 规定三角形为tab 的1/6
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;
    private int mInitTranslationX; //定义初始化的x轴的偏移量值 三角形的初始偏移位置，及第一次出现的X坐标
    private int mTranslationX; //移动时的距离
    private int mTabVisibleCount; // 屏幕可见tab的个数
    private static final int COUNT_DEFAULT_TAB = 4;//默认显示4个 tab

    private List<String> mTitles; //设置标签标题


    public ViewpagerIndicator(Context context) {

        this(context, null);
        Log.e("222",context.toString());
        //this.mContext=context;
    }

    public ViewpagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TAB);

        if (mTabVisibleCount < 0) {
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }
        a.recycle();

        Log.e("222","TypedArray a");
        //在构造方法中初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setColor(Color.BLUE);
        //另一种设置颜色的方式
        //mPaint.setColor(Color.parseColor("#fff5ee"));
        mPaint.setStyle(Paint.Style.FILL);

        //设置不让三角形的角度很尖锐
        mPaint.setPathEffect(new CornerPathEffect(3));
    }



    @Override
    //在该方法中设置一些子元素的 参数
    protected void onFinishInflate() {
        super.onFinishInflate();

        int cCount = getChildCount();
        if (cCount == 0) return;

        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);

            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();

    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    @Override  //在该方法中进行绘制三角形
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();

        //平移到画笔开始位置
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2); //加上2 表示让三角形往下移动
        //进行绘制
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

    }

    @Override //当控件的宽高发生变化时 回调
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH); //三角形底边宽度
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2; //三角形所在位置的 初始值x轴的偏移量

        initTriangle(); //初始化三角形
    }

    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 2; //三角形的高

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 指示器跟随手指进行滚动的计算
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mTabVisibleCount; //控件的宽度
        mTranslationX = (int) (tabWidth * offset + tabWidth * position); //三角形的偏移量

        //让盛装 tab的容器随着 指示器一起移动
        //为什么减2呢? 因为我们是从0 开始的
        if (position >= (mTabVisibleCount - 2) && offset > 0 && getChildCount() > mTabVisibleCount) {

            if (mTabVisibleCount != 1) {
                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int) (tabWidth * offset), 0);
            } else {
                this.scrollTo((int) (tabWidth * offset + tabWidth * position), 0);
            }
        }

        //三角形的位置发生改变之后进行重绘
        invalidate();
    }

    public void setTabItemTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            this.removeAllViews(); //清除布局文件中view

            mTitles = titles;
            for (String title : mTitles) {
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    /**
     * 设置可见 tab 数量
     *
     * @param count
     */
    public void setVisibleTabCount(int count) {
        mTabVisibleCount = count;
    }

    /**
     * 根据title创建tab
     *
     * @param title
     * @return
     */
    private View generateTextView(String title) {

        TextView textView = new TextView(getContext());

        LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLUE);
        textView.setLayoutParams(lp);

        return textView;
    }

    /**
     * 在这里还存在一个问题,如果开发者需要在 mainactivity 中需要调用setOnPageChangeListener()
     * 所以我们在这里自己定义一个接口 ,方便使用者调用.
     */

    public interface PageOnChangeListener {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    public PageOnChangeListener mListener;

    public void setOnPageChangeListener(PageOnChangeListener listener) {
        this.mListener = listener;
    }


    private ViewPager mViewPager;

    /**
     * 终极大方法
     *
     * @param viewPager
     * @param pos
     */
    public void setViewPager(ViewPager viewPager, int pos) {
        this.mViewPager = viewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //偏移量计算 tabWidth+positionOffset + position* tabWidth
                scroll(position, positionOffset);

                //自定义接口的回调
                if (mListener != null) {
                    mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //自定义接口的回调
                if (mListener != null) {
                    mListener.onPageSelected(position);
                }

                highLightTextView(position); //高亮tab文本颜色
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //自定义接口的回调
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(pos);
        highLightTextView(pos); //高亮tab文本颜色
    }

    /**
     * 重置tab文本颜色
     */
    public void resetTextViewColor(){
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView){

                ((TextView) view).setTextColor(Color.BLUE);
            }
        }
    }


    /**
     * 高亮显示某个选中的 tab
     * @param pos
     */
    public void highLightTextView(int pos) {

        resetTextViewColor(); //高亮之前 调用
        View view = getChildAt(pos);
        if (view instanceof TextView){

            ((TextView) view).setTextColor(Color.BLACK);
        }
    }

    /**
     * 设置 tab的点击事件
     */
    public void setItemClickEvent(){
        for (int i = 0; i < getChildCount(); i++) {

            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(finalI);
                }
            });
        }
    }
}