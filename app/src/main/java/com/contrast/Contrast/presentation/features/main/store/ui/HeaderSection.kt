package com.contrast.Contrast.presentation.features.main.store.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.contrast.Contrast.presentation.theme.FFD91E18

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun HeaderSection(store:String  ) {


       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ) {
           Column(Modifier.weight(1f)) {
               Text(
                   text = stringResource(R.string.choose_store_pickup),
                   style = TextStyle(
                       fontSize = 13.17.sp,
                       lineHeight = 18.44.sp,
                       fontFamily = FontFamily(Font(R.font.inter)),
                       fontWeight = FontWeight(400),
                       color = Color(0xFF757575),
                       letterSpacing = 0.19.sp,
                   )
               )
          Row (Modifier.padding(top = 3.dp), verticalAlignment = Alignment.CenterVertically){
              Image(painter = painterResource(R.drawable.map_home), contentDescription = "",Modifier.size(20.dp))
              Text(
              text = store,
              style = TextStyle(
                  fontSize = 14.12.sp,
                  lineHeight = 16.94.sp,
                  fontFamily = FontFamily(Font(R.font.inter)),
                  fontWeight = FontWeight(600),
                  color = Color(0xFF212121),
              )
                  , modifier = Modifier.padding(horizontal = 10.dp)
          )
              Image(painter = painterResource(R.drawable.down_black), contentDescription = "",Modifier.size(12.dp))

          }

       }

           Box(

           ) {
               // Icon túi
               Image(
                   painter = painterResource(R.drawable.bag_black),
                   contentDescription = "cart",
                   modifier = Modifier.align(Alignment.Center).size(48.dp).padding(top = 8.dp, end = 8.dp)
               )

               // ✅ Badge đỏ
               Box(
                   modifier = Modifier
                       .size(20.dp)
                       .align(Alignment.TopEnd)
                       .offset(x = (-5).dp, y = 5.dp) // ✅ đẩy nhẹ vào trong
                       .background(FFD91E18, shape = CircleShape),
                   contentAlignment = Alignment.Center
               ) {
                   Text(
                       text = "15",
                       style = TextStyle(
                           fontSize = 9.sp,
                           lineHeight = 9.sp,
                           fontFamily = FontFamily(Font(R.font.inter)),
                           fontWeight = FontWeight.SemiBold,
                           color = Color.White,
                           textAlign = TextAlign.Center
                       ),
                       maxLines = 1,
                       modifier = Modifier.wrapContentSize(Alignment.Center)
                   )
               }

           }

       }


}
