package com.contrast.Contrast.presentation.features.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.TealGreen

@Composable
fun CartHeader( onBackPress: () -> Unit,onBackHome: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth().background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(painter = painterResource(R.drawable.quaylai),
            contentDescription = null,
            colorFilter = ColorFilter.tint(TealGreen),
            modifier = Modifier.size(30.dp).padding(5.dp).noRippleClickableComposable { onBackPress() })
        Text(
            text = stringResource(R.string.cart_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )




        Image(painter = painterResource(R.drawable.home),
            contentDescription = null,
            colorFilter = ColorFilter.tint(TealGreen),
            modifier = Modifier.size(30.dp).padding(5.dp).noRippleClickableComposable { onBackHome() })
    }
}
