package com.example.a20213170_lab4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a20213170_lab4.R;
import com.example.a20213170_lab4.models.EquipoModel;
import com.example.a20213170_lab4.models.EventsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<EventsModel> eventsList;
    private Context context;

    public EventsAdapter(List<EventsModel> eventsList, Context context) {
        this.eventsList = eventsList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_events, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        // Obtener el evento actual
        EventsModel event = eventsList.get(position);

        // Configurar los datos en las vistas
        holder.txtNombreCompetencia.setText(event.getStrLeague());
        holder.txtRonda.setText("Ronda: " + event.getIntRound());
        holder.txtEquipoLocal.setText("Equipo local: " + event.getStrHomeTeam());
        holder.txtEquipoVisitante.setText("Equipo visitante: " + event.getStrAwayTeam());
        holder.txtResultado.setText("Resultado: " + event.getIntHomeScore() + "-" + event.getIntAwayScore());
        holder.txtFechaEncuentro.setText("Fecha de encuentro: " + event.getDateEvent());
        holder.txtCantidadEspectadores.setText("Cantidad de asistentes: " + (event.getIntSpectators() != null ? event.getIntSpectators() : "Null"));

        // Cargar las imágenes usando Picasso
        Picasso.get().load(event.getStrLeagueBadge()).into(holder.imgCompetencia);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreCompetencia, txtRonda, txtEquipoLocal, txtEquipoVisitante, txtResultado, txtFechaEncuentro, txtCantidadEspectadores;
        ImageView imgCompetencia;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            // Enlazar las vistas del XML
            txtNombreCompetencia = itemView.findViewById(R.id.txtNombreCompetencia);
            txtRonda = itemView.findViewById(R.id.txtRonda);
            txtEquipoLocal = itemView.findViewById(R.id.txtEquipoLocal);
            txtEquipoVisitante = itemView.findViewById(R.id.txtEquipoVisitante);
            txtResultado = itemView.findViewById(R.id.txtResultado);
            txtFechaEncuentro = itemView.findViewById(R.id.txtFechaEncuentro);
            txtCantidadEspectadores = itemView.findViewById(R.id.txtCantidadEspectadores);
            imgCompetencia = itemView.findViewById(R.id.imgCompetencia);
        }
    }
}
