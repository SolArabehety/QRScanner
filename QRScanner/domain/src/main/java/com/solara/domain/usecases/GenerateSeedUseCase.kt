package com.solara.domain.usecases

import com.solara.core.utils.Result
import com.solara.domain.model.Seed


interface GenerateSeedUseCase {
    suspend operator fun invoke(): Result<Seed, String>
}