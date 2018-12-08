package com.sunm0710.freechat.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sunm0710.freechat.Database.UserInfo;


public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION= 3;
    private static final String DATABASE_NAME="sqlitestudy.db";
    public DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ UserInfo.TABLE+"("
                +UserInfo.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +UserInfo.KEY_name+" TEXT, "
                +UserInfo.KEY_email+" TEXT, "
                +UserInfo.KEY_record+" TEXT)";
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ UserInfo.TABLE);

        onCreate(db);
    }
}