package com.contrast.Contrast.presentation.features.payment.topup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.MoneySelectionDialog
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldUpDown
import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBar

@Preview(showBackground = true)
@Composable
fun PreviewTopUpScreen() {
    TopUpScreen(onClose = {}, onConfirm = {})
}

@Composable
fun TopUpScreen(onClose: () -> Unit, onConfirm: (String) -> Unit) {
    var selectedAmount by remember { mutableStateOf("50,000") }
    var showDialog by remember { mutableStateOf(false) }
Column(  modifier = Modifier
    .fillMaxSize()

    .background(Color.White),
    horizontalAlignment = Alignment.CenterHorizontally) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        CustomTitleBar(
            title = stringResource(R.string.top_up),
            onBackPress = { /* Xử lý quay lại */ }
        )

        // Hiển thị thông tin thẻ
        Image(
            painter = painterResource(id = R.drawable.rewards),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Chọn số tiền nạp
        AmountSelection(selectedAmount, onAmountSelected = { selectedAmount = it })

        Spacer(modifier = Modifier.weight(1f))

        // Nút tiếp tục
        Button(
            onClick = { onConfirm(selectedAmount)
                showDialog = true
                      },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text(text = stringResource(id = R.string.continue_button), color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Hiển thị Dialog nếu `showDialog` là true
        MoneySelectionDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onAmountSelected = { showDialog = false }
        )
    }
}

    }



@Composable
fun AmountSelection(selectedAmount: String, onAmountSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {

     Row {    Image(


         painter = painterResource(id = R.drawable.coin),
         contentDescription = null,
         modifier = Modifier
             .width(30.dp)
             .height(30.dp).
         padding(5.dp)

     )

         Text(
             text = stringResource(id = R.string.amount_label),
             style = MaterialTheme.typography.bodyMedium,
             color = Color.Black,
             modifier = Modifier
                 .wrapContentWidth()
                 .height(30.dp).
                 padding(5.dp)
         ) }
        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldUpDown(
            value = selectedAmount,
            onValueChange = { onAmountSelected(it) },
            placeholder = "50,000",
        )
    }
}
