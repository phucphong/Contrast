package com.contrast.Contrast.presentation.components.logout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R

@Composable
fun LogoutButton(onClickLogout :()->Unit) {
    Button(
        onClick = {onClickLogout()},
        modifier = Modifier
            .fillMaxWidth()
            .padding( 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Text(stringResource(R.string.logout), color = Color.Black)
    }
}
