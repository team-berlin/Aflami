package com.berlin.aflami.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.utils.AsteriskVisualTransformation

@Composable
fun TextField(
    text: TextFieldValue,
    modifier: Modifier = Modifier,
    style: TextStyle = Theme.textStyle.body.medium.copy(color = Theme.color.textColors.hint),
    cursorBrush: Brush = SolidColor(Theme.color.textColors.hint),
    hintText: String = "",
    isEnabled: Boolean = true,
    isError: Boolean = false,
    maxLines: Int = 1,
    isObscured: Boolean = false,
    errorMessage: String = "",
    maxCharacters: Int = Int.MAX_VALUE,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    borderColor: Color = Theme.color.stroke,
    borderErrorColor: Color = Theme.color.statusColors.redAccent,
    borderFocusedColor: Color = Theme.color.primary,
    onTrailingIconClicked: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val canShowMaxCharacters = maxCharacters - text.text.length < 5

    val currentBorderColor by animateColorAsState(
        if (isError) borderErrorColor
        else if (isFocused) borderFocusedColor
        else borderColor
    )

    Column {
        AnimatedMessage(
            isError = isError,
            message = errorMessage,
            style = style,
        )
        Row(
            modifier = modifier
                .border(
                    width = 1.dp, color = currentBorderColor, shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .clipToBounds()
                .background(Theme.color.surfaceHigh)
                .defaultMinSize(minHeight = 56.dp)
                .then(
                    if (leadingIcon == null) Modifier.padding(start = 4.dp) else Modifier
                )
                .then(
                    if (trailingIcon == null) Modifier.padding(end = 4.dp) else Modifier
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                val imageColor by animateColorAsState(
                    targetValue = if (text.text.isEmpty()) Theme.color.textColors.body else Theme.color.textColors.hint
                )
                LeadingIcon(leadingIcon, imageColor)
                VerticalDivider()
            }
            BasicTextField(
                cursorBrush = cursorBrush,
                value = text,
                onValueChange = {
                    if (it.text.length <= maxCharacters) {
                        onValueChange(it)
                    } else if (it.text.length > text.text.length + 1) {
                        onValueChange(
                            it.copy(
                                text = it.text.substring(0, maxCharacters),
                            )
                        )
                    }


                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                maxLines = maxLines,
                enabled = isEnabled,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
                    .defaultMinSize(minHeight = 56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                textStyle = style,
                singleLine = maxLines == 1,
                visualTransformation = if (isObscured) AsteriskVisualTransformation() else VisualTransformation.None,
                decorationBox = { innerTextField ->
                    InnerTextFieldWithHint(innerTextField, text.text, hintText, style)
                })
            if (trailingIcon != null) {
                val imageColor by animateColorAsState(
                    targetValue = if (text.text.isEmpty()) Theme.color.textColors.hint else Theme.color.textColors.title
                )
                VerticalDivider()
                TrailingIcon(trailingIcon, imageColor, onTrailingIconClicked)
            }
        }
        AnimatedMaxCharacters(
            canShowMaxCharacters,
            "${text.text.length}/$maxCharacters",
            style,
        )
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
            .padding(start = 16.dp,)
            .size(24.dp)
    )
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .padding(start = 12.dp, top = 13.dp, bottom = 13.dp)
            .size(1.dp, 30.dp)
            .background(Theme.color.stroke)
    )
}

@Composable
private fun RowScope.InnerTextFieldWithHint(
    innerTextField: @Composable (() -> Unit), text: String, hintText: String, style: TextStyle
) {
    Box(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .padding(top = (if (LocalLayoutDirection.current == LayoutDirection.Rtl) 0 else 3).dp),
        contentAlignment = Alignment.CenterStart,
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
    message: String,
    style: TextStyle,
) {
    AnimatedVisibility(visible = isError) {
        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .wrapContentSize()
                .background(
                    color = Theme.color.statusColors.redAccent, shape = SpeechBubble(
                        cornerRadius = 8.dp,
                        tailWidth = 8.dp,
                        tailHeight = 4.dp,
                        tailOffsetDp = 8.dp
                    )
                )
                .padding(bottom = 4.dp)
        ) {
            Text(
                text = message,
                style = style,
                color = Theme.color.textColors.onPrimary,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
                    .animateContentSize()
            )
        }
    }
}


@Composable
private fun ColumnScope.AnimatedMaxCharacters(
    canShowMaxCharacters: Boolean,
    message: String,
    style: TextStyle,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.End
    ) {
        AnimatedVisibility(visible = canShowMaxCharacters) {
            Text(
                text = message,
                style = style,
                fontSize = 12.sp,
                color = Theme.color.textColors.title,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
                    .animateContentSize()
            )
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun TrailingIcon(leadingIcon: Int, imageColor: Color, onClick: (() -> Unit)? = null) {
    Crossfade(targetState = leadingIcon) { state ->
        Image(
            painter = painterResource(id = state),
            contentDescription = null,
            colorFilter = ColorFilter.tint(imageColor),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .then(
                    if (onClick != null)
                        Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = ripple(color = Theme.color.textColors.hint),
                            onClick = onClick
                        )
                    else
                        Modifier
                )
                .padding(vertical = 16.dp)
                .padding(start = 12.dp, end = 16.dp)
                .size(24.dp)
        )
    }
}
/*


@ThemeAndLocalePreviews
@Composable
private fun CustomTextFieldPreview() {
    AflamiTheme {
        Column(
            Modifier
                .background(Theme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                "",
                hintText = stringResource(R.string.label),
                leadingIcon = R.drawable.user,
                isEnabled = false
            )
            TextField(
                "",
                hintText = stringResource(R.string.label),
                leadingIcon = R.drawable.user,
                isError = true,
                errorMessage = stringResource(R.string.incorrect_password)
            )
            TextField(
                "This is a test title for field",
                hintText = stringResource(R.string.label),
                leadingIcon = R.drawable.user,
                maxCharacters = 32
            )
            TextField(
                "",
                hintText = stringResource(R.string.label),
                leadingIcon = R.drawable.user,
                isObscured = false
            )
            TextField(
                "",
                hintText = stringResource(R.string.label),
            )
            TextField(
                "",
                hintText = stringResource(R.string.label),
                trailingIcon = R.drawable.filter_vertical,
            )
        }
    }
}*/
