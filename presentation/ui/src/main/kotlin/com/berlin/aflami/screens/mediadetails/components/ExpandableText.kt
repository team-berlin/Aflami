package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    maxPreviewLength: Int = 180,
    previewColor: Color,
    suffixColor: Color,
    previewStyle: TextStyle,
    suffixStyle: TextStyle,
) {
    val canExpand = text.length > maxPreviewLength

    val displayText =
        if (isExpanded || !canExpand) text else text.take(maxPreviewLength).trimEnd()

    val suffix = when {
        isExpanded && canExpand -> stringResource(com.berlin.ui.R.string.read_less)
        !isExpanded && canExpand -> stringResource(com.berlin.ui.R.string.read_more)
        else -> ""
    }

    val annotated = buildAnnotatedString {
        append(displayText)
        if (suffix.isNotEmpty()) {
            withStyle(
                SpanStyle(
                    color = suffixColor,
                    fontFamily = suffixStyle.fontFamily,
                    fontWeight = suffixStyle.fontWeight,
                    fontSize = suffixStyle.fontSize
                )
            ) {
                append(suffix)
            }
        }
    }

    Row(modifier = modifier) {
        Text(
            text = annotated,
            color = previewColor,
            style = previewStyle,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable(
                enabled = canExpand,
                onClick = onToggleExpand
            ),
            textAlign = TextAlign.Start
        )
    }
}