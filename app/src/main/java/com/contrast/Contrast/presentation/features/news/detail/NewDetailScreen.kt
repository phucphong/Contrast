package com.contrast.Contrast.presentation.features.news.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatToDDMYYYYHHMM
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.components.webview.WebView
import com.contrast.Contrast.presentation.features.news.viewModel.NewsViewModel

@Preview(showBackground = true)
@Composable
fun NewDetailScreen(ido:String,viewModel: NewsViewModel = hiltViewModel(), navController: NavController) {
    val obj by viewModel.obj.collectAsState()


    val domain by viewModel.domain.collectAsState()

    LaunchedEffect(ido) {
        if(ido!="0"){
            viewModel.getNewDetail(ido)
        }

    }

    Column {
        CustomTopAppBarBackTitle (
            title = stringResource(id = R.string.new_detail),
            Color.Red,
            onBackClick = { navController.popBackStack() }
        )
        val fullUrl = (domain?.trimEnd('/') ?: "") + (obj?.filetxt ?:"" )
//        val fullUrl = "https://images2.thanhnien.vn/528068263637045248/2023/12/4/mai-han-c-17016869677251382812401.jpg"

        Log.d("IMAGE_URL", fullUrl)

     Column ( modifier = Modifier
         .fillMaxSize()
         .background(Color.White)

         .verticalScroll(rememberScrollState())){
         AsyncImage(
             model = fullUrl,
             contentDescription = null,
             modifier = Modifier
                 .height(250.dp).padding(5.dp).fillMaxWidth()
             ,
             contentScale = ContentScale.Crop
         )

         Row(
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.SpaceBetween,
             modifier = Modifier.fillMaxWidth()
         ) {

             val  createDate =obj?.cd?:""
             Text(
                 formatToDDMYYYYHHMM(createDate), fontSize = 12.sp, color = Color.Gray
                 , modifier = Modifier
                     .fillMaxWidth()
                     .padding(bottom = 5.dp)
                     .weight(1f))


             Row(verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.padding( 10.dp)) {
                 Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                 Spacer(modifier = Modifier.width(4.dp))

                 val count = obj?.soluongdoc?:0
                if(count>0){
                    Text(count.toString(), fontSize = 12.sp, color = Color.Gray)
                }

             }
         }


         WebView(htmlContent = obj?.noidung ?:"")
     }
    }


}
