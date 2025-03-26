package com.contrast.Contrast.presentation.features.membership.benefits

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack
import com.contrast.Contrast.presentation.theme.AEA1F27
import com.contrast.Contrast.presentation.theme.D292D32
import com.contrast.Contrast.presentation.theme.FFFFFFFF

// Quyền lại hàng thành viên

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MemberBenefitsScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header
        CustomTopAppBarTittleBack(
            title = "Quyền lợi hàng thẻ thành viên",
            Color.Red,
            onBackClick = { }
        )



        Spacer(modifier = Modifier.height(16.dp))

        // Membership card
        // Membership cards list (LazyRow)
        LazyRow(

        ) {
            items(5) { index -> // giả sử có 5 ảnh thẻ (bạn thay bằng danh sách thật)
                var isActive: Boolean = true

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 10.dp)
                        .shadow(
                            elevation = 15.dp,
                            spotColor = D292D32,
                            ambientColor = D292D32
                        )
                    ) {

                 if (isActive){
                     Image(
                         painterResource(R.drawable.rewards) ,
                         contentDescription = "Reward Card $index",
                         modifier = Modifier
                             .width(310.dp)
                             .height(200.dp)
                             .clip(RoundedCornerShape(16.dp)), // placeholder nếu ảnh load lâu
                         contentScale = ContentScale.Crop
                     )
                 }else{
                     LockedCard()
                 }


                }


            }
        }


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Quyền lợi hạng thẻ",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Benefit List
        Column {
            BenefitItem("Buy One Get One for first 5 Stars earned")
            BenefitItem("Birthday Coupon")
        }
    }
}
@Composable
fun LockedCard() {
    Box(
        modifier = Modifier
            .width(310.dp)

            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.lock_circle),
                contentDescription = "Locked",

                modifier = Modifier.size(60.dp)
            )

            Text(
                text = "Chưa đủ Cup để mở khóa thẻ",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500),
                    color = FFFFFFFF,
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun BenefitItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Icon(
            imageVector = Icons.Default.StarBorder,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFFE40000)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp)
    }
}
