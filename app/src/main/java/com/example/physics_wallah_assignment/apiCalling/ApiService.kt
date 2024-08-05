package com.example.physics_wallah_assignment.apiCalling

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/pokemon/")
    suspend fun getPokemonList(@Query("offset") offset : Int, @Query("limit") limit : Int) : Response<PokemonListResponse>

    @GET("v2/pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id : String) : Response<PokemonDetail>

}