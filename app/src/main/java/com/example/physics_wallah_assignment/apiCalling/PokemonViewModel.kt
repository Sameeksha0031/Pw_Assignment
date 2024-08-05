package com.example.physics_wallah_assignment.apiCalling

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Optional.empty

class PokemonViewModel : ViewModel() {
    val repository = ApiRepository()

    private val _pokemonList = MutableStateFlow<List<Result?>>(emptyList())
    val pokemonList : StateFlow<List<Result?>> = _pokemonList

    private val _pokemonDetail = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail : StateFlow<PokemonDetail?> = _pokemonDetail

    private var localList = mutableListOf<Result?>()

    fun getPokemonList(offset : Int, limit : Int){
        viewModelScope.launch {
            Log.d("ApiChecking", "getPokemonList: ViewModel")
            try {
                val response = repository.getPokemonList(offset,limit)
                if (response != null) {
                    if(response.isSuccessful){
                        Log.d("ApiChecking", "getPokemonList: $response")
                        response.body()?.let {
                            localList.addAll(it.results)
                            _pokemonList.emit(localList)
                        }
                    }
                }
            } catch (e : Exception) {
                Log.d("ApiChecking", "getPokemonList: exception $e")
            }
        }
    }

    fun getPokemonDetail(id : String){
        Log.d("ApiChecking", "getPokemonDetail: ViewModel")
        viewModelScope.launch {
            try {
                val response = repository.getPokemonDetails(id)
                if (response != null) {
                    if(response.isSuccessful){
                        Log.d("ApiChecking", "getPokemonDetail: ViewModel $response")
                        _pokemonDetail.emit(response.body())
                    }
                }
            } catch (e : Exception) {
                Log.d("ApiChecking", "getPokemonDetail: exception $e")
            }
        }
    }
}