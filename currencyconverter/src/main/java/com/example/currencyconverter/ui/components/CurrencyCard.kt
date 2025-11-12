package com.example.currencyconverter.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.constants.StringConstants
import com.example.currencyconverter.data.model.CurrencyCountry
import com.example.currencyconverter.ui.theme.BrandBlue
import com.example.currencyconverter.ui.theme.CardLight
import com.example.currencyconverter.ui.theme.ErrorPink
import com.example.currencyconverter.ui.theme.TextGray


@Composable
fun CurrencyCard(
    label: String,
    currency: CurrencyCountry,
    amount: Float,
    onCurrencyClick: () -> Unit,
    onAmountChange: ((Float) -> Unit)? = null,
    isEditable: Boolean,
    hasError: Boolean = false
) {
    val backgroundColor = if (label == StringConstants.receiverGets) CardLight else Color.White
    val shape = if (label == StringConstants.receiverGets)
        RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
    else
        RoundedCornerShape(20.dp)

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = if (hasError && label == StringConstants.sendingFrom) BorderStroke(3.dp, Color.Red) else null,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium.copy(color = TextGray))
            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onCurrencyClick)
                ) {
                    Image(
                        painter = painterResource(currency.bigFlagRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        currency.currencyCode,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
                }

                if (isEditable) {
                    BasicTextField(
                        value = String.format("%.2f", amount),
                        onValueChange = { newValue ->
                            newValue.toFloatOrNull()?.let { onAmountChange?.invoke(it) }
                        },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            color = if (hasError) ErrorPink else BrandBlue,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End
                        )
                    )
                } else {
                    Text(
                        String.format("%.2f", amount),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }
        }
    }
}
