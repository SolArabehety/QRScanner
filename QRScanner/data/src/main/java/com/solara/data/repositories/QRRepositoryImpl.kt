package com.solara.data.repositories

import com.solara.data.networking.ApiService
import com.solara.domain.model.Seed
import com.solara.domain.repositories.ConnectionErrorException
import com.solara.domain.repositories.QRRepository
import com.solara.domain.repositories.SeedServerException
import retrofit2.HttpException
import java.io.IOException

internal class QRRepositoryImpl(
    private val apiService: ApiService,
) : QRRepository {

    override suspend fun getNewSeed(): Seed {
        try {
            return apiService.getNewSeed().toModel()
        } catch (e: Exception) {
            throw when (e) {
                is IOException -> ConnectionErrorException("Connection error", e)
                is HttpException -> SeedServerException("Server error", e)
                else -> e
            }
        }
    }

}