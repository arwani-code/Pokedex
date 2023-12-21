package com.arwani.pokedex.data.network


import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String?
)