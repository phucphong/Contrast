package com.contrast.Contrast.presentation.features.main.store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.components.tab.TabBarRowBorder
@Composable
fun SearchAndFilterTabs(
    selectedTab: Int,
    tabTitles: List<String>,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Search bar (giả lập)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF2F2F2)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Tìm kiếm sản phẩm...",
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ✅ Gọi TabBarRow và truyền callback
        TabBarRowBorder(
            tabs = tabTitles,
            selectedTab = selectedTab,
            onTabSelected = onTabSelected
        )
    }
}
