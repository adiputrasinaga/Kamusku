package com.adiputrasinaga.indonesiandictionary.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.adiputrasinaga.indonesiandictionary.Model.IdEnModel;

import static com.adiputrasinaga.indonesiandictionary.Db.DbContract.IndonesiaEnglishColumn.DETAILS_INDONESIA;
import static com.adiputrasinaga.indonesiandictionary.Db.DbContract.IndonesiaEnglishColumn.WORDS_INDONESIA;
import static com.adiputrasinaga.indonesiandictionary.Db.DbContract.TABLE_INDONESIA_ENGLISH;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class IdEnHelper {

    private Context context;
    private DbHelper databaseHelper;

    private SQLiteDatabase database;

    public IdEnHelper(Context context) {
        this.context = context;
    }

    public IdEnHelper open() throws SQLException {

        databaseHelper = new DbHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<IdEnModel> getDataByWordIndo(String word){

        String result = "";
        Cursor cursor = database.query(TABLE_INDONESIA_ENGLISH, null, WORDS_INDONESIA + " LIKE ?", new String[]{"%" + word + "%"},null, null, WORDS_INDONESIA);
        cursor.moveToFirst();

        ArrayList<IdEnModel> arrayList = new ArrayList<>();
        IdEnModel kamusIndoModel;

        if(cursor.getCount()>0){

            do{
                kamusIndoModel = new IdEnModel();
                kamusIndoModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusIndoModel.setWords(cursor.getString(cursor.getColumnIndexOrThrow(WORDS_INDONESIA)));
                kamusIndoModel.setDetails(cursor.getString(cursor.getColumnIndexOrThrow(DETAILS_INDONESIA)));

                arrayList.add(kamusIndoModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public void closeIndo(){
        databaseHelper.close();
    }

    public void beginTransactionIndo(){
        database.beginTransaction();
    }

    public void setTransactionSuccessIndo(){
        database.setTransactionSuccessful();
    }

    public void endTransactionIndo(){
        database.endTransaction();
    }

    public void insertTransactionIndo(IdEnModel kamusIndoModel){

        String sql = "INSERT INTO " + TABLE_INDONESIA_ENGLISH + " (" + WORDS_INDONESIA + ", " + DETAILS_INDONESIA + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusIndoModel.getWords());
        stmt.bindString(2, kamusIndoModel.getDetails());
        stmt.execute();
        stmt.clearBindings();
    }
}

