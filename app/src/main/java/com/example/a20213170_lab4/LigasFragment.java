package com.example.a20213170_lab4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a20213170_lab4.adapter.CardLigaAdapter;
import com.example.a20213170_lab4.models.CardLigaModel;

import java.util.ArrayList;
import java.util.List;

public class LigasFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardLigaAdapter adapter;
    private List<CardLigaModel> ligaList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ligas, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLigas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        ligaList = new ArrayList<>();
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));
        ligaList.add(new CardLigaModel("4328", "English Premier League", "Soccer", "Premier League, EPL"));
        ligaList.add(new CardLigaModel("4331", "German Bundesliga", "Soccer", "Bundesliga"));

        adapter = new CardLigaAdapter(ligaList);
        recyclerView.setAdapter(adapter);


        return view;



    }
}