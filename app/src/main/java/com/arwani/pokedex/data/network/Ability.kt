package com.arwani.pokedex.data.network


import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability")
    val ability: AbilityX?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?,
    @SerializedName("slot")
    val slot: Int?
)