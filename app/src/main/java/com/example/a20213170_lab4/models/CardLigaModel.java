package com.example.a20213170_lab4.models;

public class CardLigaModel {
    private String idLeague;
    private String nombreLiga;
    private String categoriaDeporte;
    private String nombreAlternativo;

    // Constructor
    public CardLigaModel(String idLeague, String nombreLiga, String categoriaDeporte, String nombreAlternativo) {
        this.idLeague = idLeague;
        this.nombreLiga = nombreLiga;
        this.categoriaDeporte = categoriaDeporte;
        this.nombreAlternativo = nombreAlternativo;
    }

    // Getters y Setters
    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getNombreLiga() {
        return nombreLiga;
    }

    public void setNombreLiga(String nombreLiga) {
        this.nombreLiga = nombreLiga;
    }

    public String getCategoriaDeporte() {
        return categoriaDeporte;
    }

    public void setCategoriaDeporte(String categoriaDeporte) {
        this.categoriaDeporte = categoriaDeporte;
    }

    public String getNombreAlternativo() {
        return nombreAlternativo;
    }

    public void setNombreAlternativo(String nombreAlternativo) {
        this.nombreAlternativo = nombreAlternativo;
    }
}
