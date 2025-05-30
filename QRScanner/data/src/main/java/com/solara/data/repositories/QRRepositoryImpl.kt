package com.solara.data.repositories

import com.solara.data.networking.ApiService
import com.solara.domain.model.Seed
import com.solara.domain.repositories.QRRepository

internal class QRRepositoryImpl(
    private val apiService: ApiService,
) : QRRepository {

    override suspend fun getNewSeed(): Seed {
        return apiService.getNewSeed().toModel()
    }


}