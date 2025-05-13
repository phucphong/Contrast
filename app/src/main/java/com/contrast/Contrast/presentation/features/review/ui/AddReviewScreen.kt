package com.contrast.Contrast.presentation.features.review.ui

import android.net.Uri
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.media.ImageFullDialog
import com.contrast.Contrast.presentation.components.media.SelectedMediaGrid
import com.contrast.Contrast.presentation.components.media.VideoPlayerDialog

import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.toast.CustomToast
import com.contrast.Contrast.presentation.components.toast.toastCollect
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave

import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.ReviewRoutes
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.itechpro.domain.model.ToastPosition

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReviewScreen(
    navHostController: NavHostController,
    id: String,
    idUnit: String,
    fileTxt: String,
    name: String,
    viewModel: ReviewViewModel = hiltViewModel(),
    mediaCreateViewModel: MediaCreateViewModel = hiltViewModel()
) {

    val selectedMedia by mediaCreateViewModel.selectedMedia.collectAsState() // 🔥 List<CustomMedia>
    val validateMessage by mediaCreateViewModel.validateMessage.collectAsState()

    val imageSelect by mediaCreateViewModel.imageSelect.collectAsState()
    val videoSelect by mediaCreateViewModel.videoSelect.collectAsState()
    val totalSizeInMB by mediaCreateViewModel.totalSizeInMB.collectAsState()
    val fileUploadList by mediaCreateViewModel.fileUploadList.collectAsState()

    var isRegisterButton by remember { mutableStateOf(false) }
    val rating by viewModel.rating.collectAsState()
    val noteRating by viewModel.noteRating.collectAsState()
    var content by remember { mutableStateOf("") }
    var fullUrl by remember { mutableStateOf("") }
    var rank by remember { mutableStateOf("5") }
    var showDialog by remember { mutableStateOf(false) }


    var showDialogImageVideo by remember { mutableStateOf(false) }
    var isVideo by remember { mutableStateOf<Boolean>(false) }
    var pendingNavigation by remember { mutableStateOf<String?>(null) }
    var toastMessage by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }
    var showToastTotalMaxFile by remember { mutableStateOf(false) }

// Thu thập từ nhiều ViewModel
    viewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }
    mediaCreateViewModel.notificationToast.toastCollect {
        if (!showToastTotalMaxFile) {
            toastMessage = it
            showToastTotalMaxFile = true
        }
    }

    LaunchedEffect(fileUploadList) {
       if(fileUploadList.size>0){
           viewModel.uploadReviewFile(id,idUnit, rank,content, fileUploadList)
       }
    }

    if (showDialog) {
        if (validateMessage.isNotEmpty()) {
            CustomOkAlertDialog(
                message = validateMessage,
                onDismiss = {
                    showDialog = false
                    mediaCreateViewModel.clearValidationError()

                }
            )


        } else {
            val parentRouteRaw = "add_review/$id/$idUnit/${Uri.encode(fileTxt)}/${Uri.encode(name)}"
            val encodedParentRoute = Uri.encode(parentRouteRaw)

            when (pendingNavigation) {
                "image" -> navHostController.navigate(
                    ReviewRoutes.MediaPicker.withArgs(
                        maxCount = imageSelect,
                        allowImage = true,
                        allowVideo = false,
                        compressedFiles = true,
                        parentRoute = encodedParentRoute

                    )
                )
                "video" -> navHostController.navigate(
                    ReviewRoutes.MediaPicker.withArgs(
                        maxCount = videoSelect,
                        allowImage = false,
                        allowVideo = true,
                        compressedFiles = true,
                        parentRoute = encodedParentRoute
                    )
                )
            }
            showDialog = false
        }
    }
   Box {


       Column {
           CustomTopAppBarBackTitleSave(
               title = stringResource(R.string.review_product),
               painter = painterResource(id = R.drawable.quaylai),
               fontSize = 14.sp,
               onBackClick = { navHostController.popBackStack() },
               onSaveClick = {

                   if(selectedMedia.size>0){
                       mediaCreateViewModel.getTotalMediaSize(selectedMedia)
                   }else{
                       viewModel.uploadReview(id,idUnit,rating.toString(),content)
                   }

               },
           )

           CustomDividerColor()

           Row(
               modifier = Modifier.padding(10.dp),
               horizontalArrangement = Arrangement.spacedBy(16.dp),
               verticalAlignment = Alignment.CenterVertically
           ) {
               NetworkImage(
                   model = fileTxt,
                   contentDescription = null,
                   contentScale = ContentScale.Fit,
                   modifier = Modifier.size(60.dp).padding(10.dp)
               )
               CustomText(name, fontSize = 12.sp, color = FF7C7C7C)
           }

           CustomDividerColor()

           Row(
               modifier = Modifier.padding(16.dp),
               horizontalArrangement = Arrangement.spacedBy(16.dp)
           ) {
               DashedBoxButton(icon = Icons.Default.PhotoCamera, label = "Camera", onClick = {
                   selectedMedia.map { it.uri }
                       ?.let { mediaCreateViewModel.checkMediaLimit(it, "image") }
                   pendingNavigation = "image"
                   showDialog = true
               })
               DashedBoxButton(icon = Icons.Default.Videocam, label = "Video", onClick = {
                   selectedMedia.map { it.uri }
                       ?.let { mediaCreateViewModel.checkMediaLimit(it, "video") }
                   pendingNavigation = "video"
                   showDialog = true
               })
           }

           SelectedMediaGrid(
               mediaList = selectedMedia,
               onClickItem = { media ->
                   showDialogImageVideo = true
                   fullUrl = media.uri.toString()
                   isVideo = media.isVideo
               },
               onRemoveItem = { media ->
                   mediaCreateViewModel.removeMedia(media)
               }
           )


           CustomTextField(
               modifier = Modifier.heightIn(120.dp).padding(10.dp),
               value = content,
               onValueChange = { content = it },
               placeholder = stringResource(id = R.string.write_feedback_you),
               keyboardType = KeyboardType.Text,
               imeAction =  ImeAction.Done,
               keyboardActions = KeyboardActions(
                   onSend = {
                       if(selectedMedia.size>0){
                           mediaCreateViewModel.getTotalMediaSize(selectedMedia)
                       }else{
                           viewModel.uploadReview(id,idUnit,rating.toString(),content)
                       }
                   }
               )
           )

           Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
               Text(text = stringResource(R.string.rank_review), fontSize = 14.sp, color = Color.Black)
               Row(
                   horizontalArrangement = Arrangement.spacedBy(4.dp),
                   modifier = Modifier.padding(horizontal = 10.dp)
               ) {
                   (1..5).forEach { star ->
                       Icon(
                           imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                           contentDescription = "Star $star",
                           tint = if (star <= rating) Color.Black else Color.Gray,
                           modifier = Modifier
                               .size(16.dp)
                               .noRippleClickableComposable { viewModel.setRatingNote(star) }
                       )
                   }
               }
               Text(text = noteRating, fontSize = 12.sp, color = FF7C7C7C)
           }
       }

       if (showToast) {
           CustomToast(
               message =toastMessage ,
               textAlign = TextAlign.Center,
               background = FFFFFFFF,
               textColor = Color.Black,
               showToast = true,
               toastPosition = ToastPosition.CENTER,
               durationMillis= 2000,
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

       }
       if (showToastTotalMaxFile) {
           CustomToast(
               message =toastMessage ,
               textAlign = TextAlign.Center,
               background = FFFFFFFF,
               textColor = Color.Black,
               showToast = true,
               toastPosition = ToastPosition.CENTER,
               durationMillis= 2000,
               onDismiss = {
                   showToast = false

               },
               modifier = Modifier
                   .padding(horizontal = 50.dp)
                   .wrapContentHeight()
                   .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp)) // ✅ Đổ bóng
                   .clip(RoundedCornerShape(8.dp)) // ✅ Bo góc sau khi đổ bóng
                   .background(FFFFFFFF) // ✅ Bắt buộc: set lại màu nền sau khi clip
                   .border(1.dp, FFFFFFFF, RoundedCornerShape(8.dp))

           )

       }


       if (showDialogImageVideo) {
           if(!isVideo){
               ImageFullDialog(
                   imageUrl = fullUrl,
                   local = true,
                   onDismiss = { showDialogImageVideo = false },
                   onDownloadClick={

                   }
               )}else {
               VideoPlayerDialog(
                   videoUrl = fullUrl, // hoặc "file:///storage/emulated/0/Download/video.mp4"
                   local = true, // true nếu là local file
                   onDismiss = { showDialogImageVideo = false }
               )
           }

       }


   }
}
