package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState

@Composable
fun CompanyProductionSection(
    modifier: Modifier = Modifier,
    companyProductions: List<CompanyProductionUiState>,
    cellWidth: Dp = 160.dp,
    cellHeight: Dp = 145.dp,
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 8.dp,
    sidePadding: Dp = 16.dp
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val maxGridWidth = maxWidth - 2 * sidePadding
        val columns = (maxGridWidth / (cellWidth + horizontalSpacing)).toInt().coerceAtLeast(2)

        val rows = (companyProductions.size + columns - 1) / columns

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = sidePadding, end = sidePadding, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        ) {
            for (row in 0 until rows) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
                ) {
                    for (col in 0 until columns) {
                        val index = row * columns + col
                        if (index < companyProductions.size) {
                            CompanyProductionItem(
                                modifier = Modifier
                                    .width(cellWidth)
                                    .height(cellHeight),
                                item = companyProductions[index]
                            )
                        } else {
                            Spacer(
                                Modifier
                                    .width(cellWidth)
                                    .height(cellHeight)
                            )
                        }
                    }
                }
            }
        }
    }
}