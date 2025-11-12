package com.example.currencyconverter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex

@Composable
fun SwapAndRateComponent(
    fromCurrency: String,
    toCurrency: String,
    rate: Float,
    onSwapClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.zIndex(2f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        SwapIcon(onSwapClick)
        Spacer(modifier = Modifier.weight(1f))
        Rate(fromCurrency = fromCurrency, toCurrency = toCurrency, rate = rate)
        Spacer(modifier = Modifier.weight(2f))
    }
}
