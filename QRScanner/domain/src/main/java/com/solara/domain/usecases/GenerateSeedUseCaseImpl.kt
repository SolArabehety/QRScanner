package com.solara.domain.usecases

import com.solara.core.utils.Result


internal class GenerateSeedUseCaseImpl(

) : GenerateSeedUseCase {

    override suspend fun invoke(): Result<String, String> {
        return try {
            Result.Success("new seed")
        } catch (e: Exception) {
            Result.Error("Error")
        }

    }
}