package com.solara.data.networking

import com.solara.domain.model.Seed
import com.solara.data.networking.serializers.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import java.util.Date

@Serializable
data class SeedResponse(
    @SerialName("seed")
    val seed: String,

    @SerialName("expires_at")
    @Serializable(with = DateSerializer::class)
    val expiredDate: Date,
) {

    fun toModel() = Seed(
        value = seed,
        expiredDate = expiredDate
    )
}