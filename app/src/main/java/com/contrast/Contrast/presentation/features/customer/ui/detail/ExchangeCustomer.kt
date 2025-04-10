package com.contrast.Contrast.presentation.features.customer.ui.detail


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.itechpro.domain.model.InfoDetail
import androidx.compose.foundation.layout.*


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.iOSUnderlineGray
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ExchangeCustomer(
    info: InfoDetail,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Hàng chứa avatar + tên + thời gian
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Transparent)
            ) {
                // TODO: Load avatar nếu có
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Row {
                    CustomText(
                        text =info.value,
                        color = Color(0xFF00AA88),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CustomText(
                        text =info.value,
                        color = PlaceholderGray,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                CustomText(
                    text = info.value,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(start = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row( modifier = Modifier.clickable { onEditClick() }) {

                Image(painter= painterResource(R.drawable.edit),
                    contentDescription = "delete", modifier = Modifier.size(30.dp))

                CustomText(
                    text = stringResource(R.string.edit),
                    color = Color(0xFF007AFF),
                    fontSize = 13.sp,

                    ) }
           Row( modifier = Modifier.clickable { onDeleteClick() }) {

               Image(painter= painterResource(R.drawable.trash),
                   contentDescription = "delete", modifier = Modifier.size(30.dp))

               CustomText(
                   text = stringResource(R.string.delete),
               color = Color(0xFF007AFF),
               fontSize = 13.sp,

           ) }
        }

        Spacer(modifier = Modifier.height(4.dp))
        CustomDividerColor(iOSUnderlineGray)
    }
}
