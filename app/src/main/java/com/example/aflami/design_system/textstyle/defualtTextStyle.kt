package com.example.aflami.design_system.textstyle

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aflami.R


val loremIpsum = FontFamily(
    Font(R.font.loremipsum, weight = FontWeight.Normal),
    Font(R.font.loremipsum, weight = FontWeight.Medium),
    Font(R.font.loremipsum, weight = FontWeight.SemiBold)
)


val LocalAflamiTextStyle = staticCompositionLocalOf { defaultTextStyle }


val defaultTextStyle: AflamiTextStyle = AflamiTextStyle(

    headline = SizedTextStyle(
        large = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 42.sp,

            ),
        medium = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 36.sp,
        ),
        small = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 30.sp,
        )
    ),
    title = SizedTextStyle(
        large = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 30.sp,
        ),
        medium = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 28.sp,
        ),
        small = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 24.sp,
        )
    ),
    body = SizedTextStyle(
        large = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = loremIpsum,
            lineHeight = 28.sp,
        ),
        medium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = loremIpsum,
            lineHeight = 24.sp,
        ),
        small = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = loremIpsum,
            lineHeight = 22.sp,
        )
    ),
    label = SizedTextStyle(
        large = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = loremIpsum,
            lineHeight = 24.sp,
        ),
        medium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = loremIpsum,
            lineHeight = 22.sp,
        ),
        small = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = loremIpsum,
            lineHeight = 16.sp,
        )
    )
)