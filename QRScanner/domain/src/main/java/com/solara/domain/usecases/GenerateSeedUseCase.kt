package com.solara.domain.usecases

import com.solara.core.utils.Result


interface GenerateSeedUseCase {
    suspend operator fun invoke(): Result<String, String>
}