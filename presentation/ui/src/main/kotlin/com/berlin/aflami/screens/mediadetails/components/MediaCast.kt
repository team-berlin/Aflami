package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun MediaCast(
    modifier: Modifier = Modifier,
    name:String,
    poster:String
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.cast),
            style = Theme.textStyle.headline.small,
            color = Theme.color.textColors.title
        )
        Text(
            text = stringResource(R.string.all),
            style = Theme.textStyle.label.medium,
            color = Theme.color.primary
        )
    }
    Column (
        modifier=modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        AsyncImage(
            model =poster,
            contentDescription = stringResource(R.string.cast_image),
            modifier = Modifier
                .size(78.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, color = Theme.color.stroke, RoundedCornerShape(16.dp))

        )
        Text(
            text=name,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.body
        )
    }

}


@ThemeAndLocalePreviews
@Composable
private fun MediaCastPreview(){
    MediaCast(
        name = "Tom Hanks",
        poster ="https://i.pinimg.com/736x/2d/4c/77/2d4c7718ccdf3d714654dbd3d66da00f.jpg",

        )
}