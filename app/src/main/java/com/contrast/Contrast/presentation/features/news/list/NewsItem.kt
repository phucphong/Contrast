package com.contrast.Contrast.presentation.features.news.list

import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.extensions.formatToDDMYYYYHHMM
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.contrast.Contrast.presentation.theme.TealGreen

import com.itechpro.domain.model.news.News

@Preview(showBackground = true)
@Composable
fun NewsItem(article: News, domain:String,  onClickNew: () -> Unit = {}) {


  Column {   Row(
      modifier = Modifier
          .fillMaxWidth() .padding(12.dp)

  ) {
      val fullUrl = domain.trimEnd('/') + article.filetxt

      NetworkImage(
          model = fullUrl,
          modifier = Modifier.size(80.dp).padding(5.dp)
      )

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f).noRippleClickableComposable { onClickNew() }) {
          CustomText(article.ten, fontWeight = FontWeight.Bold, fontSize = 14.sp,  color = TealGreen)
          CustomText(article.nguoidang, fontSize = 12.sp, color = Color.Gray)
          Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
          ) {
             Text(formatToDDMYYYYHHMM(article.cd), fontSize = 12.sp, color = Color.Gray
                 , modifier = Modifier
                     .fillMaxWidth()
                     .padding(bottom = 5.dp)
                     .weight(1f))


              Row(verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding( 10.dp)) {
                  Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  if(article.soluongdoc>0){
                      Text(article.soluongdoc.toString(), fontSize = 12.sp, color = Color.Gray)
                  }

              }
          }


      }
  }

      CustomDividerColor() }
}
