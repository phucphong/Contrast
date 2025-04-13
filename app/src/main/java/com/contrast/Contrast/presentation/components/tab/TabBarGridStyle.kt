package com.contrast.Contrast.presentation.components.tab
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.extensions.splitTextToTwoLines
import com.contrast.Contrast.presentation.components.image.NetworkImage

import com.itechpro.domain.model.Category

@Composable
fun TabBarGridStyle(
    tabs: List<Category>,
    color: Color = Color.Black,
    textCorSelect: Color = Color.White,
    selectedTab: Int,
    type: String?,
    domain: String,
    onTabSelected: (Int) -> Unit
) {
    val chunkedTabs = tabs.chunked(3) // 3 item mỗi hàng

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        chunkedTabs.forEachIndexed { rowIndex, rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEachIndexed { index, tab ->
                    val realIndex = rowIndex * 3 + index
                    val name = if (type == "name") tab.name ?: "" else tab.ten ?: ""
                    val fullUrl = domain.trimEnd('/') + (tab.filetxt ?: "")
                    val isSelected = realIndex == selectedTab

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(80.dp)
                            .clickable { onTabSelected(realIndex) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(Color.White, shape = CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color.Black else Color.Transparent,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            NetworkImage(
                                imageUrl = fullUrl,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = splitTextToTwoLines(name),
                            fontSize = 11.sp,
                            color = if (isSelected) Color.Black else Color.Gray,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            maxLines = 2,
                            minLines = 2
                        )
                    }
                }

                // Nếu chưa đủ 3 item thì thêm box trống
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.width(80.dp))
                }
            }
        }
    }
}
