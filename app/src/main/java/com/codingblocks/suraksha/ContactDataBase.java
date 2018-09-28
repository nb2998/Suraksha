package com.codingblocks.suraksha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class ContactDataBase extends SQLiteOpenHelper {

    Context a;
    public static final String TABLE_NAME="contacts";

    public ContactDataBase(Context context) {
        super(context, "contactDataBase.db", null, 1);
        a=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table query
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (PH_NO TEXT PRIMARY KEY," +
                "NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //When update app
    }
    public boolean insertData(String number, String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("PH_NO",number);
        contentValues.put("NAME",name);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
    public void deleteContact(String number, String name){
        SQLiteDatabase db=this.getWritableDatabase();
        //Toast.makeText(a, Integer.valueOf(number), Toast.LENGTH_SHORT).show();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE PH_NO='"+number+"' AND NAME='"+name+"'");
        Toast.makeText(a, number+" DELETED.", Toast.LENGTH_SHORT).show();
    }
}
