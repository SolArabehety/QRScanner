package com.solara.domain.repositories

import com.solara.domain.model.Seed


interface QRRepository {
    suspend fun getNewSeed(): Seed
}
