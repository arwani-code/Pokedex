package com.arwani.pokedex.ui.data.network


import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String?
)