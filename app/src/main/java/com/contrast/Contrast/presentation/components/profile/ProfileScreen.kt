package com.contrast.Contrast.presentation.components.profile
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil3.compose.AsyncImage
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.itechpro.domain.model.Video

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun ProfileScreen() {
   Column {

       ProfileHeader()
       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .background(Color.White).weight(1f),
           contentPadding = PaddingValues(bottom = 80.dp) // chừa khoảng cho bottom nav nếu có
       ) {

           item {
               OrderStatusRow()
           }



           items(profileOptions) { option ->
               ProfileOptionItem(option)
           }


       }

       LogoutButton()
   }
}

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00BFA5))
            .padding(16.dp)
    ) {
        Box(Modifier.size(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.nodata), // Thay icon avatar ở đây
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("Admin", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("0%", color = Color.White)
                }
            }

            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = "QR",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun OrderStatusRow() {
    Column { Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OrderStatusItem(
            icon = R.drawable.ic_edit, // drawable của bạn
            title = "Đặt hàng\nchờ xác nhận"
        )
        OrderStatusItem(
            icon = R.drawable.ic_edit,
            title = "Đơn hàng\nđã xác nhận"
        )
        OrderStatusItem(
            icon = R.drawable.ic_edit,
            title = "Đơn hàng\nđã mua"
        )
    }
        SectionDivider() }
}

@Composable
fun OrderStatusItem(
    icon: Int,
    title: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            colorFilter = ColorFilter.tint(Color(0xFF00BFA5))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun SectionDivider() {
    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
}

val profileOptions = listOf(
    "Sản phẩm đã xem",
    "Sản phẩm đã lưu",
    "Liệu trình đang thực hiện",
    "Lịch thực hiện dịch vụ",
    "Hướng dẫn spa tại nhà","Sản phẩm đã xem",
    "Sản phẩm đã lưu",
    "Liệu trình đang thực hiện",
    "Lịch thực hiện dịch vụ",
    "Hướng dẫn spa tại nhà",
    "Liệu trình đang thực hiện",
    "Lịch thực hiện dịch vụ",
    "Hướng dẫn spa tại nhà","Sản phẩm đã xem",
    "Sản phẩm đã lưu",
    "Liệu trình đang thực hiện",
    "Lịch thực hiện dịch vụ",
    "Hướng dẫn spa tại nhà"
)

@Composable
fun ProfileOptionItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color(0xFF00BFA5))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, fontSize = 16.sp)
    }
    Divider(color = Color(0xFFF0F0F0))
}

@Composable
fun LogoutButton() {
    Button(
        onClick = { /* TODO */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding( 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Text("Đăng xuất", color = Color.Black)
    }
}
