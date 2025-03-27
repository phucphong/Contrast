package com.contrast.Contrast.presentation.features.payment.transactionHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.tab.TabBar
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.FFD91E18


data class Transaction(val title: String, val amount: String, val date: String, val balance: String)
@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun TransactionHistoryScreen() {
    val transactions = listOf(
        Transaction("Thanh toán đơn hàng", "- 150,000 đ", "16/02/2023 13:26", "Số dư ví: 500,000 đ"),
        Transaction("Thanh toán đơn hàng", "- 150,000 đ", "16/02/2023 13:26", "Số dư ví: 500,000 đ"),
        Transaction("Thanh toán đơn hàng", "- 150,000 đ", "16/02/2023 13:26", "Số dư ví: 500,000 đ")
    )

    var selectedTab by remember { mutableStateOf(0) }
    val  isData :Boolean = true
    val tabs = listOf("Thanh toán", "Nạp tiền", "Hoàn tiền")
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(vertical = 16.dp)
        , horizontalAlignment = Alignment.CenterHorizontally) {

        CustomTopAppBarTittleBack(
            title = "Lịch sử giao dịch",
            FFD91E18,
            onBackClick = { }
        )
        TabBar(tabs, selectedTab) { index -> selectedTab = index }
        if(isData){
            TransactionList(transactions)
        }else{
            Spacer(Modifier.size(80.dp))

               Image(painter = painterResource(R.drawable.no_history_tran),
                   contentDescription = "no_history_tran"
                   , modifier = Modifier.size(200.dp).padding(top = 40.dp))

               Text(
                   text = "Bạn chưa có giao dịch nào",
                   style = TextStyle(
                       fontSize = 16.sp,
                       lineHeight = 19.2.sp,
                       fontFamily = FontFamily(Font(R.font.inter)),
                       fontWeight = FontWeight(500),
                       color = Color(0xFF151515),
                       textAlign = TextAlign.Center,
                   )
               )




        }

    }
}




@Composable
fun TransactionList(transactions: List<Transaction>) {
    Column() {
        transactions.forEach { transaction ->
            TransactionItem(transaction)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Column() {
        Row(modifier = Modifier.padding(10.dp,8.dp,10.dp,0.dp)){ Text(
            text = transaction.title,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = FF000000,
            ) , modifier = Modifier.weight(1f)
        )
            Text(
                text = "- 150,000 đ",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = FF000000,
                ), textAlign = TextAlign.Right
                , modifier = Modifier.weight(1f)
            )

        }

        Row(modifier = Modifier.padding(10.dp,8.dp,10.dp,0.dp)){

           Column(modifier = Modifier.wrapContentHeight()) {
               Text(
               text = "+ 20 Cup",
               style = TextStyle(
                   fontSize = 14.sp,
                   lineHeight = 16.8.sp,
                   fontFamily = FontFamily(Font(R.font.inter)),
                   fontWeight = FontWeight(400),
                   color = FF000000,
               )
           )


               Text(
                   text = transaction.balance,
                   style = TextStyle(
                       fontSize = 14.sp,
                       lineHeight = 16.8.sp,
                       fontFamily = FontFamily(Font(R.font.inter)),
                       fontWeight = FontWeight(500),
                       color = FF000000,
                   ),
                   modifier = Modifier.padding(vertical = 8.dp)


               )
           }

            Text(
                text = transaction.date,
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = FF000000,
                ), textAlign = TextAlign.Right
                , modifier = Modifier.weight(1f).padding(top = 8.dp)

            )

        }



        CustomDividerColor()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionHistoryScreen() {
    TransactionHistoryScreen()
}
