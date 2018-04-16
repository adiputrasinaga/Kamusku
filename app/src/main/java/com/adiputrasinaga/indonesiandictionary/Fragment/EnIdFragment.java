package com.adiputrasinaga.indonesiandictionary.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adiputrasinaga.indonesiandictionary.Db.EnIdHelper;
import com.adiputrasinaga.indonesiandictionary.Model.EnIdModel;
import com.adiputrasinaga.indonesiandictionary.R;
import com.adiputrasinaga.indonesiandictionary.Adapter.EnIdAdapter;

import java.util.ArrayList;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class EnIdFragment extends Fragment {

    RecyclerView recyclerViewEnglishIndonesia;
    SearchView searchViewEnglishIndonesia;
    EnIdHelper kamusEnglishIndonesiaHelper;
    EnIdAdapter kamusAdapter;
    View rootView;

    public EnIdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_en_id, container, false);

        searchViewEnglishIndonesia = view.findViewById(R.id.search_word_english_indonesia);
        searchViewEnglishIndonesia.setFocusable(false);
        rootView = view.findViewById(R.id.root_layout);

        recyclerViewEnglishIndonesia = view.findViewById(R.id.recycler_english_indonesia);

        searchViewEnglishIndonesia.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                kamusEnglishIndonesiaHelper = new EnIdHelper(getContext());
                kamusAdapter = new EnIdAdapter(getContext());

                recyclerViewEnglishIndonesia.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewEnglishIndonesia.setAdapter(kamusAdapter);

                kamusEnglishIndonesiaHelper.open();

                ArrayList<EnIdModel> kamusModels = kamusEnglishIndonesiaHelper.getDataByWord(newText);

                kamusEnglishIndonesiaHelper.close();

                kamusAdapter.addItem(kamusModels);

                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        searchViewEnglishIndonesia.setQuery("",false);
        rootView.requestFocus();
    }
}
