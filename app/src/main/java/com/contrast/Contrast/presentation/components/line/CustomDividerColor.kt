package com.contrast.Contrast.presentation.components.line

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.theme.AEA1F27




@Composable
fun CustomDividerColor (color: Color= AEA1F27, padding:Dp=0.dp){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp).padding(padding)
            .background(color, shape = RoundedCornerShape(50))
    )

//    Box(
//        Modifier
//            .border(width = 1.dp, color = Color(0x80F6F6F6))
//            .fillMaxWidth()
//            .height(1.dp)
//            .padding(bottom = 8.dp)
//    )
}