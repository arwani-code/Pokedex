package com.arwani.pokedex.ui.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon")
    suspend fun getPokedexs(): PokedexResponse

    @GET("pokemon/{id}")
    suspend fun getDetailPokedex(
        @Path("id") id: Int
    ): PokedexResponse
}