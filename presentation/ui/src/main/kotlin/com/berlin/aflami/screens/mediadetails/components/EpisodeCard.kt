
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.berlin.aflami.component.CircularIConButton
import com.berlin.aflami.component.RatingCard
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

data class EpisodeUi(
    val id: Int,
    val episodeNumber: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val time: String,
    val date: String,
    val rating: String,
)

@Composable
fun EpisodeCard(
    episode: EpisodeUi,
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageWithRatingBadge(
                modifier = Modifier,
                imageUrl = episode.imageUrl,
                rating = episode.rating
            )

            EpisodeDetails(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                episodeNumber = episode.episodeNumber,
                title = episode.title,
                time = episode.time,
                date = episode.date
            )

            CircularIConButton(
                painter = painterResource(R.drawable.play),
                onClick = onClickPlay
            )
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = episode.description,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ImageWithRatingBadge(
    modifier: Modifier = Modifier,
    imageUrl: String,
    rating: String
) {
    Box(
        modifier = modifier
            .size(width = 116.dp, height = 78.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(imageUrl)
        val state by painter.state.collectAsState()

        when (state) {
            is AsyncImagePainter.State.Success -> {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
            }

            else -> {
                Image(
                    painter = painterResource(com.berlin.ui.R.drawable.place_holder),
                    contentDescription = stringResource(com.berlin.ui.R.string.episode_image)
                )
            }
        }

        RatingCard(
            modifier = Modifier.align(Alignment.TopEnd),
            rating = rating
        )
    }
}

@Composable
private fun EpisodeDetails(
    episodeNumber: Int,
    title: String,
    time: String,
    date: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "${stringResource(com.berlin.ui.R.string.episode)} $episodeNumber",
            style = Theme.textStyle.label.large,
            color = Theme.color.textColors.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = title,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Theme.color.stroke)
            )

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = date,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint
            )
        }
    }
}

@Composable
@Preview()
private fun EpisodeDetailsCardPreview() {
    AflamiTheme(isDarkTheme = true) {
        EpisodeCard(episode = previewEpisode)
    }
}

private val previewEpisode = EpisodeUi(
    id = 0,
    episodeNumber = 1,
    title = "Recovering a body",
    description = "In 1935, corrections officer Paul Edge comb oversees \"The Green Mile, the death row",
    imageUrl = "",
    time = "58 m",
    date = "3 Sep 2020",
    rating = "8.2",
)