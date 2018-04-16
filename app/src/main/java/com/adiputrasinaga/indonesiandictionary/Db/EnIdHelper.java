package com.adiputrasinaga.indonesiandictionary.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.adiputrasinaga.indonesiandictionary.Model.EnIdModel;
import static com.adiputrasinaga.indonesiandictionary.Db.DbContract.EnglishIndonesiaColumn.DETAILS_ENGLISH;
import static com.adiputrasinaga.indonesiandictionary.Db.DbContract.EnglishIndonesiaColumn.WORDS_ENGLISH;
import static com.adiputrasinaga.indonesiandictionary.Db.DbContract.TABLE_ENGLISH_INDONESIA;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class EnIdHelper {

    private Context context;
    private DbHelper databaseHelper;

    private SQLiteDatabase database;

    public EnIdHelper(Context context) {
        this.context = context;
    }

    public EnIdHelper open() throws SQLException {

        databaseHelper = new DbHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<EnIdModel> getDataByWord(String word){

        String result = "";
        Cursor cursor = database.query(TABLE_ENGLISH_INDONESIA,null, WORDS_ENGLISH + " LIKE ?", new String[]{"%" + word + "%"},null, null,WORDS_ENGLISH);
        cursor.moveToFirst();

        ArrayList<EnIdModel> arrayList = new ArrayList<>();
        EnIdModel kamusModel;

        if(cursor.getCount()>0){

            do{
                kamusModel = new EnIdModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setWords(cursor.getString(cursor.getColumnIndexOrThrow(WORDS_ENGLISH)));
                kamusModel.setDetails(cursor.getString(cursor.getColumnIndexOrThrow(DETAILS_ENGLISH)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public void close(){
        databaseHelper.close();
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(EnIdModel kamusModel){

        String sql = "INSERT INTO " + TABLE_ENGLISH_INDONESIA + " (" + WORDS_ENGLISH + ", " + DETAILS_ENGLISH + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getWords());
        stmt.bindString(2, kamusModel.getDetails());
        stmt.execute();
        stmt.clearBindings();
    }

    public long insert(EnIdModel kamusModel){

        ContentValues initialValues = new ContentValues();
        initialValues.put(WORDS_ENGLISH, kamusModel.getWords());
        initialValues.put(DETAILS_ENGLISH, kamusModel.getDetails());
        return database.insert(TABLE_ENGLISH_INDONESIA, null, initialValues);
    }

    public int update(EnIdModel kamusModel){

        ContentValues args = new ContentValues();
        args.put(WORDS_ENGLISH, kamusModel.getWords());
        args.put(DETAILS_ENGLISH, kamusModel.getDetails());
        return database.update(TABLE_ENGLISH_INDONESIA, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_ENGLISH_INDONESIA, _ID + " = '" + id + "'", null);
    }
}

