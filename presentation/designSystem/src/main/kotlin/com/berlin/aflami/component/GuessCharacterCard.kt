package com.berlin.aflami.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R
import kotlin.math.max

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    imageRes: Int? = null,
    imageUrl: String? = null,
    blurAmount: Float,
    onHintClicked: () -> Unit,
    showHintBar: Boolean = true,
    hintText: String = "hint? 10 Pts.",
    imageHeight: Dp = 180.dp,
    imageWidth: Dp? = null,
    hintSize: TextUnit = Theme.textStyle.label.small.fontSize,
    hintIcon: Int? = null,
    hintTextColor: Color = Theme.color.statusColors.yellowAccent,
    backgroundColor: Color = Theme.color.surface,
    cardElevation: CardElevation = CardDefaults.cardElevation(0.dp),
    cornerRadius: Dp = 24.dp
){
    val clampedTextSize = hintSize.value.coerceIn(10f, 24f).sp
    val imageCornerRadius = if(showHintBar) RoundedCornerShape(topEnd = cornerRadius,
        topStart = cornerRadius) else RoundedCornerShape(cornerRadius)
    val clampedImageHeight = imageHeight.coerceIn(120.dp, 320.dp)

    Card(
        modifier = modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(cornerRadius),
        elevation = cardElevation,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .height(clampedImageHeight)
                    .then(if(imageWidth != null) Modifier.width(imageWidth)
                    else Modifier.fillMaxWidth())
                    .blur((blurAmount * 0.2f).dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Character Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(imageCornerRadius)
                            .blur(blurAmount.dp)
                    )
                } else if (imageRes != null){
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Character Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(imageCornerRadius)
                            .blur(blurAmount.dp)
                    )
            }
            }

            if (showHintBar){
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .then(if(imageWidth != null) Modifier.width(imageWidth)
                        else Modifier.fillMaxWidth())
                        .clickable { onHintClicked() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        Text(
                            maxLines = 1,
                            text = hintText,
                            color = hintTextColor,
                            textAlign = TextAlign.Center,
                            style = Theme.textStyle.label.small.copy(fontSize = clampedTextSize),
                        )
                        if (hintIcon != null) {
                            Icon(
                                modifier = Modifier.size(clampedTextSize.value.dp),
                                painter = painterResource(id = hintIcon),
                                contentDescription = "Hint Icon",
                                tint = hintTextColor,
                            )
                        }
                    }
                }
        }
        }
    }
}



@ThemeAndLocalePreviews
@Composable
fun CharacterCardPreview() {
    AflamiTheme {
        var blur by remember { mutableFloatStateOf(20f) }

        CharacterCard(
            imageRes = R.drawable.guess_char_img,
            blurAmount = blur,
            hintIcon = R.drawable.hint_star,
            onHintClicked = {
                blur = max(0f, blur - 5f)
            }
        )
    }
}