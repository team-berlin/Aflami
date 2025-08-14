package com.berlin.aflami.screens.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

@Composable
fun Score(
    score: String,
    scoreColor: Color,
    backgroundColor: Color
) {
    var scoreAmount by remember { mutableStateOf("") }
    if (scoreColor == Theme.color.statusColors.greenAccent) {
        scoreAmount = "+$score"
    } else {
        scoreAmount = "-$score"
    }

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = scoreAmount,
            style = Theme.textStyle.headline.small,
            color = scoreColor
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun BounsScorePreview() {
    Score(
        score = "10",
        scoreColor = Theme.color.statusColors.greenAccent,
        backgroundColor = Theme.color.statusColors.greenVariant

    )
}

@Preview(showBackground = true)
@Composable
private fun MinusScorePreview() {
    Score(
        score = "10",
        scoreColor = Theme.color.statusColors.redAccent,
        backgroundColor = Theme.color.statusColors.redVariant

    )
}