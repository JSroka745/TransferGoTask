package com.example.currencyconverter.data.repository

import com.example.currencyconverter.MockCurrencies
import com.example.currencyconverter.constants.StringConstants
import com.example.currencyconverter.data.api.ApiResponse
import com.example.currencyconverter.data.api.RetrofitInstance
import com.example.currencyconverter.data.model.CurrencyCountry
import com.example.currencyconverter.data.model.FxRateResponse
import retrofit2.HttpException
import java.io.IOException

class CurrencyRepository() {
    private val api = RetrofitInstance.api


    suspend fun convertCurrency(from: String, to: String, amount: Float): ApiResponse<FxRateResponse> {
        return try {
            val response = api.getFxRates(from, to, amount)
            ApiResponse.Success(response)
        } catch (ex: HttpException) {

            ApiResponse.Error("Server error (code: ${ex.message}). Try again later.")
        } catch (e: IOException) {
            ApiResponse.Error(StringConstants.noInternetTextDescripton)
        } catch (e: Exception) {
            ApiResponse.Error("Error: ${e.localizedMessage}")
        }
    }


    fun getSupportedCurrencies(): List<CurrencyCountry> {
        return MockCurrencies.supportedCurrencies
    }
}