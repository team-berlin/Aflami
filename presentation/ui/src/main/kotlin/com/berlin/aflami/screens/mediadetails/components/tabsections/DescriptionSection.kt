package com.berlin.aflami.screens.mediadetails.components.tabsections

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
    Spacer(Modifier.height(24.dp))
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(com.berlin.ui.R.string.description),
            color = Theme.color.textColors.title,
            style = Theme.textStyle.title.small,
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
