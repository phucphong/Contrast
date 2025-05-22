package com.contrast.Contrast.presentation.components.topAppBar



import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.cart.CartIconWithBadge
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.notification.NotificationIconWithBadge
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.iOSUnderlineGray

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun BackSearchInfomation(
    painter: Painter = painterResource(R.drawable.quaylai),
    modifier: Modifier = Modifier,
    placeholder: String = "Tìm kiếm",
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    onInfoClick: () -> Unit = {},
    onBackStack: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter= painter,
            contentDescription = "Search Icon",
            modifier = Modifier.size(35.dp).padding(5.dp).noRippleClickableComposable { onBackStack() }
            , colorFilter = ColorFilter.tint(TealGreen)
        )

        // Search Box
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp).padding(horizontal = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF5F5F5))
                .noRippleClickableComposable { onInfoClick() },
            contentAlignment = Alignment.CenterStart
        ) {


            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter= painterResource(R.drawable.search),
                    contentDescription = "Search Icon",

                    modifier = Modifier.size(20.dp).padding(2.dp)
                    , colorFilter = ColorFilter.tint(iOSUnderlineGray)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    BasicTextField(
                        value = text,
                        onValueChange = onTextChanged,
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start // ✅ căn trái
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 4.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart // ✅ căn giữa dọc
                            ) {
                                innerTextField()
                            }
                        }
                    )

                    if (text.isNotEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.closeview),
                            contentDescription = "Clear text",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(20.dp)
                                .noRippleClickableComposable {
                                    onTextChanged("")
                                }
                        )
                    }
                }

            }
        }





        Image(
            painter = painterResource(id = R.drawable.info),
            contentDescription = "info",
            modifier = Modifier
                .size(30.dp).padding(5.dp),
            colorFilter = ColorFilter.tint(TealGreen)
        )

    }
}
