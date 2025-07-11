package com.berlin.aflami.screens.search.worldtour.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourInteractionListener
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourUiState
import com.berlin.ui.R

@Composable
fun AnimatedCountriesList(
    state: WorldTourUiState,
    listener: WorldTourInteractionListener
) {
    Column {
        AnimatedVisibility(
            visible = state.dropDownExpanded
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
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                itemsIndexed(
                    items = state.filteredCountries.toList(),
                    key = { _, countryWithCode -> countryWithCode.second }
                ) { index, countryWithCode ->
                    CountryItem(
                        countryWithCode = countryWithCode,
                        onCountryClick = {
                            listener.onCountryNameChanged(it)
                            listener.onSearchClick()
                        }
                    )

                    if (index != state.filteredCountries.size - 1) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(Theme.color.stroke)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryItem(
    modifier: Modifier = Modifier,
    countryWithCode: Pair<String, String>,
    onCountryClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCountryClick(countryWithCode.first) }
            .padding(start = 16.dp)
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 16.dp)
    ) {
        Text(
            text = countryWithCode.first,
            style = Theme.textStyle.body.medium,
            color = Theme.color.secondary
        )
    }
}