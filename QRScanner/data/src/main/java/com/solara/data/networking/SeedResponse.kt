package com.solara.data.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeedResponse(
    @SerialName("seed")
    val seed: String,

    @SerialName("expires_at")
    val expiredDate: String,
)