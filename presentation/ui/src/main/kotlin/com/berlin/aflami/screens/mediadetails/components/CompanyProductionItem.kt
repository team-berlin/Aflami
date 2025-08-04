package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.color.ExtraColors
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.designsystem.R
import com.berlin.safeimageviewer.SafeImageViewer

@Composable
fun CompanyProductionItem(
    modifier: Modifier = Modifier,
    item: CompanyProductionUiState
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(ExtraColors.white)
            .border(1.dp, Theme.color.stroke, RoundedCornerShape(12.dp))

    ) {
        AsyncImage(
            modifier = modifier,
            model = item.image?:"",
            contentDescription = stringResource(com.berlin.ui.R.string.company_production_image_cd),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.place_holder),
            fallback = painterResource(R.drawable.place_holder),
            placeholder = painterResource(R.drawable.place_holder),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .background(ExtraColors.overlayGradient)
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = item.name,
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.onPrimary,
                maxLines = 2,
                lineHeight = 24.sp,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.country,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.onPrimaryBody,
                maxLines = 1,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun CompanyProductionItemPreview() {
    AflamiTheme {
        CompanyProductionItem(
            item = CompanyProductionUiState(
                id = "1",
                image = "https://image.tmdb.org/t/p/w500/c9dVHPOL3cqCr2593Ahk0nEKTEM.png",
                name = "Universal",
                country = "US"
            )
        )
    }
}