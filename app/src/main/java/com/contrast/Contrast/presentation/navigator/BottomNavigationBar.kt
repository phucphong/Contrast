package com.contrast.Contrast.presentation.navigator


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.itechpro.domain.model.agency.BottomNavItem


@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Trang chủ", R.drawable.ic_home, true),
        BottomNavItem("Danh mục", R.drawable.ic_location, true),
        BottomNavItem("Video", R.drawable.ic_store, true),
        BottomNavItem("Tin tức", R.drawable.ic_membership, true),
        BottomNavItem("Tôi", R.drawable.contrast_box, true),
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (selectedIndex == index)
                                    Brush.verticalGradient(
                                        colors = listOf(Color(0xFFFFD6D6), Color.White)
                                    )
                                else Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Transparent)
                                ),
                                shape = RoundedCornerShape(0.dp)
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            if (selectedIndex == index) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(3.dp)
                                        .background(Color.Red)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Image(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(if (item.isCenter) 40.dp else 24.dp),
                                colorFilter = if (!item.isCenter) androidx.compose.ui.graphics.ColorFilter.tint(
                                    if (selectedIndex == index) Color.Red else Color.Gray
                                ) else null
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            if (item.hasLabel) {
                                Text(
                                    text = item.label,
                                    color = if (selectedIndex == index) Color.Red else Color.Gray,
                                    style = TextStyle(fontSize = 11.sp)
                                )
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                alwaysShowLabel = true
            )
        }
    }
}
