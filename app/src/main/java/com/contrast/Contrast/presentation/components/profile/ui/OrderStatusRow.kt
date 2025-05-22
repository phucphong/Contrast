package com.contrast.Contrast.presentation.components.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.itechpro.domain.model.category.Category

@Composable
fun OrderStatusRow(statusList: List<Category>, onProfileClick: (Category) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        statusList.forEach { status ->
            status.icon?.let {
                OrderStatusItem(
                    icon = it,
                    title = status.ten?:"",
                    onClick ={
                        onProfileClick(status)
                    }
                )
            }
        }
    }
}
