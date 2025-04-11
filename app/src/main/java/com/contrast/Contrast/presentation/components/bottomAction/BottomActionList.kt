package com.contrast.Contrast.presentation.components.bottomAction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import com.itechpro.domain.model.BottomActionItem

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp


@Composable
fun BottomActionList(
    actions: List<BottomActionItem>,
    onItemClick: (BottomActionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        actions.forEach { action ->
            BottomAction(
                icon = action.iconRes,
                label = action.label,
                onClick = { onItemClick(action) }
            )
        }
    }
}
