package com.adiputrasinaga.indonesiandictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiputrasinaga.indonesiandictionary.R;
import com.adiputrasinaga.indonesiandictionary.DetailActivity;
import com.adiputrasinaga.indonesiandictionary.Model.IdEnModel;

import java.util.ArrayList;
/**
 * Created by Adiputra on 12/01/2018.
 */

public class IdEnAdapter extends RecyclerView.Adapter<IdEnAdapter.KamusIndoHolder> {

    private ArrayList<IdEnModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public IdEnAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public KamusIndoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordlist_item, parent, false);

        return new KamusIndoHolder(view);
    }

    public void addItem(ArrayList<IdEnModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(KamusIndoHolder holder, int position) {
        final String words = mData.get(position).getWords();
        final String details_words = mData.get(position).getDetails();

        holder.textViewWord.setText(words);
        holder.textViewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_WORDS, words);
                intent.putExtra(DetailActivity.EXTRA_DETAILS, details_words);
                context.startActivity(intent);
            }
        });
    }

    public class KamusIndoHolder extends RecyclerView.ViewHolder{
        private TextView textViewWord;
        public KamusIndoHolder(View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.txt_words);
        }
    }
}


