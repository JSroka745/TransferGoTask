package com.example.transfergotask.data.model

data class FxRateResponse(
    val from: String,
    val fromAmount: Int,
    val rate: Double,
    val to: String,
    val toAmount: Double
)