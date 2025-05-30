package com.solara.domain.usecases

import com.solara.core.utils.Result
import com.solara.domain.model.Seed
import com.solara.domain.model.SeedError


interface GenerateSeedUseCase {
    suspend operator fun invoke(): Result<Seed, SeedError>
}