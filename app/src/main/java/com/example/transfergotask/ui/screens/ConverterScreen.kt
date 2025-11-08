package com.example.transfergotask.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transfergotask.MockCurrencies
import com.example.transfergotask.viewmodel.ConverterViewModel
import com.example.transfergotask.ui.screens.ErrorBanner

private enum class EditingField {
    FROM, TO
}

@Composable
fun ConverterScreen(modifier: Modifier = Modifier, viewModel: ConverterViewModel = viewModel()) {

    val from = viewModel.fromCurrency
    val to = viewModel.toCurrency
    val amount = viewModel.amount
    val converted = viewModel.convertedAmount
    val rate = viewModel.rate
    var showBanner by remember { mutableStateOf(true) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var whichValueEdited: EditingField? = remember { null }


    if (showBanner) {
        ErrorBanner(message = "cosik", onDismiss = { showBanner = false })
    }

    LaunchedEffect(true) {
        viewModel.fetchConversion()
    }



    CurrencyPickerBottomSheet(
        currenciesList = MockCurrencies.supportedCurrencies,
        show = showBottomSheet,
        onDismiss = { showBottomSheet = false },
        onCurrencySelected = { selectedCurrency ->
            when (whichValueEdited) {
                EditingField.FROM -> {
                    viewModel.updateFromCurrency(selectedCurrency)
                    whichValueEdited = null
                }

                EditingField.TO -> {
                    viewModel.updateToCurrency(selectedCurrency)
                    whichValueEdited = null
                }

                null -> {}
            }
        }
    )







    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Currency Converter", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = amount.toString(),
            onValueChange = { value ->
                value.toFloatOrNull()?.let { viewModel.updateAmount(it) }
            },
            label = { Text("Amount (${from.currencyCode})") }
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            whichValueEdited = EditingField.FROM
            showBottomSheet = true
        }) {
            Text("From: ${from.currencyName}")
        }


        Button(onClick = {
            whichValueEdited = EditingField.TO
            showBottomSheet = true
        }) {
            Text("To: ${to.currencyName}")
        }

        Text("Converted: $converted")
        Text("Rate: 1 ${from.currencyCode} = $rate ${to.currencyCode}")

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            showBanner = true
            viewModel.swapCurrencies()
        }) {
            Text("Swap")
        }
    }
}
