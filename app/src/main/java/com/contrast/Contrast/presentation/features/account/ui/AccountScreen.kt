package com.contrast.Contrast.presentation.features.account.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.account.personalInfo.PersonalInfoScreen
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFD91E18

@Preview(device = Devices.PHONE, showBackground = true)

@Composable
fun AccountScreenPreview() {
    val navController = rememberNavController()  // Tạo NavController giả lập cho Preview
    AccountScreen(
        navController = navController,

        )
}

@Composable
fun AccountScreen( navController: NavController) {
    val accountItems = listOf(
        R.string.personal_info to R.drawable.user,
        R.string.membership to R.drawable.ic_medal,
        R.string.transaction_history to R.drawable.ic_transaction
    )

    val settingsItems = listOf(
        R.string.preferences to R.drawable.ic_settings,
        R.string.language to R.drawable.ic_language,
        R.string.security to R.drawable.ic_shield
    )

    val supportItems = listOf(
        R.string.faq to R.drawable.ic_help,
        R.string.feedback to R.drawable.ic_feedback
    )

    val policyItems = listOf(
        R.string.terms_of_use to R.drawable.ic_terms,
        R.string.privacy_policy to R.drawable.ic_privacy
    )

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        // Avatar & Tên người dùng
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.avatar1), // Ảnh avatar
                contentDescription = "Avatar",
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        // Chuyển hướng sang màn hình thông tin cá nhân
                        navController.navigate("personal_info")
                    }
            ) {
                Text(text = "Xin chào!", fontSize = 12.sp, color = Color.Gray)
                Text(text = "Long Duy Luong", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Image(
                painter = painterResource(R.drawable.close), // Ảnh avatar
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        // Đoạn mã xử lý sự kiện click ở đây
                        // Ví dụ: Log ra thông tin hoặc thực hiện hành động khác

                    }
            )

        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 10.dp).weight(1f)) {
            item {
                Image(
                    painter = painterResource(R.drawable.rewards),
                    contentDescription = "Illustration",
                    modifier = Modifier
                        .height(230.dp)
                        .fillMaxWidth().padding( vertical = 6.dp, horizontal = 8.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
//            stickyHeader { SectionHeader(title = stringResource(R.string.account_details)) }// cố định khi kéo tới đỉnh
            item { SectionHeader(title = stringResource(R.string.account_details)) }
            items(accountItems) { (titleRes, icon) -> MenuItem(title = stringResource(titleRes), icon = icon) }

            item { SectionHeader(title = stringResource(R.string.settings)) }
            items(settingsItems) { (titleRes, icon) -> MenuItem(title = stringResource(titleRes), icon = icon) }

            item { SectionHeader(title = stringResource(R.string.support)) }
            items(supportItems) { (titleRes, icon) -> MenuItem(title = stringResource(titleRes), icon = icon) }

            item { SectionHeader(title = stringResource(R.string.policy)) }
            items(policyItems) { (titleRes, icon) -> MenuItem(title = stringResource(titleRes), icon = icon) }
        }




        Button(
            onClick = { /* TODO: Logout */ },
            modifier = Modifier.fillMaxWidth().padding(25.dp,0.dp,25.dp,25.dp).height(48.dp),
            shape = RoundedCornerShape(16),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Red),
            border = BorderStroke(1.dp, FFD91E18)
        ) {
            Text(
                text = "Đăng xuất",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    color = FFD91E18,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(500),
            color = FF000000),
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )


}

@Composable
fun MenuItem(title: String, icon: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { /* TODO: Xử lý sự kiện click */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = FF151515,
            ), modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(R.drawable.arrowright),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
    Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
}
