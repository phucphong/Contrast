package com.contrast.Contrast.presentation.features.main.store.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.searchBar.SearchBar

import com.contrast.Contrast.presentation.components.tab.TabBarRowBorder
import com.contrast.Contrast.presentation.theme.FFFCFCFC


@Composable
fun SearchAndFilterTabs(
    selectedTab: Int,
    tabTitles: List<String>,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(20.dp).background(Color.White)) {

        var searchText by remember { mutableStateOf("") }
        // Search bar (giả lập)
        SearchBar(searchText = searchText, onTextChange = { searchText = it })
        Spacer(modifier = Modifier.height(20.dp))

        // ✅ Gọi TabBarRow và truyền callback
        TabBarRowBorder(
            tabs = tabTitles,
            selectedTab = selectedTab,
            onTabSelected = onTabSelected
        )
    }
}
