package com.contrast.Contrast.presentation.components.topAppBar



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.TealGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBarBackTitleFilter(
    title: String,
    painter: Painter= painterResource(id = R.drawable.quaylai),
    fontSize: TextUnit = 14.sp,
    titleColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold,
    iconTint: Color = TealGreen,

    onBackClick: () -> Unit,
    onFilterClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            CustomText(
                text = title,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = titleColor, textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter =painter,
                    contentDescription = "Back",
                    tint = iconTint,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        },
        actions = {
            if ( onFilterClick != null) {

                IconButton(onClick = onFilterClick) {
                    Icon(
                        painter = painterResource(R.drawable.filter),
                        contentDescription = "filter",
                        tint = iconTint
                    )
                }
            }
        }
        ,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor
        )
    )
}
