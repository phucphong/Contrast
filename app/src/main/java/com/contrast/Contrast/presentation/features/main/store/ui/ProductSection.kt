package com.contrast.Contrast.presentation.features.main.store.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFFCFCFC

@Composable
fun ProductSection(title: String, products: List<Product>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).background(FFFCFCFC)) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = title, style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(600),
            color = Color(0xFFD91E18),

            ),

        )
        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Giới hạn chiều cao
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp), // ✅ hoặc Modifier.height(400.dp)
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(products) { product ->
                    ProductCard(
                        title = product.title,
                        price = product.price,
                        imageRes = product.imageRes,
                        discount = product.discount,
                        onAdd = { /* TODO */ }
                    )
                }
            }
        )
    }
}
