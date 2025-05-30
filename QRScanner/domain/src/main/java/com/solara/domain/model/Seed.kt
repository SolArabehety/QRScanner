package com.solara.domain.model

import java.util.Date


data class Seed(
    val value: String,
    val expiredDate: Date
){
    fun getSecondsRemaining(): Int {
        val currentTime = System.currentTimeMillis()
        val expirationTime = expiredDate.time
        val diffMillis = expirationTime - currentTime
        val seconds = diffMillis / 1000
        return if (seconds > Int.MAX_VALUE) Int.MAX_VALUE else seconds.coerceAtLeast(0).toInt()
    }

}
