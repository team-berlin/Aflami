package com.example.aflami.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aflami.R
import com.example.aflami.designsystem.theme.AflamiTheme
import com.example.aflami.designsystem.theme.Theme

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Theme.textStyle.body.medium,
    hintText: String = "",
    maxLines: Int = 1,
    height: Dp = Dp.Unspecified,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = "",
    maxCharacters: Int = Int.MAX_VALUE,
    @DrawableRes leadingIcon: Int? = null,
    borderColor: Color = Theme.color.stroke,
    borderErrorColor: Color = Theme.color.statusColors.redAccent,
    borderFocusedColor: Color = Theme.color.primary,
    onValueChange: (String) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val canShowMaxCharacters = maxCharacters - text.length < 5

    val currentBorderColor by animateColorAsState(
        targetValue = if (isError) borderErrorColor else if (isFocused) borderFocusedColor else borderColor
    )

    val messageColor by animateColorAsState(
        targetValue = if (isError) Theme.color.textColors.onPrimary
        else if (canShowMaxCharacters) Theme.color.textColors.body
        else Theme.color.primary
    )

    val message = if (isError) errorMessage
    else if (canShowMaxCharacters) "${text.length}/$maxCharacters"
    else ""

    Column {
        AnimatedMessage(
            isError = isError,
            canShowMaxCharacters = canShowMaxCharacters,
            message = message,
            style = style,
            messageColor = messageColor
        )
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp, color = currentBorderColor, shape = RoundedCornerShape(16.dp)
                )
                .defaultMinSize(minHeight = 56.dp)
                .height(height)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.Top
        ) {
            if (leadingIcon != null) {
                val imageColor by animateColorAsState(
                    targetValue = if (text.isEmpty()) Theme.color.textColors.hint else Theme.color.textColors.body
                )
                LeadingIcon(leadingIcon, imageColor)
                VerticalDivider()
            }
            BasicTextField(
                value = text,
                onValueChange = {
                    if (it.length <= maxCharacters) onValueChange(it)
                    else if (it.length > text.length + 1) onValueChange(
                        it.substring(
                            0, maxCharacters
                        )
                    )
                },
                maxLines = maxLines,
                enabled = isEnabled,
                modifier = modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 56.dp)
                    .height(height)
                    .clip(RoundedCornerShape(16.dp))
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                textStyle = style.copy(color = Theme.color.textColors.body),
                singleLine = maxLines == 1,
                decorationBox = { innerTextField ->
                    InnerTextFieldWithHint(innerTextField, text, hintText, maxLines, style)
                })
        }

    }
}

@Composable
private fun LeadingIcon(leadingIcon: Int, imageColor: Color) {
    Image(
        imageVector = ImageVector.vectorResource(id = leadingIcon),
        contentDescription = null,
        colorFilter = ColorFilter.tint(imageColor),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .size(24.dp)
    )
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .padding(horizontal = 12.dp, vertical = 13.dp)
            .size(1.dp, 30.dp)
            .background(Theme.color.stroke)
    )
}

@Composable
private fun RowScope.InnerTextFieldWithHint(
    innerTextField: @Composable (() -> Unit),
    text: String,
    hintText: String,
    maxLines: Int,
    style: TextStyle
) {
    Box(
        modifier = if (maxLines == 1) Modifier else Modifier
            .padding(vertical = 5.dp)
            .padding(top = (if (LocalLayoutDirection.current == LayoutDirection.Rtl) 0 else 3).dp),
        contentAlignment = if (maxLines == 1) Alignment.CenterStart else Alignment.TopStart,
    ) {
        innerTextField()
        if (text.isEmpty()) {
            Text(
                text = hintText,
                style = style,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Theme.color.textColors.hint,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun ColumnScope.AnimatedMessage(
    isError: Boolean,
    canShowMaxCharacters: Boolean,
    message: String,
    style: TextStyle,
    messageColor: Color
) {
    AnimatedVisibility(
        visible = isError || canShowMaxCharacters
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 4.dp)
                .animateContentSize()
        ) {
            Image(
                painter = painterResource(R.drawable.container),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = message,
                style = style,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontStyle = Theme.textStyle.label.medium.fontStyle,
                color = messageColor,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(top = 8.dp)
                    .animateContentSize()
            )
        }
    }
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    AflamiTheme {
        Column(
            Modifier
                .background(Theme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                "",
                hintText = "Label",
                leadingIcon = R.drawable.user,
                maxLines = 1,
                isEnabled = false
            )
            CustomTextField(
                "",
                hintText = "Ali Ahmed Abdullah",
                leadingIcon = R.drawable.user,
                maxLines = 1,
                isError = true,
                errorMessage = "incorrect password"
            )
            CustomTextField(
                "",
                hintText = "Ali Ahmed Abdullah",
                leadingIcon = R.drawable.user,
                maxLines = 1,
                borderFocusedColor = Theme.color.primary,
            )
            CustomTextField(
                "Ali Ahmed Abdullah",
                hintText = "",
                leadingIcon = R.drawable.user,
                maxLines = 1,
                maxCharacters = 32
            )
        }
    }

}
