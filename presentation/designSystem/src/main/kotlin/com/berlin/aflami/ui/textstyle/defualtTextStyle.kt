package com.berlin.aflami.ui.textstyle

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.berlin.designsystem.R


val loremIpsum = FontFamily(
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_semibold, weight = FontWeight.SemiBold)
)

val nicomoji = FontFamily(
    Font(R.font.nicomoji_regular, weight = FontWeight.Normal)
)


val LocalAflamiTextStyle = staticCompositionLocalOf { defaultTextStyle }


val defaultTextStyle: AflamiTextStyle = AflamiTextStyle(

    headline = SizedTextStyle(
        large = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 42.sp,

            ), medium = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 36.sp,
        ), small = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 30.sp,
        )
    ), title = SizedTextStyle(
        large = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 30.sp,
        ), medium = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 28.sp,
        ), small = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = loremIpsum,
            lineHeight = 24.sp,
        )
    ), body = SizedTextStyle(
        large = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = loremIpsum,
            lineHeight = 28.sp,
        ), medium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = loremIpsum,
            lineHeight = 24.sp,
        ), small = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = loremIpsum,
            lineHeight = 22.sp,
        )
    ), label = SizedTextStyle(
        large = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = loremIpsum,
            lineHeight = 24.sp,
        ), medium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = loremIpsum,
            lineHeight = 22.sp,
        ), small = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = loremIpsum,
            lineHeight = 16.sp,
        )
    )
)