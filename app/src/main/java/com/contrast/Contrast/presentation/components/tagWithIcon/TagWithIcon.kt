package com.contrast.Contrast.presentation.components.tagWithIcon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import com.contrast.Contrast.R
@Composable
fun TagWithIcon(
    icon: Painter,
    text: String,
    isAdd: Boolean,
    backgroundColor: Color = Color(0xFFF6F6F6),
    iconBackgroundColor: Color = Color(0xFFFFB43A),
    iconTint: Color = Color.White,
    textColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(iconTint)
        )



        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 10.dp)
        )
        if(isAdd){
            Image(
                painter = painterResource(R.drawable.addlich),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(iconTint)
            )
        }
    }
}
