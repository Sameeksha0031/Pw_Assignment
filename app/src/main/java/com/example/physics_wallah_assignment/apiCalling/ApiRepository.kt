package com.example.physics_wallah_assignment.apiCalling

import retrofit2.Response

class ApiRepository {
    val retrofit = ApiClient.getApi()
    val apiService = retrofit?.create(ApiService::class.java)

    suspend fun getPokemonList(offset : Int,limit : Int): Response<PokemonListResponse>? = apiService?.getPokemonList(offset,limit)

    suspend fun getPokemonDetails(id: String): Response<PokemonDetail>? {
        return apiService?.getPokemonDetail(id)
    }
}