package com.berlin.aflami.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R


@Composable
fun NoInternetConnectionPlaceholder(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CountryTourExploring(
            image = painterResource(R.drawable.no_internet_placeholder),
            titleId = R.string.offline_title,
            messageId = R.string.check_connection_title
        )

        Button(
            onClick = { onClick() },
            modifier = modifier
                .width(90.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            enabled = enable,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                Theme.color.primaryVariant
            ),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.retry),
                style = Theme.textStyle.label.large,
                color = Theme.color.primary
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun NoInternetConnectionPlaceholderPreview() {
    NoInternetConnectionPlaceholder()
}