package com.example.a20213170_lab4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a20213170_lab4.R;
import com.example.a20213170_lab4.models.CardLigaModel;

import java.util.List;

public class CardLigaAdapter extends RecyclerView.Adapter<CardLigaAdapter.LigaViewHolder> {

    // Lista de modelos
    private List<CardLigaModel> ligaList;

    // Constructor del adaptador
    public CardLigaAdapter(List<CardLigaModel> ligaList) {
        this.ligaList = ligaList;
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de cada ítem del RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ligas, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        // Obtener el modelo actual
        CardLigaModel liga = ligaList.get(position);

        // Asignar los valores del modelo a los elementos de la vista
        holder.txtNombreLiga.setText(liga.getNombreLiga());
        holder.txtCategoriaDeporte.setText("Deporte: " + liga.getCategoriaDeporte());
        holder.txtNombreAlternativo.setText(liga.getNombreAlternativo());
    }

    @Override
    public int getItemCount() {
        // Retornar el tamaño de la lista
        return ligaList.size();
    }

    // ViewHolder para el RecyclerView
    public static class LigaViewHolder extends RecyclerView.ViewHolder {

        // Elementos de la vista del CardView
        TextView txtNombreLiga;
        TextView txtCategoriaDeporte;
        TextView txtNombreAlternativo;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar las vistas
            txtNombreLiga = itemView.findViewById(R.id.txtNombreLiga);
            txtCategoriaDeporte = itemView.findViewById(R.id.txtCategoriaDeporte);
            txtNombreAlternativo = itemView.findViewById(R.id.txtNombreAlternativo);
        }
    }
}
