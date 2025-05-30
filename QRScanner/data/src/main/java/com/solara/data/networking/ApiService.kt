package com.solara.data.networking

import retrofit2.http.POST


/**
 * Defines API endpoints for seed-related operations.
 */
interface ApiService {

    /**
     * Fetches a new seed from the backend.
     *
     * @return [SeedResponse] with seed value and expiration date.
     */
    @POST("seed")
    suspend fun getNewSeed(): SeedResponse

}