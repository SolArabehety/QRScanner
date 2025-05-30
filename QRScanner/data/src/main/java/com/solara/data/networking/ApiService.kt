package com.solara.data.networking

import retrofit2.http.POST


interface ApiService {

    @POST("seed")
    suspend fun getNewSeed(): SeedResponse

}