package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.ImageButton;

import android.widget.TextView;


import com.jackson.ccc.test.R;

public class AddAgora extends Activity {
    ImageButton addImage, back;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add);
        addImage = (ImageButton) findViewById(R.id.addImage);
        title = (TextView)findViewById(R.id.inform_title);
        title.setText("发布物品");
        back = (ImageButton) findViewById(R.id.back_ib);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAgora.this.finish();
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TackPicture tack=new TackPicture();
                Bitmap bitmap = tack.getBitmapFromSharedPreferences();
                addImage.setImageBitmap(bitmap);*/
                Log.e("222", "2addImage.setOnClickListener2");
            }
        });
    }
}