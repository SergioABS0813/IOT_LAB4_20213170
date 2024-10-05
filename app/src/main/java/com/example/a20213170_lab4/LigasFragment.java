package com.example.a20213170_lab4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a20213170_lab4.adapter.CardLigaAdapter;
import com.example.a20213170_lab4.models.CardLigaModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LigasFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardLigaAdapter adapter;
    private List<CardLigaModel> ligaList;

    private Button btnSearchLiga;
    private EditText etBuscarPais;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ligas, container, false);

        btnSearchLiga = view.findViewById(R.id.btnSearchLiga);
        etBuscarPais = view.findViewById(R.id.etBuscarPais);
        btnSearchLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais = etBuscarPais.getText().toString().trim();

                if (TextUtils.isEmpty(pais)) {
                    // Si el EditText está vacío, hacer la consulta general (todas las ligas)
                    fetchAllLigas();
                } else {
                    // Si se escribe algo en el EditText, buscar por país
                    fetchLigasByCountry(pais);
                }
            }
        });

        recyclerView = view.findViewById(R.id.recyclerViewLigas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        return view;



    }

    private void fetchAllLigas() {

        adapter = new CardLigaAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        List<CardLigaModel> ligasObtenidas = new ArrayList<>();  // Aquí irían los datos obtenidos

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<LigasResponse> call = api.getAllLeagues();

        call.enqueue(new Callback<LigasResponse>() {
            @Override
            public void onResponse(Call<LigasResponse> call, Response<LigasResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<CardLigaModel> ligasObtenidas = response.body().getLeagues(); //obtenemos las ligas
                    adapter = new CardLigaAdapter(ligasObtenidas); //las seteamos en el adapter
                    recyclerView.setAdapter(adapter);

                }else{
                    Toast.makeText(getContext(), "Error en la emisión de datos, vuelva a intentarlo por favor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LigasResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión, vuelva a intentarlo por favor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchLigasByCountry(String pais) {
        List<CardLigaModel> ligasObtenidas = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<LigasResponseCountry> call = api.getAllLeaguesByCountry(pais);

        call.enqueue(new Callback<LigasResponseCountry>() {
            @Override
            public void onResponse(Call<LigasResponseCountry> call, Response<LigasResponseCountry> response) {

                if(response.isSuccessful() && response.body() != null) {
                    // Verificar si el campo "countries" está vacío o es nulo
                    if (response.body().getCountries() == null || response.body().getCountries().isEmpty()) {
                        Toast.makeText(getContext(), "El país ingresado no tiene ligas disponibles o no existe, por favor vuelva a ingresar uno de nuevo", Toast.LENGTH_SHORT).show();
                    } else {
                        // mensaje de éxito
                        Toast.makeText(getContext(), "Ligas encontradas", Toast.LENGTH_SHORT).show();
                        List<CardLigaModel> ligasObtenidas = response.body().getCountries(); // obtenemos las ligas
                        adapter = new CardLigaAdapter(ligasObtenidas); // las seteamos en el adapter
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Error en la emisión de datos, vuelva a intentarlo por favor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LigasResponseCountry> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión, vuelva a intentarlo por favor", Toast.LENGTH_SHORT).show();

            }
        });
    }
}