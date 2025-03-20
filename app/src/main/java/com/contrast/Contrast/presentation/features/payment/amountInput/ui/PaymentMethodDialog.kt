package com.contrast.Contrast.presentation.features.payment.amountInput.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFD7D7D7
import com.contrast.Contrast.presentation.theme.FF1E7D3A
import com.contrast.Contrast.presentation.components.line.CustomDivider
import com.contrast.Contrast.presentation.components.text.CustomText

@Preview(showBackground = true)
// Dialog hiển thị danh sách phương thức thanh toán giống Dialog Fragment
@Composable
fun PaymentMethodDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false) // Đảm bảo UI full-width
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.92f) // Chiếm 90% chiều cao màn hình
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)) // Bo tròn góc trên
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FF1E7D3A),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close),
                            tint = Color.White
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.payment_methods),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )

                }
                Column {

                    // Header


                    Spacer(modifier = Modifier.height(8.dp))

                    val paymentMethods = listOf(
                        PaymentMethodData(
                            R.drawable.card,
                            stringResource(id = R.string.domestic_card),
                            stringResource(id = R.string.domestic_banks)
                        ),
                        PaymentMethodData(
                            R.drawable.card,
                            stringResource(id = R.string.international_card),
                            stringResource(id = R.string.international_banks)
                        ),
                        PaymentMethodData(
                            R.drawable.ic_wallet,
                            stringResource(id = R.string.e_wallet),
                            stringResource(id = R.string.wallet_payment)
                        )
                    )

                    LazyColumn(modifier = Modifier.fillMaxHeight(0.8f)) { // Chiếm 80% chiều cao Dialog
                        items(paymentMethods) { method ->
                            PaymentMethodItem(
                                icon = method.icon,
                                title = method.title,
                                subtitle = method.subtitle
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(FFD7D7D7, shape = RoundedCornerShape(50))
                    )
                    // Footer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically // Canh giữa theo chiều dọc
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.card), // Icon bảo mật
                            contentDescription = stringResource(id = R.string.pci_dss),
                            modifier = Modifier
                                .size(60.dp)
                                .weight(1f), // Giúp hình ảnh không chiếm quá nhiều không gian
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Tạo khoảng cách giữa hình và text

                        Column(
                            modifier = Modifier.weight(3f), // Chiếm nhiều không gian hơn để chứa text
                            horizontalAlignment = Alignment.End // Canh trái cho text

                        ) {


                            CustomText(
                                text = stringResource(id = R.string.powered_by_payoo),
                                FontWeight.Bold,
                                fontSize = 14.sp, // Font lớn hơn một chút
                                color = Color.Black,
                                TextAlign.Right // Căn chỉnh text về bên phải
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            CustomText(
                                text = stringResource(id = R.string.secure_transaction),
                                FontWeight.Medium,
                                fontSize = 12.sp, // Font lớn hơn một chút
                                color = Color.Black,
                                TextAlign.Right // Căn chỉnh text về bên phải
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }


                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(icon: Int, title: String, subtitle: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                CustomText(text = title, fontSize = 16.sp, color = Color.Black)
                CustomText(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
            Image(
                painter = painterResource(R.drawable.down),
                contentDescription = "",
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .padding(5.dp)

            )
        }

        CustomDivider()
    }
}

data class PaymentMethodData(val icon: Int, val title: String, val subtitle: String)
