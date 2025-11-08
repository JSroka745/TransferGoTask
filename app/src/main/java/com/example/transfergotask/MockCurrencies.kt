package com.example.transfergotask

import com.example.transfergotask.data.model.CurrencyCountry

object MockCurrencies {

    val supportedCurrencies = listOf(
        CurrencyCountry(
            "Poland", "Polish zloty", "PLN",
            R.drawable.flag_pl_small, R.drawable.flag_pl_big
        ),
        CurrencyCountry(
            "Germany", "Euro", "EUR",
            R.drawable.flag_de_small, R.drawable.flag_de_big
        ),
        CurrencyCountry(
            "Great Britain", "British Pound", "GBP",
            R.drawable.flag_gb_small, R.drawable.flag_gb_big
        ),
        CurrencyCountry(
            "Ukraine", "Hrivna", "UAH",
            R.drawable.flag_ua_small, R.drawable.flag_ua_big
        )
    )
}