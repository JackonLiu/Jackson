package com.jackson.ccc.http;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.jackson.ccc.test.R;

import static android.widget.RelativeLayout.TRUE;

/**
 * 自定义标题工具栏
 * 自定义属性申明与获取
 * Created by LXP on 17-3-10.
 */

public class Topbar  extends  RelativeLayout{
    private Button leftButton,rightButton;
    private TextView textView;

    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    private float titleTextsize;
    private int  titleTextColor;
    private String title;

    private LayoutParams leftParams,rightParams,titleParams;

    public Topbar(Context context) {
        this(context,null);
    }

    public Topbar(Context context , AttributeSet attrs) {
        super(context,attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Topbar);

        leftTextColor= ta.getColor(R.styleable.Topbar_leftTextColor,0);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftText = ta.getString(R.styleable.Topbar_leftText);

        rightTextColor= ta.getColor(R.styleable.Topbar_rightTextColor,0);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightText = ta.getString(R.styleable.Topbar_rightText);

        titleTextsize  = ta.getDimension(R.styleable.Topbar_titleTextSize,0);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor,0);
        title = ta.getString(R.styleable.Topbar_title);

        ta.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        textView = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        textView.setTextColor(titleTextColor);
        textView.setTextSize(titleTextsize);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        setBackgroundColor(0xFFF59563);

        leftParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);

        addView(leftButton,leftParams);
        
        rightParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);

        addView(rightButton,rightParams);
        
        titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);

        addView(textView,titleParams);

        /**1.分析需要使用的自定义属性
         * 2.在res/values/attrs.xml定义声明
         * 3、在layout xml文件中进行使用
         * 4.在VIew的构造方法中进行获取
         *

        //获取大量属性的方法

        int n= ta.getIndexCount();

        for(int i=0;i<n;i++){
            int attr = ta.getIndex(i);
            switch (attr){
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable drawable = (BitmapDrawable)ta.getDrawable(attr);
                    Bitmap mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    int mColor = ta.getColor(attr,0xFF45c01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    String mText = ta.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    int mTextSize =(int)ta.getDimension(attr, TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics()));
                    break;
            }

        }
        ta.recycle();
         */
    }
}
