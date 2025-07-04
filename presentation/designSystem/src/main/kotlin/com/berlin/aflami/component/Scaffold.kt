package com.berlin.aflami.component

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.berlin.aflami.ui.theme.Theme
import androidx.compose.material3.Scaffold as MaterialScaffold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    containerColor: Color = Theme.color.surface,
    contentColor: Color = Color.Unspecified,
    content: @Composable () -> Unit,
) {
    MaterialScaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackBarHost,
        containerColor = containerColor,
        contentColor = contentColor,
        content = { content() },
    )
}