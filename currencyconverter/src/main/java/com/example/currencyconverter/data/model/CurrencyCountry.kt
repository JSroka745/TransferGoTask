package com.example.currencyconverter.data.model

import androidx.annotation.DrawableRes

data class CurrencyCountry(
    val countryName: String,
    val currencyName: String,
    val currencyCode: String,
    val maxAmount: Float,
    @DrawableRes val smallFlagRes: Int,
    @DrawableRes val bigFlagRes: Int
)
