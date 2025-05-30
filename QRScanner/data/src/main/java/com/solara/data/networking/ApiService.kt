package com.solara.data.networking

import com.solara.data.networking.requests.ValidateSeedRequest
import com.solara.data.networking.responses.SeedResponse
import com.solara.data.networking.responses.ValidateSeedResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


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

    @POST("validate")
    suspend fun validateSeed(@Body seed: ValidateSeedRequest): ValidateSeedResponse

}