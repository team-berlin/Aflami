package com.berlin.aflami.ui.color

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object ExtraColors {
    val darkReddishPink12 = Color(0x1FBF434C)
    val darkReddishGreen12 = Color(0x1F429946)
    val white = Color(0xFFFFFFFF)

    val blueLinearGradient = Brush.linearGradient(
        listOf(
            Color(0xFF53ABF9),
            Color(0xFF336490),
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY),
    )

    val darkPurpleLinearGradient = Brush.linearGradient(
        listOf(
            Color(0xFFD85895),
            Color(0xFF803559)
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY),
    )

    val overlayGradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0xFF0D090B)
        )
    )

    val black50 = Color(0x80000000)

    val primaryGredient=Brush.linearGradient(
        listOf(
            Color(0xFFF564A9),
            Color(0xFF973A66)
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY)

    )
    val BackgroundGradient=Brush.linearGradient(
        listOf(
            Color(0xCC63163A),
            Color(0x0063163A),
        ),
        end = Offset(0f, Float.POSITIVE_INFINITY)

    )

    val onBoardingLinearGradientTopToDown=Brush.verticalGradient(
        listOf(
            Color(0x000D0608),
            Color(0xFF0D0608)
        ),

    )
    val onBoardingLinearGradientDownToTop=Brush.verticalGradient(
        listOf(
            Color(0xFF0D0608),
            Color(0x000D0608),
        ),

    )
    val guessMovieByCharacterGradient = Brush.linearGradient(
        listOf(Color(0x80D85895),
            Color(0x05FFFFFF)
        )
    )

    val guessMovieByPosterGradient =
        Brush.linearGradient(
            listOf(Color(0x802BA3D9), Color(0x05FFFFFF))
        )
    val guessMovieByReleaseGradient =
        Brush.linearGradient(
            listOf(Color(0x800A203A), Color(0x05FFFFFF))
        )
    val guessMovieByGenreGradient =
        Brush.linearGradient(
            listOf(Color(0x80E5A02E), Color(0x05FFFFFF))
        )
    val borderActionGameGard =
        Brush.linearGradient(
            listOf(
                Color(0x14FFFFFF),
                Color(0x3DFFFFFF)
            )
        )
    val shadowGuessMovieByCharacter=Color(0x1FD85895)
    val shadowGuessMovieByByPoster=Color(0x1F2BA3D9)

    val shadowGuessMovieRelease=Color( 0x800A203A)

    val shadowGuessMovieByGenre=Color(0x1EE5A02E)

    val diagonalStripe = Color(0x00EFF9FE)

    val gameBackgroundGradient=Brush.verticalGradient(
        listOf(Color(0x3DD95997),Color(0x00D85895)),

        )
    val moodPickerGradient=Brush.verticalGradient(
        listOf(Color(0x14FFFFFF),Color(0x3DFFFFFF)),
    )


}