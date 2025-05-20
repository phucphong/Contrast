package com.contrast.Contrast.presentation.features.login.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText

import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.navigationEvent.SplashNaEvent

@Composable
fun LoginRegisterSwitch(

    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth().padding(horizontal = 10.dp),

    ) {

        val backgroundSelected = Color.White

        val textColorSelected = TealGreen
        val textColorUnselected = Color.White
        Box(
            modifier = Modifier
                .weight(1f)

        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, backgroundSelected, shape = RoundedCornerShape(8.dp))
                .background(backgroundSelected, shape = RoundedCornerShape(8.dp))
                .noRippleClickableComposable { onLoginClick() },
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(R.string.login),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color =textColorSelected,
                modifier = Modifier.padding(0.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, backgroundSelected, shape = RoundedCornerShape(8.dp))
                .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp) )
                .noRippleClickableComposable { onRegisterClick() },
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(R.string.register),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = textColorUnselected,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}
