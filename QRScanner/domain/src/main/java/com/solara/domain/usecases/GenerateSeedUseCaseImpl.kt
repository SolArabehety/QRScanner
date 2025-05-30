package com.solara.domain.usecases

import com.solara.core.utils.Result
import com.solara.domain.model.Seed
import com.solara.domain.repositories.QRRepository


internal class GenerateSeedUseCaseImpl(
    private val qrRepository: QRRepository,
) : GenerateSeedUseCase {

    override suspend fun invoke(): Result<Seed, String> {
        return try {
            Result.Success(qrRepository.getNewSeed())
        } catch (e: Exception) {
            Result.Error("Error")
        }

    }
}