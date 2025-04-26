package com.contrast.Contrast.presentation.features.review.ui

import android.os.Build
import android.util.Log

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertDialog
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave

import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FF7C7C7C


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReviewScreen(
    navHostController: NavHostController,
    id: String,
    fileTxt: String,
    name: String,
    viewModel: ReviewViewModel = hiltViewModel(),
    mediaCreateViewModel: MediaCreateViewModel = hiltViewModel()
) {
    val input by viewModel.reviewInput.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val context = LocalContext.current
    val selectedMedia by mediaCreateViewModel.selectedMedia.collectAsState()
    val validateMessage by mediaCreateViewModel.validateMessage.collectAsState()
    val typeAttach by mediaCreateViewModel.typeAttach.collectAsState()
    val imageSelect by mediaCreateViewModel.imageSelect.collectAsState()
    val videoSelect by mediaCreateViewModel.videoSelect.collectAsState()

    var isRegisterButton by remember { mutableStateOf(false) }
    val rating by viewModel.rating.collectAsState()
    val noteRating by viewModel.noteRating.collectAsState()
    var note by remember { mutableStateOf("") }

    val gridState = rememberLazyGridState()
    var showDialog by remember { mutableStateOf(false) }
    var pendingNavigation by remember { mutableStateOf<String?>(null) }
    if (showDialog) {
        if (validateMessage.isNotEmpty()) {
            CustomAlertDialog(
                message = validateMessage,
                onDismiss = {
                    showDialog = false
                    mediaCreateViewModel.clearValidationError()
                    mediaCreateViewModel.typeAttach("")
                }
            )
        } else {
            when (pendingNavigation) {
                "image" -> navHostController.navigate(
                    NavRoutes.MediaPicker.withArgs(
                        maxCount = imageSelect,
                        allowImage = true,
                        allowVideo = false,
                        compressedFiles = true,
                    )
                )

                "video" -> navHostController.navigate(
                    NavRoutes.MediaPicker.withArgs(
                        maxCount = videoSelect,
                        allowImage = false,
                        allowVideo = true,
                        compressedFiles = true,
                    )
                )
            }
            showDialog = false
        }


    }

    Column {
        CustomTopAppBarBackTitleSave(
            title = stringResource(R.string.review_product),
            painter = painterResource(id = R.drawable.quaylai),
            fontSize = 14.sp,
            onBackClick = { navHostController.popBackStack() },
            onSaveClick = {

                viewModel.reviewInput
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
                contentScale = ContentScale.Fit, // hoặc ContentScale.Inside
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp) // ✅ full cả chiều ngang + cao


            )

            CustomText(name, fontSize = 12.sp, color = FF7C7C7C)

        }
        CustomDividerColor()


        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashedBoxButton(icon = Icons.Default.PhotoCamera, label = "Camera", onClick = {

                mediaCreateViewModel.checkMediaLimit(selectedMedia, "image")
                pendingNavigation = "image"
                showDialog = true

            })

            DashedBoxButton(icon = Icons.Default.Videocam, label = "Video", onClick = {
                mediaCreateViewModel.checkMediaLimit(selectedMedia, "video")
                pendingNavigation = "video"
                showDialog = true
            })
        }


        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 10.dp)
        ) {
            items(selectedMedia) { uri ->
                val selected = selectedMedia.contains(uri)

                Box(
                    modifier = Modifier.aspectRatio(1f)
                ) {
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Phần kiểm tra: Nếu uri là video thì vẽ Icon Video
                    val context = LocalContext.current
                    val mimeType = remember(uri) {
                        context.contentResolver.getType(uri).orEmpty()
                    }
                    if (mimeType.startsWith("video")) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Videocam,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    if (selected) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(20.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(16.dp)
                                    .noRippleClickableComposable {
                                        mediaCreateViewModel.removeMedia(uri)
                                    }
                            )
                        }
                    }
                }
            }
        }



        CustomTextField(

            modifier = Modifier
                .heightIn(120.dp)
                .padding(10.dp),

            value = note,
            onValueChange = { note = it },
            placeholder = stringResource(id = R.string.write_feedback_you),
            keyboardType = KeyboardType.Text
        )



        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.rank_review), fontSize = 14.sp, color = Color.Black)

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                (1..5).forEach { star ->
                    Icon(imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star $star",
                        tint = if (star <= rating) Color.Black else Color.Gray, // vàng & xám
                        modifier = Modifier
                            .size(16.dp)
                            .noRippleClickableComposable { viewModel.setRatingNote(star) })


                }
            }
            Text(text = noteRating, fontSize = 12.sp, color = FF7C7C7C)

        }


    }
}
