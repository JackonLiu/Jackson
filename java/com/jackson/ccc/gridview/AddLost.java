package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jackson.ccc.test.R;

//校园活动类
public class AddLost extends Activity {

    private ImageButton add,back_ib;
    private Context mContext = null;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        title = (TextView)findViewById(R.id.inform_title);
        title.setText("发布失物");
        back_ib = (ImageButton) findViewById(R.id.back_ib);
        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLost.this.finish();
            }
        });
        add = (ImageButton) findViewById(R.id.addImage);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* TackPicture tack = new TackPicture();
                Bitmap bitmap = tack.getBitmapFromSharedPreferences();
                add.setImageBitmap(bitmap);*/
            }
        });
    }
}