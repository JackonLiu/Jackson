package com.jackson.ccc.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by LXP on 17-3-6.
 */

public class MyOpenHelper  extends SQLiteOpenHelper{

    /**
     *
     * @param context  上下文
     * name:数据库的名字
     * factory  目的创建crusor对象
     */

    public MyOpenHelper(Context context){
        super(context,"News2.db",null,1,null);
        Log.e("222","super(context,\"Test.db\",null,1,null);");

    }

    /**
     *Called when the Database is created for the first time
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table school(_id varchar(10),category varchar(20),info varchar(100))");
        db.execSQL("insert into school(_id,category,info) values('2','校园通知','清明节放假啦')");
        Log.e("222","insert into school");
    }

    /**
      drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e("222","onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {");
    }

}
