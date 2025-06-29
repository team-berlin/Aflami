package com.example.aflami.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.aflami.design_system.textstyle.LocalAflamiTextStyle
import com.example.aflami.design_system.textstyle.defaultTextStyle


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