package com.contrast.Contrast.presentation.features.product.share
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.itechpro.domain.model.ShareOption
import kotlinx.coroutines.launch
@Preview(device = Devices.PHONE)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShareBottomSheet(
    onDismissRequest: () -> Unit,
    onShareClick: (ShareOption) -> Unit
) {
    val shareOptions = listOf(
        ShareOption("Messenger", R.drawable.messenger),
        ShareOption("Zalo", R.drawable.zalo),
        ShareOption("WhatsApp", R.drawable.whatsapp),
        ShareOption("Facebook", R.drawable.facebook),
        ShareOption("Tin nhắn", R.drawable.sms),
        ShareOption("Copy Link", R.drawable.copy_link)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Chia sẻ để nhận Hoa hồng ngay!",
                style = MaterialTheme.typography.h4
            )
            IconButton(onClick = onDismissRequest) {
                Icon(Icons.Default.Close, contentDescription = "Đóng")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Hoa hồng info
        Text(
            text = "Hoa hồng Tiếp thị liên kết 13%, ước tính ₫49.270",
            style = MaterialTheme.typography.h4,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid các app chia sẻ
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(shareOptions) { option ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onShareClick(option) }
                ) {
                    Icon(
                        painter = painterResource(id = option.iconRes),
                        contentDescription = option.label,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = option.label, style = MaterialTheme.typography.h4)
                }
            }
        }
    }
}

