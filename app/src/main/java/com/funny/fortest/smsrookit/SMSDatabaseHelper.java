package com.funny.fortest.smsrookit;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhaochengyu on 2016/3/30.
 */
public class SMSDatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_SMS = "create table SMS ("
            + "id integer primary key autoincrement, "
            + "sender text, "
            + "message text,"
            + "flag int)";


    public SMSDatabaseHelper(Context context, String name,
                            CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("SMSDatabaseHelper", "onCreate: execute~~~");
        db.execSQL(CREATE_SMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
