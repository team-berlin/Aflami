package com.berlin.aflami.screens.mediadetails.components.screensections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.mediadetails.components.ExpandableText
import com.berlin.aflami.ui.theme.Theme

@Composable
fun DescriptionSection(
    overview: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            text = stringResource(com.berlin.ui.R.string.description),
            color = Theme.color.textColors.title,
            style = Theme.textStyle.title.small,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ExpandableText(
            text = overview,
            isExpanded = isExpanded,
            onToggleExpand = onToggleExpand,
            previewColor = Theme.color.textColors.hint,
            suffixColor = Theme.color.primary,
            previewStyle = Theme.textStyle.body.small,
            suffixStyle = Theme.textStyle.label.medium
        )
    }
}
