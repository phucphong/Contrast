package com.contrast.Contrast.presentation.components.tab
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
fun TabBarPagedGridScrollable(
    tabs: List<Category>,
    selectedTab: Int,
    type: String?,
    domain: String,
    onTabSelected: (Int) -> Unit
) {
    val isSinglePage = tabs.size <= 8
    val pages = if (isSinglePage) listOf(tabs) else tabs.chunked(8)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(pages.size) { pageIndex ->
            val pageItems = pages[pageIndex]

            // Tạo danh sách slot cố định 8 item (4 x 2), nếu thiếu thì thêm null
            val paddedItems = pageItems + List(8 - pageItems.size) { null }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.width(320.dp) // 4 cột x 80dp
            ) {
                for (rowIndex in 0 until 2) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (colIndex in 0 until 4) {
                            val slotIndex = rowIndex * 4 + colIndex
                            val item = paddedItems[slotIndex]
                            val globalIndex = pageIndex * 8 + slotIndex

                            if (item != null) {
                                val name = if (type == "name") item.name ?: "" else item.ten ?: ""
                                val fullUrl = domain.trimEnd('/') + (item.filetxt ?: "")
                                val isSelected = globalIndex == selectedTab

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .width(72.dp)
                                        .clickable { onTabSelected(globalIndex) }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(52.dp)
                                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                                            .border(
                                                width = 1.dp,
                                                color = Color.White,
                                                shape = RoundedCornerShape(12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        NetworkImage(
                                            model = fullUrl,
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
                            } else {
                                Spacer(modifier = Modifier.width(72.dp)) // slot trống để căn đều
                            }
                        }
                    }
                }
            }
        }
    }
}
