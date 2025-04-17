package com.contrast.Contrast.presentation.components.cart
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.category.viewmodel.CategoryViewModel
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.searchBar.SearchBar

import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFFCFCFC
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.enumApp.CategoryType
@Composable
fun CartIconWithBadge(
    count: Int,
    onCartClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(5.dp)
            .noRippleClickableComposable { onCartClick() } // 👈 dùng hàm extension của bạn
    ) {
        Image(
            painter = painterResource(id = R.drawable.cart),
            contentDescription = "Cart",
            modifier = Modifier
                .align(Alignment.Center)
                .size(30.dp).padding(5.dp),
            colorFilter = ColorFilter.tint(TealGreen)
        )

        if (count > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 1.dp, y = (-1).dp)
                    .size(16.dp)
                    .background(Color.Red, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
