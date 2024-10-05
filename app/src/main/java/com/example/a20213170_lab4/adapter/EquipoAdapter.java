package com.example.a20213170_lab4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a20213170_lab4.R;
import com.example.a20213170_lab4.models.EquipoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<EquipoModel> equipoList;
    private Context context;

    // Constructor para pasar la lista de equipos
    public EquipoAdapter(List<EquipoModel> equipoList, Context context) {
        this.equipoList = equipoList;
        this.context = context;
    }

    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de la tarjeta (card_ligas.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_posiciones, parent, false);
        return new EquipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
        // Obtener el equipo actual de la lista
        EquipoModel equipo = equipoList.get(position);

        // Vincular los datos del equipo con las vistas
        holder.txtNombreEquipo.setText(equipo.getStrTeam());
        holder.txtRanking.setText("Ranking: " + equipo.getIntRank());
        holder.txtVictoriasDerrotasEmpates.setText("Victorias: " + equipo.getIntWin() + " | Empates: " + equipo.getIntDraw() + " | Derrotas: " + equipo.getIntLoss());
        holder.txtGoles.setText("Goles anotados: " + equipo.getIntGoalsFor() + " | Goles concedidos: " + equipo.getIntGoalsAgainst() + " | Diferencia de goles: " + equipo.getIntGoalDifference());

        // Setear la imagen del logo (si es un recurso local)
        Picasso.get().load(equipo.getStrBadge()).into(holder.imgBadge);


    }

    @Override
    public int getItemCount() {
        return equipoList.size();
    }

    // Clase interna para el ViewHolder (maneja las vistas de cada tarjeta)
    public class EquipoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreEquipo, txtRanking, txtCategoriaDeporte, txtVictoriasDerrotasEmpates, txtGoles;
        ImageView imgBadge;

        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Vincular los elementos del layout card_ligas.xml con los campos del ViewHolder
            txtNombreEquipo = itemView.findViewById(R.id.txtNombreEquipo);
            txtRanking = itemView.findViewById(R.id.txtRanking);
            txtCategoriaDeporte = itemView.findViewById(R.id.txtCategoriaDeporte);
            txtVictoriasDerrotasEmpates = itemView.findViewById(R.id.txtVictoriasDerrotasyEmpates);
            txtGoles = itemView.findViewById(R.id.txtGoles);
            imgBadge = itemView.findViewById(R.id.imgBadge);
        }
    }
}
