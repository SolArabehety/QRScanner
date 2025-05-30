package com.solara.domain.repositories

import com.solara.domain.model.Seed


interface QRRepository {
    suspend fun getNewSeed(): Seed
}

class ConnectionErrorException(
    message: String,
    exception: Exception,
) : Exception(message, exception)


class SeedServerException(
    message: String,
    exception: Exception,
) : Exception(message, exception)


