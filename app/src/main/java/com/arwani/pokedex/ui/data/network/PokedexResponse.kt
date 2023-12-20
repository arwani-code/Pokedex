package com.arwani.pokedex.ui.data.network


import com.google.gson.annotations.SerializedName

data class PokedexResponse(
    @SerializedName("abilities")
    val abilities: List<Ability>?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("sprites")
    val sprites: Sprites?,
    @SerializedName("name")
    val name: String?
)