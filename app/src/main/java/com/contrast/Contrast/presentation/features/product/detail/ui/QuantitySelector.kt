package com.contrast.Contrast.presentation.features.product.detail.ui
import androidx.compose.foundation.background
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
import com.contrast.Contrast.presentation.theme.FFFF9800

@Preview(showBackground = true)
@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(6.dp))
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.quantity),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Nút trừ
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .padding(2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
                .clickable(enabled = quantity > 1) {
                    onQuantityChange(quantity - 1)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // Số lượng
        Box(
            modifier = Modifier
                .width(35.dp)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        // Nút cộng
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .padding(2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
                .clickable {
                    onQuantityChange(quantity + 1)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
