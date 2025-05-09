package com.contrast.Contrast.presentation.components.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.base64.decodeBase64ToBitmap
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.features.login.ui.LoginRegisterSwitch


@Composable
fun ProfileHeader(
    avartar: String,
    fullName: String,
    qrCode: String,
    agencyName: String,
    discount: Double,
    isLogin: Boolean,

    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {

    val imageBitmap = remember(qrCode) {
        decodeBase64ToBitmap(qrCode)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00BFA5))
            .padding(16.dp)
    ) {

        Box (Modifier.size(10.dp))
        if(isLogin){
            Row() {

                Box (modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.datatranfer),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(30.dp).padding(3.dp),
                    colorFilter = ColorFilter.tint(Color.White)

                )
                Box (Modifier.size(15.dp))

                Image(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(27.dp).padding(3.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {


            NetworkImage(
                model = avartar,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.width(12.dp))
            if (isLogin) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomText(
                        fullName,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            painter = painterResource(R.drawable.rankingbadge),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(16.dp)
                        )

                        CustomText(
                            agencyName,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(horizontal = 4.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.discount_account),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 4.dp)
                        )
                        CustomText(
                            "${discount.formatDouble()}%",
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 1.dp)
                        )
                    }
                }
            } else {



            }
            if (isLogin) {

                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(60.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.noimagevetical),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(60.dp)
                    )
                }


            }else{
                LoginRegisterSwitch(

                    onLoginClick = { onLoginClick() },
                    onRegisterClick = { onRegisterClick()}
                )
            }


        }
    }
}