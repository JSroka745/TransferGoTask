package com.example.currencyconverter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.BrandBlue

@Composable
fun SwapIcon(onSwapClick: () -> Unit){
    Icon(
        painter = painterResource(id = R.drawable.ic_swap),
        contentDescription = "Swap currencies",
        tint = Color.White,
        modifier = Modifier
            .size(30.dp)
            .background(BrandBlue, shape = CircleShape)
            .padding(3.dp)
            .clickable { onSwapClick() },
    )
}