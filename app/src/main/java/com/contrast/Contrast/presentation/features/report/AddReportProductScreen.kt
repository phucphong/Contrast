package com.contrast.Contrast.presentation.features.report


import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.line.CustomDividerColor

import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.toast.CustomToast
import com.contrast.Contrast.presentation.components.toast.toastCollect

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.product.viewmodel.ProductViewModel

import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.UltraLightGray
import com.itechpro.domain.model.ToastPosition


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReportProductScreen(
    navHostController: NavHostController,
    id: String,
    idUnit: String,
    fileTxt: String,
    name: String,
    viewModel: ProductViewModel = hiltViewModel()

) {
    val typeReports by viewModel.typeReports.collectAsState()
    var selectedReason by remember { mutableStateOf<String?>(null) }
    var toastMessage by remember { mutableStateOf("") }
    var reasonName by remember { mutableStateOf("") }
    var reasonNote by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }

    viewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }
    LaunchedEffect(Unit) {

        viewModel.getTypeReport()
    }
 Box {
     Column(modifier = Modifier.background(UltraLightGray)) {
     CustomTopAppBarBackTitleSave(
         title = stringResource(R.string.report_product),
         painter = painterResource(id = R.drawable.quaylai),
         fontSize = 14.sp,
         text = stringResource(R.string.send),
         isSave = selectedReason != null, // chỉ bật khi chọn lý do
         onBackClick = { navHostController.popBackStack() },
         onSaveClick = {
             viewModel.addEditLikeReport("/ex/api_Sanpham/addbaocao",id, idUnit ,"report",selectedReason?:"", note)

         }
     )
     CustomDividerColor()
     if(selectedReason==null){
         Spacer(modifier = Modifier.height(8.dp))

         Text(
             text = "CHỌN LÝ DO",
             color = Color.Gray,
             fontSize = 13.sp,
             modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
         )

         LazyColumn(modifier = Modifier.fillMaxSize()) {
             items(typeReports) { reason ->
                 Column ( modifier = Modifier.background(Color.White)){
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .noRippleClickableComposable {
                                 selectedReason = reason.id?:""
                                 reasonName = reason.ten?:""
                                 reasonNote = reason.mota?:""
                             }
                             .padding(vertical = 16.dp, horizontal = 16.dp),
                         horizontalArrangement = Arrangement.SpaceBetween,
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         Text(
                             text = reason.ten?:"",
                             fontSize = 15.sp,
                             color = Color.Black,
                                     modifier = Modifier.weight(1f)
                         )
                         Image(
                             painter = painterResource(R.drawable.arrowright),
                             contentDescription = null,
                             colorFilter = ColorFilter.tint(TealGreen),
                             modifier = Modifier.size(18.dp)
                         )
                     }
                     CustomDividerColor()
                 }
             }
         }
     }else{
       Column (Modifier.fillMaxSize()){
           Text(
               text = reasonName,
               color = Color.Black,
               fontSize = 13.sp,
               modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
           )
           Text(
               text = reasonNote,
               color = Color.Gray,
               fontSize = 13.sp,
               modifier = Modifier.padding(horizontal = 16.dp)
           )

           CustomTextField(
               modifier = Modifier.heightIn(120.dp).padding(10.dp),
               value = note,
               onValueChange = { note = it },
               placeholder = stringResource(id = R.string.content),
               keyboardType = KeyboardType.Text,
               imeAction =  ImeAction.Done,
               keyboardActions = KeyboardActions(
                   onSend = {
                       viewModel.addEditLikeReport("/ex/api_Sanpham/addbaocao",id, idUnit ,"report",selectedReason?:"", note)
                   }
               )
           )
       }
     }
 }

     if (showToast) {
         CustomToast(
             message = toastMessage,
             textAlign = TextAlign.Center,
             background = FFFFFFFF,
             textColor = Color.Black,
             showToast = true,
             toastPosition = ToastPosition.CENTER,
             durationMillis = 2000,
             onDismiss = {
                 showToast = false
                 navHostController.popBackStack()
             },
             modifier = Modifier
                 .padding(horizontal = 50.dp)
                 .wrapContentHeight()
                 .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp)) // ✅ Đổ bóng
                 .clip(RoundedCornerShape(8.dp)) // ✅ Bo góc sau khi đổ bóng
                 .background(FFFFFFFF) // ✅ Bắt buộc: set lại màu nền sau khi clip
                 .border(1.dp, FFFFFFFF, RoundedCornerShape(8.dp))

         )

     } }
}
