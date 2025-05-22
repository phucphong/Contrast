package com.contrast.Contrast.presentation.components.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.LightGrayBackground
import com.contrast.Contrast.presentation.theme.TealGreen

@Composable
fun CompactDropdownField(
    value: String,
    textColor: Color= TealGreen,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    isDown: Boolean = false,
    onClick: () -> Unit = {},

) {

    val customFontFamily = FontFamily(
        Font(R.font.inter),
        Font(R.font.inter_18pt_bold, FontWeight.Bold),
    )


    Box(
        modifier = modifier
            .height(40.dp)
            .background(if(isRequired) LightGrayBackground else Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFD7D7D7), shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
    Row {
        Box(modifier = modifier
            .weight(1f)
            .padding(vertical = 5.dp)
            .noRippleClickableComposable { onClick() }
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontFamily = customFontFamily,
                color = textColor,
            )
        }
        if(isDown){
            Image(
                painter = painterResource(R.drawable.down),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .padding( vertical = 5.dp)

            )
        }



    }
    }
}
