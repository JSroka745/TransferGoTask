package com.example.currencyconverter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Rate(
    fromCurrency: String,
    toCurrency: String,
    rate: Float,
) {
    Text(
        text = "1 $fromCurrency = $rate $toCurrency",
        color = Color.White,
        fontSize = 12.sp,
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(50))
            .padding(vertical = 2.dp, horizontal = 8.dp)
    )
}