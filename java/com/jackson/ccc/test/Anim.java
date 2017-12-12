package com.jackson.ccc.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by LXP on 17-3-12.
 */

public class Anim extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        //从资源文件中下载动画
         Animation aaa = AnimationUtils.loadAnimation(this,R.anim.init_anim);

        //为谁设置这个动画
        imageView.setAnimation(aaa);

        aaa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //initData(); 初始化数据
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Anim.this, MainActivity.class);
                startActivity(intent);
                Anim.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
