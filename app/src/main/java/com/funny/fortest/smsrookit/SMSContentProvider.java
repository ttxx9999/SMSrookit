package com.funny.fortest.smsrookit;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.database.Cursor;
import android.util.Log;

public class SMSContentProvider extends ContentProvider {
    public SMSContentProvider() {
    }

    private SMSDatabaseHelper dbHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int num = db.delete("SMS",null,null);
        return num;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return "vnd.android.cursor.dir/vnd.com.funny.fortest.provider.SMS";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newSMSId = db.insert("SMS", null, values);
        Uri uriReturn = null;
        uriReturn = Uri.parse("content://com.funny.fortest.provider/SMS" + newSMSId);
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new SMSDatabaseHelper(getContext(), "SMSrookit.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Log.d("zhaochengyu", "SMSContentProvider query: execute");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sender,message FROM SMS WHERE flag = 0",new String[] {});
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
