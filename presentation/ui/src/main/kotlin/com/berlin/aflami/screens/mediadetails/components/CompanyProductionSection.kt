package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState

@Composable
fun CompanyProductionSection(
    companyProductions: List<CompanyProductionUiState>,
    columns: Int = 2 // Set this as needed (2 or 3 typical)
) {
    val rows = (companyProductions.size + columns - 1) / columns
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < companyProductions.size) {
                        CompanyProductionItem(
                            item = companyProductions[index]
                        )
                    } else {
                        // Spacer to keep grid structure for incomplete last row
                        Spacer(Modifier.width(160.dp))
                    }
                }
            }
        }
    }
}