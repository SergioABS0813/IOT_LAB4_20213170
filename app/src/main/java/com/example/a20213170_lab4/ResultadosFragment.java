package com.example.a20213170_lab4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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

import com.example.a20213170_lab4.adapter.EquipoAdapter;
import com.example.a20213170_lab4.adapter.EventsAdapter;
import com.example.a20213170_lab4.models.EquipoModel;
import com.example.a20213170_lab4.models.EventsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosFragment extends Fragment implements SensorEventListener{

    Button btnSearchResultados;
    EditText etIdLigaResultados, etTemporadaResultados, etRondaResultados;
    RecyclerView recyclerViewResultados;
    private int cantidadPartidos;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private static final float SHAKE_THRESHOLD = 20.0f; // Umbral de 20 m/s²
    private long lastUpdate = 0;
    List<EventsModel> eventsModelList;


    private EventsAdapter adapter;
    private List<EventsModel> eventsModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);
        recyclerViewResultados = view.findViewById(R.id.recyclerViewResultados);

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        btnSearchResultados = view.findViewById(R.id.btnSearchResultados);
        etIdLigaResultados = view.findViewById(R.id.etIdLigaResultados);
        etTemporadaResultados = view.findViewById(R.id.etTemporadaResultados);
        etRondaResultados = view.findViewById(R.id.etRondaResultados);
        recyclerViewResultados = view.findViewById(R.id.recyclerViewResultados);
        recyclerViewResultados.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewResultados.setHasFixedSize(true);
        recyclerViewResultados.setNestedScrollingEnabled(true);

        btnSearchResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idLiga = etIdLigaResultados.getText().toString().trim();
                String temporada = etTemporadaResultados.getText().toString().trim();
                String ronda = etRondaResultados.getText().toString().trim();

                if (TextUtils.isEmpty(idLiga) || TextUtils.isEmpty(temporada) || TextUtils.isEmpty(ronda)) {
                    Toast.makeText(getContext(), "Falta colocar el id de la Liga o la Temporada, por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();

                }else{
                    fetchApi(idLiga, temporada, ronda);
                }

            }
        });

        return view;
    }

    private void fetchApi(String idLiga, String temporada, String ronda){
        //Logica de API
        //Limpia y permite el reinicio en cada búsqueda
        if (adapter != null) {
            adapter = new EventsAdapter(new ArrayList<>(), getContext());
            recyclerViewResultados.setAdapter(adapter);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<EventResponse> call = api.getEvent(idLiga, ronda, temporada);

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.body().getEvents() == null || response.body().getEvents().isEmpty()) {
                    boolean errorHandled = false; // Bandera para saber si ya se manejó un error específicou

                    try {
                        // Intentamos convertir el ID de liga a entero
                        int idLigaInt = Integer.parseInt(idLiga);

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

                    //tratamos de convertir ronda a entero
                    try {
                        int rondaInt = Integer.parseInt(ronda);

                        //Ronda debe ser mayor que cero y menor que (N-1) equipos x2
                        //teniendo la cantidad de partidos en una ronda, podemos determinar la cantidad de equipos y por lo tanto la cantidad de rondas en total que habrá en esa LIGA
                        int cantidadEquipos = cantidadPartidos*2;
                        if(0 < rondaInt && rondaInt <=  (cantidadEquipos - 1)*2){
                            //valido
                        }else{
                            Toast.makeText(getContext(), "El número de rondas no puede ser mayor que " + (cantidadEquipos - 1)*2 + " ni menor que 0", Toast.LENGTH_SHORT).show();
                            errorHandled = true;

                        }
                    }catch (NumberFormatException e){
                        Toast.makeText(getContext(), "El ID de la ronda debe ser entero", Toast.LENGTH_SHORT).show();
                        errorHandled = true;
                    }

                    // Si ningún error específico fue detectado, mostramos el mensaje del id inexistente
                    if (!errorHandled) {
                        Log.d("PosicionesFragment", "No existe id de la liga");
                        Toast.makeText(getContext(), "El id de la liga no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    // mensaje de éxito
                    Toast.makeText(getContext(), "VALIDO RESULTADOS", Toast.LENGTH_SHORT).show();
                    eventsModelList = response.body().getEvents(); // obtenemos la tabla de eventos
                    cantidadPartidos = eventsModelList.size();
                    adapter = new EventsAdapter(eventsModelList, getContext()); // las seteamos en el adapter
                    recyclerViewResultados.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                boolean errorHandled = false; // Bandera para saber si ya se manejó un error específicou

                try {
                    // Intentamos convertir el ID de liga a entero
                    int idLigaInt = Integer.parseInt(idLiga);

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

                //tratamos de convertir ronda a entero
                try {
                    int rondaInt = Integer.parseInt(ronda);

                    //Ronda debe ser mayor que cero y menor que (N-1) equipos x2
                    //teniendo la cantidad de partidos en una ronda, podemos determinar la cantidad de equipos y por lo tanto la cantidad de rondas en total que habrá en esa LIGA
                    int cantidadEquipos = cantidadPartidos*2;
                    if(0 < rondaInt && rondaInt <=  (cantidadEquipos - 1)*2){
                        //valido
                    }else{
                        Toast.makeText(getContext(), "El número de rondas no puede ser mayor que " + (cantidadEquipos - 1)*2 + " ni menor que 0", Toast.LENGTH_SHORT).show();
                        errorHandled = true;

                    }
                }catch (NumberFormatException e){
                    Toast.makeText(getContext(), "El ID de la ronda debe ser entero", Toast.LENGTH_SHORT).show();
                    errorHandled = true;
                }

                // Si ningún error específico fue detectado, mostramos el mensaje del id inexistente
                if (!errorHandled) {
                    Log.d("PosicionesFragment", "No existe id de la liga");
                    Toast.makeText(getContext(), "El id de la liga no existe", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        // Registramos el listener del acelerómetro cuando el Fragment está activo
        if (acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Desregistramos el listener del acelerómetro cuando el Fragment se pausa
        sensorManager.unregisterListener(this);
    }


    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();

            // Solo procesamos una vez cada 500ms
            if ((currentTime - lastUpdate) > 500) {
                lastUpdate = currentTime;

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Calculamos la magnitud de la aceleración
                float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

                // Si la aceleración supera el umbral, mostramos el diálogo
                if (acceleration > SHAKE_THRESHOLD) {
                    mostrarDialogoConfirmacion();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void mostrarDialogoConfirmacion() {
        if (!eventsModelList.isEmpty()) {
            // Mostrar el diálogo de confirmación para deshacer la última acción
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirmación")
                    .setMessage("¿Deseas eliminar el último resultado?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar el último resultado de la lista
                        eventsModelList.remove(eventsModelList.size() - 1);
                        recyclerViewResultados.getAdapter().notifyDataSetChanged();
                        Toast.makeText(getContext(), "Último resultado eliminado", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            Toast.makeText(getContext(), "No hay resultados para eliminar", Toast.LENGTH_SHORT).show();
        }
    }



}