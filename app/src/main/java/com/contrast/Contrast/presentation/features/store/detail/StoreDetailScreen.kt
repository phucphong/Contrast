package com.contrast.Contrast.presentation.features.store.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.slider.ImageSlider
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.contrast.Contrast.presentation.theme.FFFFF6F6

@Preview(device = Devices.PHONE)
@Composable
fun StoreDetailScreen() {
    val context = LocalContext.current
    var showFeedbackDialog by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().background(FCFCFC).verticalScroll(rememberScrollState()),
        ) {
        // Header Image
       Box {

           ImageSlider(0.dp)
           Image(painter = painterResource(R.drawable.back), contentDescription = "back",
               modifier = Modifier.size(60.dp). padding(15.dp)

               )
       }

        // Content Card
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(16.dp),
               ) {
                Text(
                    text = "Contrast Văn Chương",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = FFD91E18,
                        textAlign = TextAlign.Center,

                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 50.dp)
                )

                Text(
                    text = "Chi tiết địa chỉ",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF7C7C7C),
                    ),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                )

                // Address
                Card ( shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color.White
                  ){

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding( 20.dp,20.dp,10.dp,20.dp)) {
                        Image(painter = painterResource(id = R.drawable.location_store),
                            contentDescription = null, modifier = Modifier.size(30.dp).padding(end = 10.dp))

                        Text(text = stringResource(id = R.string.store_address), color =FF151515)

                    }

                }



                Text(
                    text = "Hotline",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF7C7C7C),
                    )
                    ,modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                )
                Card ( shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color.White,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                ) {
                    // Hotline
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(20.dp, 10.dp, 10.dp, 10.dp).clickable {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0333687462"))
                            context.startActivity(intent)
                        }) {
                        Image(
                            painter = painterResource(id = R.drawable.call),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp).padding(end = 10.dp)
                        )

                        Text(text = "0333687462", color =FF151515)
                    }
                }



                // Notification
                Text(text = stringResource(id = R.string.store_note), color = FF7C7C7C,
                    modifier = Modifier.padding(vertical = 20.dp))



                Text(
                    text = "Giờ mở cửa",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 23.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF7C7C7C),
                    )
                    , modifier = Modifier.padding(vertical = 10.dp)
                )
                // Open Hours
                Card(
                    backgroundColor = FFFFF6F6,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp,16.dp,0.dp,16.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.padding(end = 15.dp)) {

                            Text(
                                text =stringResource(id = R.string.store_open),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 32.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFFD91E18),
                                )
                            )

                            Text(
                                text = stringResource(id = R.string.store_open_24),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = FFD91E18,
                                ),
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }

                        Image(painter = painterResource(R.drawable.clock_store),
                            contentDescription = "clock_store",
                            modifier = Modifier.size(50.dp))
                        Text(
                            text = "24",
                            style = TextStyle(
                                fontSize = 30.sp,
                                lineHeight = 46.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(800),
                                color = FFD91E18,
                            ),
                                    modifier = Modifier.padding(start = 15.dp)

                        )
                        Text(
                            text = "GIỜ",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
                                color = FFD91E18,
                            ),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Column {
                            Image(
                            painter = painterResource(id = R.drawable.open_24),
                            contentDescription = "image description",
                            contentScale = ContentScale.None,
                            modifier =  Modifier.size(15.dp)


                        )
                            Box(modifier = Modifier.size(20.dp))


                        }
                    }

                }




                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // 🔹 NÚT PHẢN HỒI
                    Button(
                        onClick = { showFeedbackDialog = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.elevation(6.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.Red, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.feedback),
                                    contentDescription = "Feedback Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Phản hồi",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.8.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1D1D),
                                )
                            )
                        }
                    }

                    // 🔹 NÚT CHIA SẺ
                    Button(
                        onClick = { showShareDialog = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.elevation(6.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.Red, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.share), // icon khác feedback
                                    contentDescription = "Share Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Chia sẻ",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.8.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1D1D),
                                )
                            )
                        }
                    }
                }


            }
        }
    }
}
