package com.berlin.aflami.screens.search.mediadetails

import android.graphics.fonts.FontStyle
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.Rating
import com.berlin.aflami.screens.search.components.ExpandableText
import com.berlin.aflami.ui.textstyle.IBM
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R
import java.time.format.TextStyle

@Composable
fun ReviewSection(
    modifier: Modifier = Modifier,
    avatarImage: Painter,
    name: String,
    userName: String,
    rating: String,
    content: String,
    date: String,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
    ) {
        items(7) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Image(
                    painter = avatarImage,
                    contentDescription = "Avatar Image",
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            BorderStroke(1.dp, Theme.color.stroke),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = name,
                        color = Theme.color.textColors.title,
                        style = Theme.textStyle.title.medium
                    )
                    Text(
                        text = "@$userName",
                        color = Theme.color.textColors.hint,
                        style = Theme.textStyle.label.small
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Rating(
                    modifier = Modifier
                        .offset(y = -(4).dp, x = 4.dp),
                    rating
                )
            }

//            ExpandableText(
//                text = content,
//                modifier = Modifier
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 12.dp),
//            )

            Text(
                date,
                color = Theme.color.textColors.hint,
                style = Theme.textStyle.label.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Theme.color.stroke
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewSectionPreview() {
    AflamiTheme(isDarkTheme = false) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No Reviews Found",
                color = Theme.color.textColors.title,
                style = Theme.textStyle.title.medium,
                textAlign = TextAlign.Center
            )
        }
    }
}