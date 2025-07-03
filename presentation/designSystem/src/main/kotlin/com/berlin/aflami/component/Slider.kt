package com.berlin.aflami.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun Slider(
    modifier: Modifier,
    moviesList: List<Movie>,
) {
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { moviesList.size })

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = 244.dp
    val itemHeight = 300.dp
    val contentPadding = (screenWidth - itemWidth) / 2
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(itemWidth),
        contentPadding = PaddingValues(horizontal = contentPadding),
        modifier = modifier
            .aspectRatio(itemHeight / itemWidth)

    ) { pageIndex ->

        val movie = moviesList[pageIndex]
        MovieCard(
            movie = movie,
            isCentered = pageIndex == pagerState.currentPage
        )

    }
}


@Composable
fun MovieCard(
    movie: Movie,
    isCentered: Boolean,
) {

    val cardWidth = animateDpAsState(
        targetValue = if (isCentered) 244.dp else 207.dp,
    ).value
    val cardHeight = animateDpAsState(
        targetValue = if (isCentered) 300.dp else 276.dp,
    ).value
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(movie.image.toInt()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight)
                .clip(RoundedCornerShape(24.dp))
        )
        if (isCentered) {
            RatingCard(
                modifier = Modifier.align(Alignment.TopEnd),
                rating = movie.rating,
            )
            PlayButton()
        }
    }
}

@Composable
fun RatingCard(
    modifier: Modifier,
    rating: String
) {
    Row(
        modifier = modifier
            .padding(top = 4.dp, end = 5.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 4.dp
                )
            )
            .background(
                Theme.color.primaryVariant
            )
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 4.dp
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
fun PlayButton() {
    Box(
        Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(
                color = Theme.color.textColors.onPrimary.copy(alpha = .87f)
            )

            .border(1.dp, color = Theme.color.stroke), contentAlignment = Alignment.Center

    ) {
        Icon(
            modifier = Modifier
                .size(40.dp),
            painter = painterResource(R.drawable.play_arrow),
            contentDescription = "Featured",
            tint = Theme.color.primary,
        )
    }
}

data class Movie(
    val id: String,
    val image: Int,
    val rating: String
)

@Composable
@Preview(
    showBackground = true,
)
fun SliderPreview() {
    AflamiTheme(
        isDarkTheme = true
    ) {
        Slider(
            modifier = Modifier,
            moviesList = moviesList,
        )
    }
}

val moviesList = listOf(
    Movie(
        id = "Test",
        image = R.drawable.movie_poster2,
        "9.9",
    ),
    Movie(
        id = "wow",
        image = R.drawable.movie_poster3,
        rating = "9.9"
    ),
    Movie(
        id = "Test",
        image = R.drawable.movie_poster2,
        rating = "7.4"
    ),
    Movie(
        id = "Test",
        image = R.drawable.movie_poster2,
        "9.9",
    ),
    Movie(
        id = "wow",
        image = R.drawable.movie_poster3,
        rating = "9.9"
    ),
    Movie(
        id = "Test",
        image = R.drawable.movie_poster2,
        rating = "7.4"
    ),
)