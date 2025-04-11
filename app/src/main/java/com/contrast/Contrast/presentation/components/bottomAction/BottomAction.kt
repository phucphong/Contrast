package com.contrast.Contrast.presentation.components.bottomAction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun BottomAction(icon: Int, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .noRippleClickableComposable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = icon), contentDescription = label, modifier = Modifier.size(24.dp))
        Text(text = label, fontSize = 12.sp, modifier = Modifier.padding(5.dp))
    }
}
