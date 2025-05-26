package com.contrast.Contrast.presentation.components.searchBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.cart.CartIconWithBadge
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.notification.NotificationIconWithBadge
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.iOSUnderlineGray
@Preview(showBackground = true)
@Composable
fun TopBackSearchFilter(
    painter: Painter = painterResource(R.drawable.quaylai),
    modifier: Modifier = Modifier,
    placeholder: String = "Tìm kiếm",
    isBackStack: Boolean = true,
    isFilter: Boolean = true,
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    onBackStack: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onSearchClick: () -> Unit = {} // ✅ Thêm callback mới
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isBackStack) {

            Image(
                painter = painter,
                contentDescription = "Back",
                colorFilter = ColorFilter.tint(TealGreen),
                modifier = Modifier.size(24.dp).noRippleClickableComposable { onBackStack() }
            )

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .padding(horizontal = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(18.dp)
                        .noRippleClickableComposable { onSearchClick() }, // ✅ gọi search tại đây
                    colorFilter = ColorFilter.tint(iOSUnderlineGray)
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = text,
                    onValueChange = onTextChanged,
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search), // ✅ IME Action
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide() // ✅ ẩn bàn phím
                            onSearchClick() // ✅ gọi search
                        }
                    ),
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )

                if (text.isNotEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.closeview),
                        contentDescription = "Clear text",
                        modifier = Modifier
                            .size(18.dp)
                            .noRippleClickableComposable { onTextChanged("")
                                onSearchClick()
                            }
                    )
                } else {
                    if (isFilter) {
                        Image(
                            painter = painterResource(R.drawable.filter),
                            contentDescription = "Filter",
                            colorFilter = ColorFilter.tint(TealGreen),
                            modifier = Modifier
                                .size(28.dp)
                                .padding(vertical = 5.dp)
                                .noRippleClickableComposable { onFilterClick() }
                        )
                    }
                }
            }
        }
    }
}
