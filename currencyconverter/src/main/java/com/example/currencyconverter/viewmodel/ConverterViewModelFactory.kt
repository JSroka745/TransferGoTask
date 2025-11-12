package com.example.currencyconverter.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.data.network.NetworkConnectivityObserver
import com.example.currencyconverter.data.repository.CurrencyRepository


class ConverterViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            val networkObserver = NetworkConnectivityObserver(application)
            val repository = CurrencyRepository()

            @Suppress("UNCHECKED_CAST")
            return ConverterViewModel(repository, networkObserver) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
