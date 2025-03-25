package com.contrast.Contrast.presentation.features.main.home.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.FaceIDAuthDialog
import com.contrast.Contrast.presentation.components.alertDialog.FaceIDPermissionDialog
import com.contrast.Contrast.presentation.components.alertDialog.FeedbackSuccessDialog
import com.contrast.Contrast.presentation.components.slider.ImageSlider
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.features.feedback.FeedbackDialog
import com.contrast.Contrast.presentation.features.payment.ui.LockVerificationDialog
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FFFCFCFC

@Preview(showBackground = true)
@Composable
fun HomePage() {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FCFCFC)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 HEADER */
        /** 🔹 HEADER */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.avatar1),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    CustomText("Contrast xin chào 👋", fontSize = 12.sp, color = Color.Gray)
                    CustomText("Nguyễn Phúc Phong", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Row {
                IconButton(onClick = { /* TODO: Notifications */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        tint = Color.Red,
                        contentDescription = "Thông báo"
                    )
                }
                IconButton(onClick = { /* TODO: Wallet */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wallet),
                        tint = Color.Red,
                        contentDescription = "Nạp tiền"

                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 SỐ DƯ */

        WalletBalanceView()

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 BANNER QUẢNG CÁO */
        ImageSlider(10.dp)

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 SỰ KIỆN ĐANG DIỄN RA */


        Text(
            text = "Sự kiện đang diễn ra",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF151515),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // 🔥 Thêm khoảng cách giữa các item
        ) {
            items(listOf(
                "Tặng Sticker chúc mừng ngày Quốc Khánh 2-9",
                "Đổi bạn cùng tiền",
                "Mua 1 tặng 1 ngày hội cà phê"
            )) { event ->
                EventCard("10 ",
                    "TH6",
                    "Tặng Sticker chúc mừng ngày Quốc Khánh 2-9",
                    "Mua được giảm giá")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 DANH SÁCH CỬA HÀNG */
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {


            Text(
                text = "Danh sách cửa hàng",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF151515),
                    textAlign = TextAlign.Center,
                )
            )
            ClickableText(
                text = AnnotatedString("XEM TẤT CẢ"),

                onClick = { /* TODO: Xem tất cả cửa hàng */ },
                style = TextStyle(color = Color.Red, fontSize = 14.sp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // 🔥 Khoảng cách giữa các cửa hàng
        ) {
            items(listOf(
                Pair("3,5 km", "Contrast Văn Chương"),
                Pair("4,2 km", "Contrast Tô Hiệu"),
                Pair("2,1 km", "Contrast Trần Duy Hưng")
            )) { (distance, name) ->
                StoreCard( name, "Địa chỉ chi tiết ở đây...")
            }}

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 NÚT PHẢN HỒI */
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.elevation(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 90.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.feedback),
                        contentDescription = "Feedback Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Phản hồi",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.8.sp,
                        fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF1D1D1D),
                    )
                )
            }
        }

//        FeedbackDialog(
//            showDialog = showDialog,
//            onDismiss = { showDialog = false },
//            onContinue = { selectedBranch ->
//                showDialog = false
//                println("Người dùng chọn cơ sở: $selectedBranch")
//            }
//        )

        Spacer(modifier = Modifier.height(16.dp))


        // yêu cầu vân tay
//        BiometricDisableDialog(
//            onDisableBiometric = { showDialog = false },
//            onDismiss = { showDialog = false }
//        )

//        CongratulationsDialog(
//            onDismiss = { showDialog = false },
//            onRedeem = { showDialog = false }
//        )
//
//        CustomAlertDialog(
//            "Thành công",
//            onDismiss = { showDialog = false }
//        )


//        LockVerificationDialog(
//            showDialog = true,
//            onDismiss = { showDialog = false }
//        )

//        FaceIDAuthDialog(
//            onDismiss = { showDialog = false }
//        )


//        FaceIDPermissionDialog(
//            onConfirm = { showDialog = false },
//            onDismiss = { showDialog = false }
//        )

        // thoong bao phản hồi thành công
//        FeedbackSuccessDialog(
//            onDismiss = { showDialog = false }
//        )

    }
}
@Composable
fun WalletBalanceView() {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔹 Card hiển thị số dư
        Card(
            modifier = Modifier
                .weight(1f) // Chiếm toàn bộ phần còn lại
                .clip(RoundedCornerShape(8.dp)), // Bo góc
            backgroundColor = Color.Black, // Nền đen
            elevation = 4.dp // Bóng đổ nhẹ
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    CustomText("Ví của tôi", fontSize = 14.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CustomText(
                            "đ 8,656.60",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.arrowright),
                            contentDescription = "Xem chi tiết",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 🔹 Nút "Nạp tiền"
        Box(
            modifier = Modifier
                .height(78.dp) // 🔥 Đặt chiều cao cố định
                .width(130.dp) // 🔥 Định kích thước giống thiết kế
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)) // 🔥 Viền đen
        ) {
            Button(
                onClick = { /* TODO: Xử lý nạp tiền */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxSize() // 🔥 Đảm bảo nút full trong Box
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Thêm",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Nạp tiền", color = Color.White, fontSize = 14.sp)
                }
            }
        }

    }
}
