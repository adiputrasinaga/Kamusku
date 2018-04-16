package com.adiputrasinaga.indonesiandictionary;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_WORDS = "extra_words";
    public static String EXTRA_DETAILS = "extra_details";

    public TextView textViewWord, textViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.details);

        String words = getIntent().getStringExtra(EXTRA_WORDS);
        String detail_word = getIntent().getStringExtra(EXTRA_DETAILS);

        textViewWord = findViewById(R.id.txt_words_detail);
        textViewDetail = findViewById(R.id.txt_details_detail);

        textViewWord.setText(words);
        textViewDetail.setText(detail_word);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();

        if(i == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
