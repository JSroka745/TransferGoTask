package com.example.currencyconverter.ui.screens


import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.MockCurrencies
import com.example.currencyconverter.constants.StringConstants
import com.example.currencyconverter.ui.components.CurrencyCard
import com.example.currencyconverter.ui.components.CurrencyPickerBottomSheet
import com.example.currencyconverter.ui.components.ErrorBanner
import com.example.currencyconverter.ui.components.SmallErrorComponent
import com.example.currencyconverter.ui.components.SwapAndRateComponent
import com.example.currencyconverter.viewmodel.ConverterViewModel
import com.example.currencyconverter.viewmodel.ConverterViewModelFactory

private enum class EditingField {
    FROM, TO
}

@Composable
fun ConverterScreen(modifier: Modifier = Modifier) {

    val application = LocalContext.current.applicationContext as Application
    val factory = ConverterViewModelFactory(application)
    val viewModel: ConverterViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState

    if (uiState.errorMessage != null) {
        ErrorBanner(
            modifier = modifier,
            message = uiState.errorMessage ?: "Unknown error",
            onDismiss = { viewModel.clearError() }
        )
    }
    CurrencyConverterCard(viewModel = viewModel, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterCard(
    viewModel: ConverterViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState
    var showBottomSheet by remember { mutableStateOf(false) }
    var whichValueEdited by remember { mutableStateOf<EditingField?>(null) }

    LaunchedEffect(showBottomSheet) {
        if (!showBottomSheet) whichValueEdited = null
    }

    if (showBottomSheet) {
        CurrencyPickerBottomSheet(
            currenciesList = MockCurrencies.supportedCurrencies,
            onDismiss = { showBottomSheet = false },
            onCurrencySelected = { selectedCurrency ->
                when (whichValueEdited) {
                    EditingField.FROM -> viewModel.updateFromCurrency(selectedCurrency)
                    EditingField.TO -> viewModel.updateToCurrency(selectedCurrency)
                    null -> {}
                }
                showBottomSheet = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .shadow(6.dp, RoundedCornerShape(20.dp))
                .background(Color(0xFFF4F6FA), RoundedCornerShape(20.dp))
        ) {
            Column {
                CurrencyCard(
                    label = StringConstants.sendingFrom,
                    currency = uiState.fromCurrency,
                    amount = uiState.amount,
                    onCurrencyClick = {
                        whichValueEdited = EditingField.FROM
                        showBottomSheet = true
                    },
                    onAmountChange = { viewModel.updateAmount(it) },
                    isEditable = true,
                    hasError = uiState.smallErrorMessage != null
                )

                CurrencyCard(
                    label = StringConstants.receiverGets,
                    currency = uiState.toCurrency,
                    amount = uiState.convertedAmount,
                    onCurrencyClick = {
                        whichValueEdited = EditingField.TO
                        showBottomSheet = true
                    },
                    isEditable = false
                )
            }

            SwapAndRateComponent(
                fromCurrency = uiState.fromCurrency.currencyCode,
                toCurrency = uiState.toCurrency.currencyCode,
                rate = uiState.rate,
                onSwapClick = { viewModel.swapCurrencies() },
                modifier = Modifier.align(Alignment.Center)
            )
        }

        uiState.smallErrorMessage?.let {
            SmallErrorComponent(it)
        }
    }
}

