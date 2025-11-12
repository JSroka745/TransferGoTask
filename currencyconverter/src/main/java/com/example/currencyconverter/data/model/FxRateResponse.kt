package com.example.currencyconverter.data.model

data class FxRateResponse(
    val from: String,
    val fromAmount: Int,
    val rate: Float,
    val to: String,
    val toAmount: Float
)