package com.contrast.Contrast.presentation.components.alertDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
@Preview(showBackground = true)
@Preview(name = "Light Mode", showBackground = true)
//thông báo ổi voucher thành công
@Composable
fun VoucherRedeemDialog(
    onDismiss: () -> Unit,
    onViewVouchers: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Close Icon",
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.congratulations),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_voucher_success),
                    contentDescription = "Voucher Success Image",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.voucher_redeem_success),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.view_vouchers),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onViewVouchers() }
                )
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}