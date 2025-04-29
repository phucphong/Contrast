package com.contrast.Contrast.presentation.features.product.detail.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.FF9E9E9E
import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.TealGreen

@Preview(showBackground = true)
@Composable
fun QuantitySelector(

    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    quantity: Double,
    increaseQuantity: () -> Unit,
    showQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .padding(vertical = 20.dp, horizontal = 1.dp)
    ) {
        Text(
            text = stringResource(R.string.quantity),
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, FF9E9E9E, RoundedCornerShape(4.dp))
                .background(Color.White)
        ) {
            // Nút trừ
            Box(
                modifier = Modifier
                    .width(21.dp)
                    .height(21.dp)

                    .clickable { decreaseQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Text("-", fontSize = 14.sp, color = TealGreen)
            }
            Box (Modifier.width(1.dp).height(20.dp).background(FF9E9E9E))
            // Số lượng
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .padding(horizontal = 8.dp).noRippleClickableComposable { showQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quantity.toString().replace(".0",""),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
            Box (Modifier.width(1.dp).height(20.dp).background(FF9E9E9E))
            // Nút cộng
            Box(
                modifier = Modifier
                    .width(21.dp)
                    .height(21.dp)

                    .clickable { increaseQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Text("+", fontSize = 11.sp,  color = TealGreen)
            }
        } }
}

