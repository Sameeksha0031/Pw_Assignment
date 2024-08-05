package com.example.physics_wallah_assignment.apiCalling
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class PokemonListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
)

@Serializable
data class Result(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)