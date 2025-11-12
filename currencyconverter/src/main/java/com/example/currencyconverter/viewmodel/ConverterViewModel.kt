package com.example.currencyconverter.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.constants.StringConstants
import com.example.currencyconverter.data.api.ApiResponse
import com.example.currencyconverter.data.model.CurrencyCountry
import com.example.currencyconverter.listeners.NetworkConnectivityObserver
import com.example.currencyconverter.listeners.NetworkStatusListener
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class ConverterUiState(
    val fromCurrency: CurrencyCountry,
    val toCurrency: CurrencyCountry,
    val amount: Float = 300f,
    val convertedAmount: Float = 0f,
    val rate: Float = 0f,
    val errorMessage: String? = null,
    val smallErrorMessage: String? = null,
)

class ConverterViewModel(
    private val repository: CurrencyRepository,
    private val networkObserver: NetworkConnectivityObserver
) : ViewModel(), NetworkStatusListener {

    private val currencies = repository.getSupportedCurrencies()
    private var isNetworkAvailable = false

    private var conversionJob: Job? = null

    private val _uiState = mutableStateOf(
        ConverterUiState(
            fromCurrency = currencies.first { it.currencyCode == "PLN" },
            toCurrency = currencies.first { it.currencyCode == "UAH" }
        )
    )
    val uiState: State<ConverterUiState> = _uiState

    init {
        networkObserver.startListening(this)
        fetchConversion(immediately = true)
    }

    fun swapCurrencies() {
        _uiState.value = _uiState.value.copy(
            fromCurrency = _uiState.value.toCurrency,
            toCurrency = _uiState.value.fromCurrency,
            convertedAmount = 0f,
            rate = 0f,
        )
        if (validateAmount()) {
            fetchConversion(true)
        }
    }

    fun updateAmount(newAmount: Float) {
        if (newAmount < 0) return
        _uiState.value = _uiState.value.copy(amount = newAmount)
        if (validateAmount()) {
            fetchConversion()
        }

    }

    fun updateFromCurrency(newCurrency: CurrencyCountry) {

        if (newCurrency.currencyCode == _uiState.value.toCurrency.currencyCode) {
            swapCurrencies()
        } else {
            _uiState.value = _uiState.value.copy(fromCurrency = newCurrency)
            fetchConversion()
        }
    }

    fun updateToCurrency(newCurrency: CurrencyCountry) {
        if (newCurrency.currencyCode == _uiState.value.fromCurrency.currencyCode) {
            swapCurrencies()
        } else {
            _uiState.value = _uiState.value.copy(toCurrency = newCurrency)
            fetchConversion()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun fetchConversion(immediately: Boolean = false) {
        conversionJob?.cancel()

        conversionJob = viewModelScope.launch {
            if (!immediately) {
                delay(500L)
            }

            if (!validateAmount()) {
                _uiState.value = _uiState.value.copy(convertedAmount = 0f, rate = 0f)
                return@launch
            }

            if (!isNetworkAvailable) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = StringConstants.noInternetTextDescripton
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(errorMessage = null)

            val currentState = _uiState.value
            val response = repository.convertCurrency(
                from = currentState.fromCurrency.currencyCode,
                to = currentState.toCurrency.currencyCode,
                amount = currentState.amount
            )

            _uiState.value = when (response) {
                is ApiResponse.Success -> _uiState.value.copy(
                    rate = response.data.rate,
                    convertedAmount = response.data.toAmount
                )

                is ApiResponse.Error -> _uiState.value.copy(
                    errorMessage = response.message
                )

                is ApiResponse.Loading -> _uiState.value
            }
        }
    }

    override fun onNetworkStatusChanged(isConnected: Boolean) {
        isNetworkAvailable = isConnected
        if (!isConnected) {
            _uiState.value = _uiState.value.copy(
                errorMessage = StringConstants.noInternetTextDescripton
            )
        } else {
            if (_uiState.value.errorMessage?.contains(StringConstants.noInternetTextDescripton) == true) {
                clearError()
                fetchConversion()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkObserver.stopListening()
    }


    private fun validateAmount(): Boolean {
        val currentAmount = _uiState.value.amount
        val limit = _uiState.value.fromCurrency.maxAmount
        val isTooBig = currentAmount > limit

        if (isTooBig) {
            val symbols = DecimalFormatSymbols(Locale("pl", "PL"))
            val formatter = DecimalFormat("###,###", symbols)
            val formattedLimit = formatter.format(limit)

            _uiState.value = _uiState.value.copy(
                smallErrorMessage = "Maximum sending amount: $formattedLimit ${uiState.value.fromCurrency.currencyCode}"
            )
            return false
        } else {
            _uiState.value = _uiState.value.copy(
                smallErrorMessage = null
            )
        }
        return true
    }
}

