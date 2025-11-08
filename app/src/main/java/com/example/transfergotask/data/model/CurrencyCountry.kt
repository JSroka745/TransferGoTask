package com.example.transfergotask.data.model

import androidx.annotation.DrawableRes

data class CurrencyCountry(
    val countryName: String,
    val currencyName: String,
    val currencyCode: String,
    @DrawableRes val smallFlagRes: Int,
    @DrawableRes val bigFlagRes: Int
)
