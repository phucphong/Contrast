package com.contrast.Contrast.presentation.features.notification.ui



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.itechpro.domain.model.Notification
import com.itechpro.domain.model.NotificationType

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column ( modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)){
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(16.dp)
                  .background(Color.White)
          ) {
              // Thẻ loại thông báo (nếu có)
              notification.type?.let { type ->
                  NotificationTag(type)
                  Spacer(modifier = Modifier.height(8.dp))
              }

              // Tiêu đề thông báo
              Text(
                  text = notification.title,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold
              )

              // Nội dung thông báo (nếu có)
              notification.message?.let {
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                      text = it,
                      fontSize = 14.sp,
                      color = Color.Gray
                  )
              }

              Spacer(modifier = Modifier.height(8.dp))

              // Ngày tạo thông báo (bên phải)
              Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Text(
                      text = notification.date,
                      fontSize = 12.sp,
                      color = Color.Gray
                  )

                  // Trạng thái đơn hàng (nếu có)
                  notification.status?.let {
                      Text(
                          text = it,
                          fontSize = 12.sp,
                          color = Color.Blue,
                          fontWeight = FontWeight.Bold
                      )
                  }
              }
          }
      }
    }
}

@Composable
fun NotificationTag(type: NotificationType) {
    val backgroundColor = when (type) {
        NotificationType.VOUCHER -> Color(0xFFDCF4FF)
        NotificationType.EVENT -> Color(0xFFFFF5DA)
        NotificationType.NEWS -> Color(0xFFFFE4E1)
    }

    val textColor = when (type) {
        NotificationType.VOUCHER -> Color(0xFF007BFF)
        NotificationType.EVENT -> Color(0xFFFFA500)
        NotificationType.NEWS -> Color.Red
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = when (type) {
                NotificationType.VOUCHER -> stringResource(R.string.voucher)
                NotificationType.EVENT -> stringResource(R.string.event)
                NotificationType.NEWS -> stringResource(R.string.news)
            },
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}
