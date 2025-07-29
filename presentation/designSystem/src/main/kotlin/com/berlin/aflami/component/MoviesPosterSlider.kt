package com.berlin.aflami.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.color.ExtraColors.black50
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R
import com.berlin.safeimageviewer.SafeImageViewer


data class MovieCardUiState(
    val id: String, val posterImage: Int, val rating: String
)

//@Composable
//fun MoviesPosterSlider(
//    modifier: Modifier = Modifier,
//    poster: String,
//    rating: String,
//    onClick: () -> Unit = {},
//    pagerState: PagerState,
//) {
//
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//    val itemWidth = 244.dp
//    val contentPadding = (screenWidth - itemWidth) / 2
//    HorizontalPager(
//        state = pagerState,
//        pageSize = PageSize.Fixed(itemWidth),
//        contentPadding = PaddingValues(horizontal = contentPadding),
//        modifier = modifier.fillMaxWidth()
//    ) { pageIndex ->
//        MovieCard(
//            isCentered = pageIndex == pagerState.currentPage,
//            onClick = onClick,
//            rating = rating,
//            posterImageUrl = poster
//        )
//    }
//}

//@Composable
//fun MovieCard(
//    isCentered: Boolean,
//    onClick: () -> Unit,
//    rating: String,
//    posterImageUrl: String,
//) {
//
////    val cardWidth = animateDpAsState(
////        targetValue = if (isCentered) 244.dp else 207.dp,
////    ).value
////    val cardHeight = animateDpAsState(
////        targetValue = if (isCentered) 300.dp else 276.dp,
////    ).value
//
//    val cardWidth = 244.dp
//    val cardHeight = 300.dp
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
////        AsyncImage(
////            model = posterImageUrl,
////            contentDescription = null,
////            contentScale = ContentScale.Crop,
////            modifier = Modifier
////                .width(cardWidth)
////                .height(cardHeight)
////                .clip(RoundedCornerShape(24.dp))
////        )
//        SafeImageViewer(
//            imageUri = posterImageUrl,
//            modifier = Modifier
//                .width(cardWidth)
//                .height(cardHeight)
//                .clip(RoundedCornerShape(24.dp))
//        )
//        if (isCentered) {
//            RatingCard(
//                modifier = Modifier.align(Alignment.TopEnd),
//                rating = rating,
//            )
//            PlayButton(
//                onClick = { })
//        }
//    }
//}

@Composable
fun RatingCard(
    modifier: Modifier, rating: String
) {
    Row(
        modifier = modifier
            .padding(top = 4.dp, end = 5.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 4.dp
                )
            )
            .background(
                Theme.color.primaryVariant
            )
            .border(
                width = 1.dp, color = Theme.color.stroke, shape = RoundedCornerShape(
                    topStart = 4.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 4.dp
                )
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(R.drawable.ic_rating),
            tint = Theme.color.statusColors.yellowAccent,
            contentDescription = "rating"
        )
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = rating,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.body
        )
    }
}

@Composable
fun PlayButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(
                color = Theme.color.textColors.onPrimary.copy(alpha = .87f)
            )
            .border(1.dp, color = Theme.color.stroke)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(R.drawable.play_arrow),
            contentDescription = "Featured",
            tint = Theme.color.primary,
        )
    }
}

@Composable
fun BlurredPosterBackground(
    imageUrl: String, modifier: Modifier = Modifier
) {
    SafeImageViewer(
        imageUri = imageUrl,
        contentDescription = "Blurred Poster Background",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .background(black50),
        blurCheck = false
    )
}
