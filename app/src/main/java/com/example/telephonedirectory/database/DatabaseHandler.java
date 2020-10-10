package com.example.telephonedirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.telephonedirectory.getData.Phone;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database name
    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 1;
    //Table name
    private static final String TABLE_NAME = "Contact";

    //Column name
    private static final String KEY_NAME ="Name";
    private static final String KEY_PHONE_NUMBER = "Phone_Number";

    private static final String KEY_ID = "id";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create Table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Script
        String create_contacts_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s nText, %s nText)"
                                                    , TABLE_NAME,KEY_ID,KEY_NAME,KEY_PHONE_NUMBER);

        sqLiteDatabase.execSQL(create_contacts_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String delete_table = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);
        sqLiteDatabase.execSQL(delete_table);
        onCreate(sqLiteDatabase);
    }

    public void addPhone(Phone phone){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,phone.getName());
        values.put(KEY_PHONE_NUMBER,phone.getPhoneNumber());
        database.insert(TABLE_NAME,null,values);
        database.close();
    }

    public List<Phone> getAllPhone(){
        List<Phone> phones = new ArrayList<>();
        //Select all query
        String selectAll = "Select * From "+ TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAll,null);
        //Looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Phone phone = new Phone();
                phone.setName(cursor.getString(1));
                phone.setPhoneNumber(cursor.getString(2));
                phones.add(phone);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return phones;
    }

//    public int getNumberOfContact(){
//        String count = "SELECT * FROM " + TABLE_NAME;
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery(count,null);
//        int number = cursor.getCount();
//        cursor.close();
//        return number;
//    }

    public void updatePhone(Phone phone,String oldPhone){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,phone.getName());
        values.put(KEY_PHONE_NUMBER,phone.getPhoneNumber());
        database.update(TABLE_NAME,values,KEY_PHONE_NUMBER + " = ?",new String[]{oldPhone});
        database.close();

    }
    public void deletePhone(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,KEY_ID + "= ? ", new String[]{name});
        database.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        onCreate(database);
        database.close();

    }
}
