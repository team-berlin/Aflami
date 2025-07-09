package screens

import MediaUiState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SearchByActorScreen(padding: PaddingValues) {

    SearchByActorScreenContent(padding)

}

@Composable
fun SearchByActorScreenContent(padding: PaddingValues) {
    Box(
        Modifier
            .padding(padding)
            .fillMaxSize()
            .background(Theme.color.surface)

    ) {
        Column(

        ) {
            TopBar(
                modifier = Modifier.padding(vertical = 8.dp),
                title = {
                    Text(
                        text = stringResource(R.string.search_by_actor),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title
                    )
                }, leadingIcon = {
                    Box(
                        Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Theme.color.surfaceHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = stringResource(R.string.icon_cd)
                        )
                    }
                }
            )
            Box(
                Modifier.padding(horizontal = 16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "",
                    hintText = stringResource(R.string.actor_name_hint),
                )
            }

            MoviesList(dummyListOfShows)

        }

//        FindByActorCard(
//            modifier = Modifier.align(Alignment.Center)
//        )

    }

}

@Composable
fun FindByActorCard(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier.size(width = 76.dp, height = 80.dp),
            painter = painterResource(R.drawable.find_by_actor),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.find_by_actor),
            style = Theme.textStyle.title.medium,
            color = Theme.color.textColors.title
        )
        Text(
            text = stringResource(R.string.find_by_actor_quotation),
            style = Theme.textStyle.title.small,
            color = Theme.color.textColors.body,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MoviesList(
    listOfShows: List<MediaUiState>
) {
    LazyVerticalGrid(
        GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(listOfShows) { show ->
            MediaCard(
                modifier = Modifier.size(width = 160.dp, height = 222.dp),
                mediaImg = show.poster,
                title = show.title,
                typeOfMedia = show.mediaType,
                date = show.releaseYear,
                rating = show.rating.toDouble()
            )
        }
    }
}

val dummyListOfShows = listOf(
    MediaUiState(
        id = 1L,
        title = "Your Name",
        mediaType = "TV show",
        rating = "9.9",
        releaseYear = "2016",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg",
    ),
    MediaUiState(
        1L,
        "Your Name",
        "TV Show",
        "9.9",
        "2016",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        2L,
        "Breaking Bad",
        "TV Show",
        "9.5",
        "2008",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        3L,
        "Interstellar",
        "Movie",
        "8.6",
        "2014",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        4L,
        "The Office",
        "TV Show",
        "8.9",
        "2005",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        5L,
        "Inception",
        "Movie",
        "8.8",
        "2010",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        6L,
        "Stranger Things",
        "TV Show",
        "8.7",
        "2016",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        7L,
        "Attack on Titan",
        "TV Show",
        "9.1",
        "2013",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        8L,
        "The Matrix",
        "Movie",
        "8.7",
        "1999",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        9L,
        "The Crown",
        "TV Show",
        "8.6",
        "2016",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        10L,
        "Parasite",
        "Movie",
        "8.6",
        "2019",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        11L,
        "The Mandalorian",
        "TV Show",
        "8.8",
        "2019",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        12L,
        "Spirited Away",
        "Movie",
        "8.6",
        "2001",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        13L,
        "Dark",
        "TV Show",
        "8.8",
        "2017",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        14L,
        "Friends",
        "TV Show",
        "8.9",
        "1994",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        15L,
        "The Batman",
        "Movie",
        "8.0",
        "2022",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        16L,
        "Narcos",
        "TV Show",
        "8.8",
        "2015",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        17L,
        "The Boys",
        "TV Show",
        "8.7",
        "2019",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        18L,
        "Joker",
        "Movie",
        "8.4",
        "2019",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        19L,
        "Sherlock",
        "TV Show",
        "9.1",
        "2010",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    ),
    MediaUiState(
        20L,
        "Whiplash",
        "Movie",
        "8.5",
        "2014",
        poster = "https://media.themoviedb.org/t/p/w220_and_h330_face/ombsmhYUqR4qqOLOxAyr5V8hbyv.jpg"
    )
)


@ThemeAndLocalePreviews
@Composable
fun SearchByActorScreenContentPreview() {
    SearchByActorScreen(PaddingValues())
}
