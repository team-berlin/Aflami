package com.berlin.aflami.screens.search.worldtour.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun AnimatedCountriesList(
    modifier: Modifier = Modifier,
    visible: Boolean,
    filteredCountries: Map<String, String>,
    onCountryNameChanged: (String) -> Unit,
    onCountryClick: () -> Unit,
) {
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = visible
        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = 16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .imePadding()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Theme.color.surfaceHigh)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.countries),
                        style = Theme.textStyle.label.medium,
                        color = Theme.color.textColors.title,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                itemsIndexed(
                    items = filteredCountries.toList(),
                    key = { _, countryWithCode -> countryWithCode.second }
                ) { index, countryWithCode ->
                    CountryItem(
                        countryWithCode = countryWithCode,
                        onCountryClick = {
                            onCountryNameChanged(it)
                            onCountryClick()
                        }
                    )

                    if (index != filteredCountries.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Theme.color.stroke
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryItem(
    countryWithCode: Pair<String, String>,
    onCountryClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCountryClick(countryWithCode.first) }
            .padding(start = 16.dp)
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 16.dp)
    ) {
        Text(
            text = countryWithCode.first,
            style = Theme.textStyle.body.medium,
            color = Theme.color.textColors.body
        )
    }
}