package com.example.currencyconverter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.constants.StringConstants
import com.example.currencyconverter.data.model.CurrencyCountry
import com.example.currencyconverter.ui.theme.CardLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerBottomSheet(
    currenciesList: List<CurrencyCountry>,
    onDismiss: () -> Unit,
    onCurrencySelected: (CurrencyCountry) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = if (searchQuery.isBlank()) {
        currenciesList
    } else {
        currenciesList.filter { currency ->
            currency.countryName.contains(searchQuery, ignoreCase = true) ||
                    currency.currencyCode.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                StringConstants.sendingTo,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search") },
                shape = RoundedCornerShape(8.dp),
            )
            Spacer(Modifier.height(16.dp))
            Text(StringConstants.allCountries, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(filteredList) { currency ->
                    CurrencyListItem(currency = currency, onClick = { onCurrencySelected(currency) })
                    HorizontalDivider(thickness = 1.dp, color = CardLight)
                }
            }
        }
    }
}

@Composable
private fun CurrencyListItem(
    currency: CurrencyCountry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(CardLight)
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = currency.bigFlagRes),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = currency.countryName,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 16.sp
            )
            Text(
                text = currency.currencyCode,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}
