package com.example.a20213170_lab4;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("all_leagues.php")
    Call<LigasResponse> getAllLeagues();

    @GET("search_all_leagues.php")
    Call<LigasResponseCountry> getAllLeaguesByCountry(@Query("c") String country);

    @GET("lookuptable.php")
    Call<EquipoResponseTable> getTable(
            @Query("l") String idLiga,
            @Query("s") String temporada
    );


}
