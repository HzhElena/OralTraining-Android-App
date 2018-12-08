package com.sunm0710.freechat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepo {
    private DBHelper dbHelper;
    public UserRepo(Context context){
        dbHelper=new DBHelper(context);
    }
    public void insert_user(UserInfo user)
    {
    	if(get_id(user)==-1)
    		insert(user);
    }
    public void insert(UserInfo user){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UserInfo.KEY_name,user.username);
        values.put(UserInfo.KEY_pass,user.password);
        values.put(UserInfo.KEY_email,user.email);
        values.put(UserInfo.KEY_ID,user.userid);
        db.insert(UserInfo.TABLE,values);
        db.close();
    }
    public int get_id(String username)
    {
        int userid = -1;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                UserInfo.KEY_ID + "," +
                " FROM " + UserInfo.TABLE
                + " WHERE " +
                UserInfo.KEY_name+ "=?";
        ContentValues values=new ContentValues();
        values.put(UserInfo.KEY_name,username);
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(username)});
        if(cursor.moveToFirst()){
            do{
               userid =cursor.getInt(cursor.getColumnIndex(UserInfo.KEY_ID));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userid;
    }
    public void update_record(UserInfo user)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(UserInfo.KEY_name,user.userid);
        values.put(UserInfo.KEY_pass,user.password);
        values.put(UserInfo.KEY_email,user.email);
        values.put(UserInfo.KEY_record,user.records);

        db.update(UserInfo.TABLE,values,UserInfo.KEY_ID+"=?",new String[] { String.valueOf(user.userid) });
        db.close();
    }
    public UserInfo getUserbyId(int id)
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                UserInfo.KEY_ID + "," +
                UserInfo.KEY_name + "," +
                UserInfo.KEY_email + "," +
                UserInfo.KEY_record +
                " FROM " + UserInfo.TABLE
                + " WHERE " +
                UserInfo.KEY_ID + "=?";
        UserInfo user=new UserInfo();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
                user.userid =cursor.getInt(cursor.getColumnIndex(UserInfo.KEY_ID));
                user.username =cursor.getString(cursor.getColumnIndex(UserInfo.KEY_name));
                user.email  =cursor.getString(cursor.getColumnIndex(UserInfo.KEY_email));
                user.records =cursor.getString(cursor.getColumnIndex(UserInfo.KEY_record));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }
}
