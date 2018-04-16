package com.adiputrasinaga.indonesiandictionary.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adiputrasinaga.indonesiandictionary.R;

import com.adiputrasinaga.indonesiandictionary.Adapter.IdEnAdapter;
import com.adiputrasinaga.indonesiandictionary.Db.IdEnHelper;
import com.adiputrasinaga.indonesiandictionary.Model.IdEnModel;

import java.util.ArrayList;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class IdEnFragment extends Fragment {

    RecyclerView recyclerViewIndonesiaEnglish;
    SearchView searchViewIndonesiaEnglish;
    IdEnHelper kamusIndonesiaEnglishHelper;
    IdEnAdapter kamusAdapter;
    View rootView;

    public IdEnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_id_en, container, false);

        searchViewIndonesiaEnglish = view.findViewById(R.id.search_word_indonesia_english);
        searchViewIndonesiaEnglish.setFocusable(false);

        rootView = view.findViewById(R.id.root_layout_indo);

        recyclerViewIndonesiaEnglish = view.findViewById(R.id.recycler_indonesia_english);

        searchViewIndonesiaEnglish.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kamusIndonesiaEnglishHelper = new IdEnHelper(getContext());
                kamusAdapter = new IdEnAdapter(getContext());

                recyclerViewIndonesiaEnglish.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewIndonesiaEnglish.setAdapter(kamusAdapter);

                kamusIndonesiaEnglishHelper.open();

                ArrayList<IdEnModel> kamusIndoModels = kamusIndonesiaEnglishHelper.getDataByWordIndo(newText);

                kamusIndonesiaEnglishHelper.closeIndo();
                kamusAdapter.addItem(kamusIndoModels);

                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        searchViewIndonesiaEnglish.setQuery("", false);
        rootView.requestFocus();
    }
}
