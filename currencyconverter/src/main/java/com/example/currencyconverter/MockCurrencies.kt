package com.example.currencyconverter

import com.example.currencyconverter.data.model.CurrencyCountry

object MockCurrencies {

    val supportedCurrencies = listOf(
        CurrencyCountry(
            "Poland", "Polish zloty", "PLN", 20000f,
            R.drawable.flag_pl_small, R.drawable.flag_pl_big
        ),
        CurrencyCountry(
            "Germany", "Euro", "EUR", 5000f,
            R.drawable.flag_de_small, R.drawable.flag_de_big
        ),
        CurrencyCountry(
            "Great Britain", "British Pound", "GBP", 1000f,
            R.drawable.flag_gb_small, R.drawable.flag_gb_big
        ),
        CurrencyCountry(
            "Ukraine", "Hrivna", "UAH", 50000f,
            R.drawable.flag_ua_small, R.drawable.flag_ua_big
        )
    )
}