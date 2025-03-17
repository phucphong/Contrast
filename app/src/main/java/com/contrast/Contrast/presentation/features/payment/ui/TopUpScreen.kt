package com.contrast.Contrast.presentation.features.payment.ui


import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewTopUpScreen() {
    TopUpScreen(onClose = {}, onConfirm = {})
}

@Composable
fun TopUpScreen(onClose: () -> Unit, onConfirm: (String) -> Unit) {
    var selectedAmount by remember { mutableStateOf("50,000") }
    val amountOptions = listOf("10,000", "50,000", "100,000", "500,000", "1,000,000")

    Scaffold {
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
                onBackPress = { onClose() }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Hiển thị thông tin thẻ
            TopUpCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Chọn số tiền nạp
            AmountSelection(selectedAmount, amountOptions, onAmountSelected = { selectedAmount = it })

            Spacer(modifier = Modifier.weight(1f))

            // Nút tiếp tục
            Button(
                onClick = { onConfirm(selectedAmount) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.continue), color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TopUpCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF453A)) // Màu đỏ
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Trailblazer", color = Color.White, style = MaterialTheme.typography.titleMedium)
                Text("0 - 99", color = Color.White, style = MaterialTheme.typography.bodySmall)
                Text("Exclusive perks for high achievers", color = Color.White, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AmountSelection(selectedAmount: String, options: List<String>, onAmountSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        Text(
            text = stringResource(id = R.string.amount_label),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedAmount,
                onValueChange = {},
                readOnly = true,
                leadingIcon = { Text("đ", color = Color.Black) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text("đ $option") },
                        onClick = {
                            onAmountSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
