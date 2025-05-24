package com.contrast.Contrast.presentation.components.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.category.Category

@Composable
fun ProfileOptionItem(category: Category, onProfileClick: (Category) -> Unit = {},) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProfileClick(category) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        category.icon?.takeIf { it != 0 }?.let { iconRes ->
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(TealGreen),
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))
        Text(category.ten?:"", fontSize = 13.sp, modifier = Modifier.weight(1f))



        Image(
            painter = painterResource(R.drawable.arrowright),
            contentDescription = null,
            colorFilter = ColorFilter.tint(TealGreen),
            modifier = Modifier.size(25.dp).padding(5.dp)
        )
    }
    Divider(color = Color(0xFFF0F0F0))
}