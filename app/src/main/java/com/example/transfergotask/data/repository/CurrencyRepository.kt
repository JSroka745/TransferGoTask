package com.example.transfergotask.data.repository

import com.example.transfergotask.MockCurrencies
import com.example.transfergotask.data.api.ApiResponse
import com.example.transfergotask.data.api.RetrofitInstance
import com.example.transfergotask.data.model.CurrencyCountry
import com.example.transfergotask.data.model.FxRateResponse

class CurrencyRepository {
    private val api = RetrofitInstance.api

    suspend fun convertCurrency(from: String, to: String, amount: Float): ApiResponse<FxRateResponse> {
        val x = api.getFxRates(from, to, amount)
        return ApiResponse.Success(x)
    }

    fun getSupportedCurrencies(): List<CurrencyCountry> {
        return MockCurrencies.supportedCurrencies
    }
}



