package com.contrast.Contrast.presentation.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itechpro.domain.model.Category


@Composable
fun TabBarRow(tabs: List<Category>,

              color: Color=Color.Red,textCorSelect: Color=Color.Black,
              selectedTab: Int,

              type :String?

              , onTabSelected: (Int) -> Unit) {
    val listState = rememberLazyListState()



    // Auto scroll
    LaunchedEffect(selectedTab) {
        val itemInfo = listState.layoutInfo.visibleItemsInfo.find { it.index == selectedTab }

        val viewportCenter = listState.layoutInfo.viewportEndOffset / 2
        val itemOffset = itemInfo?.offset ?: 0
        val itemSize = itemInfo?.size ?: 0
        val scrollOffset = itemOffset + itemSize / 2 - viewportCenter

        listState.animateScrollBy(scrollOffset.toFloat())
    }


    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        items(tabs.size) { index ->
            var textWidth by remember { mutableStateOf(0) }
            var name =""
            if(type=="name"){
                 name =tabs[index].name?:""
            }else{
                name =tabs[index].ten?:""
            }


                Column(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onTabSelected(index)
                    }
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = if (index == selectedTab) FontWeight.Bold else FontWeight.Normal,
                    color = if (index == selectedTab) textCorSelect else Color.Gray,
                    modifier = Modifier.onGloballyPositioned {
                        textWidth = it.size.width
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (index == selectedTab) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(with(LocalDensity.current) { textWidth.toDp() }) // ✅ chiều rộng bằng Text
                            .background(color)
                    )
                }
            }
        }
    }
}
