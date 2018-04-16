package com.adiputrasinaga.indonesiandictionary;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adiputrasinaga.indonesiandictionary.Db.EnIdHelper;
import com.adiputrasinaga.indonesiandictionary.Db.IdEnHelper;
import com.adiputrasinaga.indonesiandictionary.Model.IdEnModel;
import com.adiputrasinaga.indonesiandictionary.Model.EnIdModel;
import com.adiputrasinaga.indonesiandictionary.Setting.AppSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {

        final String TAG = LoadData.class.getSimpleName();
        EnIdHelper kamusEnglishIndonesiaHelper;
        IdEnHelper kamusIndonesiaEnglishHelper;
        AppSetting appPreferences;

        @Override
        protected void onPreExecute() {
            kamusEnglishIndonesiaHelper = new EnIdHelper(SplashScreen.this);
            kamusIndonesiaEnglishHelper = new IdEnHelper(SplashScreen.this);
            appPreferences = new AppSetting(SplashScreen.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Boolean firstRun = appPreferences.getFirstRun();
            if(firstRun){
                ArrayList<EnIdModel> kamusModels = preLoadRaw();
                ArrayList<IdEnModel> kamusIndoModels = preLoadIndoRaw();

                kamusEnglishIndonesiaHelper.open();
                kamusEnglishIndonesiaHelper.beginTransaction();

                try {
                    for (EnIdModel model: kamusModels){
                        kamusEnglishIndonesiaHelper.insertTransaction(model);
                    }
                    kamusEnglishIndonesiaHelper.setTransactionSuccess();
                }

                catch (Exception e){
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusEnglishIndonesiaHelper.endTransaction();
                kamusEnglishIndonesiaHelper.close();
                kamusIndonesiaEnglishHelper.open();
                kamusIndonesiaEnglishHelper.beginTransactionIndo();

                try {
                    for(IdEnModel indoModel: kamusIndoModels){
                        kamusIndonesiaEnglishHelper.insertTransactionIndo(indoModel);
                    }
                    kamusIndonesiaEnglishHelper.setTransactionSuccessIndo();
                }

                catch (Exception e){
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusIndonesiaEnglishHelper.endTransactionIndo();
                kamusIndonesiaEnglishHelper.closeIndo();
                appPreferences.setFirstRun(false);
            }

            else {
                try {
                    synchronized (this){
                        this.wait(2000);
                    }
                }

                catch (Exception e){

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<EnIdModel> preLoadRaw() {
        ArrayList<EnIdModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.en_id);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do{
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                EnIdModel kamusModel;

                kamusModel = new EnIdModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }

    private ArrayList<IdEnModel> preLoadIndoRaw() {
        ArrayList<IdEnModel> kamusIndoModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.id_en);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do{
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                IdEnModel kamusIndoModel;

                kamusIndoModel = new IdEnModel(splitstr[0], splitstr[1]);
                kamusIndoModels.add(kamusIndoModel);
                count++;
            } while (line != null);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return kamusIndoModels;
    }
}
