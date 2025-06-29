package com.example.aflami.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.aflami.designsystem.textstyle.LocalAflamiTextStyle
import com.example.aflami.designsystem.textstyle.defaultTextStyle


@Composable
fun AflamiTheme(

    content: @Composable () -> Unit

) {
    CompositionLocalProvider(
        LocalAflamiTextStyle provides defaultTextStyle
    ) {
        content()
    }


}