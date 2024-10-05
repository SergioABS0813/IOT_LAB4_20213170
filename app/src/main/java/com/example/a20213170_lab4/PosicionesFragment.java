package com.example.a20213170_lab4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a20213170_lab4.adapter.CardLigaAdapter;
import com.example.a20213170_lab4.adapter.EquipoAdapter;
import com.example.a20213170_lab4.models.CardLigaModel;
import com.example.a20213170_lab4.models.EquipoModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PosicionesFragment extends Fragment {

    EditText etIdLigaPosiciones, etTemporadaPosiciones;
    Button btnSearchPosiciones;

    private RecyclerView recyclerViewPosiciones;
    private EquipoAdapter adapter;
    private List<EquipoModel> ligaList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posiciones, container, false);

        etIdLigaPosiciones = view.findViewById(R.id.etIdLigaPosiciones);
        etTemporadaPosiciones = view.findViewById(R.id.etTemporadaPosiciones);
        btnSearchPosiciones = view.findViewById(R.id.btnSearchPosiciones);
        recyclerViewPosiciones = view.findViewById(R.id.recyclerViewPosiciones);
        recyclerViewPosiciones.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewPosiciones.setHasFixedSize(true);
        recyclerViewPosiciones.setNestedScrollingEnabled(true);

        btnSearchPosiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String posiciones = etIdLigaPosiciones.getText().toString().trim();
                String temporada = etTemporadaPosiciones.getText().toString().trim();

                if (TextUtils.isEmpty(posiciones) || TextUtils.isEmpty(temporada)) {
                    Toast.makeText(getContext(), "Falta colocar el id de la Liga o la Temporada, por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();

                }else{
                    fetchPosiciones(posiciones, temporada);
                }
            }
        });

        return view;
    }

    private void fetchPosiciones(String posiciones, String temporada) {

        if (adapter != null) {
            adapter = new EquipoAdapter(new ArrayList<>(), getContext());
            recyclerViewPosiciones.setAdapter(adapter);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<EquipoResponseTable> call = api.getTable(posiciones, temporada);

        call.enqueue(new Callback<EquipoResponseTable>() {
            @Override
            public void onResponse(Call<EquipoResponseTable> call, Response<EquipoResponseTable> response) {
                if (response.body().getTable() == null || response.body().getTable().isEmpty()) {
                    Toast.makeText(getContext(), "El país ingresado no tiene ligas disponibles o no existe, por favor vuelva a ingresar uno de nuevo", Toast.LENGTH_SHORT).show();
                } else {

                    // mensaje de éxito
                    Toast.makeText(getContext(), "VALIDO", Toast.LENGTH_SHORT).show();
                    List<EquipoModel> equipoModelList = response.body().getTable(); // obtenemos la tabla
                    adapter = new EquipoAdapter(equipoModelList, getContext()); // las seteamos en el adapter
                    recyclerViewPosiciones.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<EquipoResponseTable> call, Throwable t) {
                boolean errorHandled = false; // Bandera para saber si ya se manejó un error específicou

                try {
                    // Intentamos convertir el ID de liga a entero
                    int idLiga = Integer.parseInt(posiciones);

                    // Ahora validamos el formato de la temporada
                    if (temporada.matches("^20\\d{2}-20\\d{2}$")) {
                        String[] years = temporada.split("-");

                        try {
                            // Intentamos parsear ambas partes a enteros
                            int firstYear = Integer.parseInt(years[0]);
                            int secondYear = Integer.parseInt(years[1]);

                            // Verificamos si el segundo año es uno más que el primero
                            if (secondYear != firstYear + 1) {
                                Toast.makeText(getContext(), "El segundo año debe ser uno más que el primero.", Toast.LENGTH_SHORT).show();
                                errorHandled = true;
                            }

                            // Verificamos que no exceda el año 2025
                            if (secondYear > 2025) {
                                Toast.makeText(getContext(), "El último año no puede exceder la temporada 2025", Toast.LENGTH_SHORT).show();
                                errorHandled = true;
                            }

                        } catch (NumberFormatException e) {
                            // Validamos si los años no son enteros
                            Toast.makeText(getContext(), "El formato de la temporada es incorrecto.", Toast.LENGTH_SHORT).show();
                            errorHandled = true;
                        }
                    } else {
                        // Validamos si el formato de la temporada no es válido
                        Toast.makeText(getContext(), "El formato de la temporada debe ser 20XX-20XX.", Toast.LENGTH_SHORT).show();
                        errorHandled = true;
                    }

                } catch (NumberFormatException e) {
                    // verificamos si el ID de la liga no es un número entero, mostramos un mensaje de error
                    Toast.makeText(getContext(), "El ID de la liga debe ser un número entero.", Toast.LENGTH_SHORT).show();
                    errorHandled = true;  // Error manejado
                }

                // Si ningún error específico fue detectado, mostramos el mensaje del id inexistente
                if (!errorHandled) {
                    Log.d("PosicionesFragment", "No existe id de la liga");
                    Toast.makeText(getContext(), "El id de la liga no existe", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


}