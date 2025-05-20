package com.contrast.Contrast.presentation.features.news.detail

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.buildHtmlFromBody

import com.contrast.Contrast.extensions.formatToDDMYYYYHHMM
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle

import com.contrast.Contrast.presentation.components.webview.HtmlContentWebView

import com.contrast.Contrast.presentation.features.news.viewModel.NewsViewModel


@Composable
fun NewDetailScreen(
    navHostController: NavHostController, id: String, viewModel: NewsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()


    LaunchedEffect(id) {
        if (id != "0") {
            viewModel.getNewDetail(id)
        }

    }

    if (state.errorMessage.isNotEmpty()) {
        CustomOkAlertDialog(message = state.errorMessage, onDismiss = {
            viewModel.clearErrorMessage()

        })
    }

    Column {

        CustomBackTitle(
            title = stringResource(R.string.new_detail),
            tint = Color.Black,
            textColor = Color.Black,
            fontSize = 12.sp,
            painter = painterResource(id = R.drawable.quaylai),
            onBackPress = { navHostController.popBackStack() }
        )

        val fullUrl = (state.domain?.trimEnd('/') ?: "") + (state.newDetail?.filetxt ?: "")


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {

            NetworkImage(
                model = fullUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .padding(5.dp)
                    .fillMaxWidth(),


            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                val createDate = state.newDetail?.cd ?: ""



                Text(
                    state.newDetail?.nguoidang ?: "",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier

                        .padding(start = 5.dp)
                )
                Text(
                    formatToDDMYYYYHHMM(createDate),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(
                        Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    val count = state.newDetail?.soluongdoc ?: 0
                    if (count > 0) {
                        Text(count.toString(), fontSize = 12.sp, color = Color.Gray)
                    }

                }
            }


            val htmlContent = state.newDetail?.noidung ?: ""
//
//            if(state.newDetail!=null){
//
//                ViewNewsScreen(news = state.newDetail!!, domain = state.domain ?: "")
//            }


            HtmlContentWebView(
                htmlContent = htmlContent,
                domain = state.domain ?: "",
            )
        }
    }


}

