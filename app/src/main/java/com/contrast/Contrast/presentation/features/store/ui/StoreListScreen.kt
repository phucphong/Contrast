package com.contrast.Contrast.presentation.features.store.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.main.home.ui.StoreCard
data class Product(
    var title: String,
    val price: String,
    val imageRes: Int,
    val discount: String? = null
)
@Preview(showBackground = true)
@Composable
fun StoreListScreen(navController: NavController? = null) {
    val teaProducts = listOf(
        Product("Nhẫn Hoa Mộc Tê", "55.000 đ", R.drawable.product_image, "50% OFF"),
        Product("Nhẫn Hoa Mộc Tê", "55.000 đ", R.drawable.product_image, "50% OFF")
    )

    val coffeeProducts = listOf(
        Product("Nhẫn Hoa Mộc Tê", "55.000 đ", R.drawable.product_image),
        Product("Nhẫn Hoa Mộc Tê", "55.000 đ", R.drawable.product_image),
        Product("Nhẫn Hoa Mộc Tê", "55.000 đ", R.drawable.product_image)
    )



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

    ) {
        item {
            HeaderSection("Cơ sở Khâm Thiên")
            var selectedTab by remember { mutableStateOf(0) }
            val tabTitles = listOf("CONTRAST", "MUNCHIES", "MERCH", "VPP")
            SearchAndFilterTabs(
                selectedTab = selectedTab,
                tabTitles =tabTitles,
                onTabSelected = { selectedTab = it }
            )
        }

        item {
            ProductSection(title = "TRÀ", products = teaProducts)
        }

        item {
            ProductSection(title = "CÀ PHÊ", products = coffeeProducts)
        }
    }




}





