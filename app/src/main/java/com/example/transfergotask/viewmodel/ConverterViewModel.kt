package com.example.transfergotask.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transfergotask.data.api.ApiResponse
import com.example.transfergotask.data.model.CurrencyCountry
import com.example.transfergotask.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import kotlin.compareTo

class ConverterViewModel(
    private val repository: CurrencyRepository = CurrencyRepository()
) : ViewModel() {


    val currencies = repository.getSupportedCurrencies()

    var fromCurrency by mutableStateOf(currencies.first { it.currencyCode == "PLN" })

    var toCurrency by mutableStateOf(currencies.first { it.currencyCode == "UAH" })
        private set

    var amount by mutableFloatStateOf(300F)
        private set

    var convertedAmount by mutableStateOf(0.0)
        private set

    var rate by mutableStateOf(0.0)
        private set


    fun swapCurrencies() {
        val temp = fromCurrency
        fromCurrency = toCurrency
        toCurrency = temp
        fetchConversion()
    }

    fun updateAmount(newAmount: Float) {
        amount = newAmount
        fetchConversion()
    }

    fun updateFromCurrency(newCurrency: CurrencyCountry) {
        fromCurrency = newCurrency
        fetchConversion()
    }

    fun updateToCurrency(newCurrency: CurrencyCountry) {
        toCurrency = newCurrency
        fetchConversion()
    }

    fun fetchConversion() {
        viewModelScope.launch {

            val response = repository.convertCurrency(fromCurrency.currencyCode, toCurrency.currencyCode, amount)

            when (response) {
                is ApiResponse.Success -> {
                    rate = response.data.rate
                    convertedAmount = response.data.toAmount
                }

                is ApiResponse.Error -> {
                    // Handle error
                }

                is ApiResponse.Loading -> {
                    // Handle loading state
                }
            }

        }
    }
}
