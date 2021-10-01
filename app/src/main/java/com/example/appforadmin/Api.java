package com.example.appforadmin;

import com.example.appforadmin.contact.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("rest/players/list")
    Call<List<Player>> getAllPlayers();

    @GET("rest/players/{id}")
    Call<Player> getPlayerById(@Path("id") Long id);

    @POST("rest/players")
    Call<Player> createPlayer(@Body Player player);

    @POST("rest/players/{id}")
    Call<Player> updatePlayer(@Path("id") Long id, @Body Player player);

    @DELETE("rest/players/{id}")
    Call<Player> deletePlayer(@Path("id") Long id);
}
