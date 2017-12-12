package com.jackson.ccc.gridview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jackson.ccc.test.R;

public class AddNews extends Activities {

    private ImageButton addImage,finsh;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add);
        title = (TextView)findViewById(R.id.inform_title);
        title.setText("发布资讯");
        addImage = (ImageButton) findViewById(R.id.addImage);
        finsh = (ImageButton) findViewById(R.id.back_ib);
        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNews.this.finish();
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
