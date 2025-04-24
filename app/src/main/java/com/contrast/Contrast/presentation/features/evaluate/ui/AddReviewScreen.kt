package com.contrast.Contrast.presentation.features.evaluate.ui
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.image.ReviewCreateViewModel
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.ratingbar.RatingStars
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave

import com.contrast.Contrast.presentation.features.evaluate.EvaluateViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReviewScreen(navHostController: NavHostController, id: String, viewModel: EvaluateViewModel = hiltViewModel(), reviewCreateViewModel: ReviewCreateViewModel = hiltViewModel()) {
    val input by viewModel.reviewInput.collectAsState()
    val reviews by viewModel.reviews.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val context = LocalContext.current

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.addImage(it) }
    }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.setVideo(it) }
    }


    Column(modifier = Modifier.padding(16.dp)) {
        CustomTopAppBarBackTitleSave(
            title = stringResource(R.string.evaluate_product),
            backgroundColor = FAFAFA,
            fontSize = 14.sp,
            onBackClick = {navHostController.popBackStack() },
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashedBoxButton(
                icon = Icons.Default.PhotoCamera,
                label = "Camera",
                onClick = {

//                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                    navHostController.navigate(
                        NavRoutes.MediaPicker.withArgs(
                            maxCount = 5,
                            allowImage = true,
                            allowVideo = true
                        )
                    )


                }
            )

            DashedBoxButton(
                icon = Icons.Default.Videocam,
                label = "Video",
                onClick = {videoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)) }
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            reviews.forEach { item ->
                EvaluateItem(
                    evaluate = item,
                    domain = domain,
                    onDownloadClick = { fileUrl ->
//                        onDownloadClick(fileUrl)
                    }
                )
            }
        }


        CustomTextField(
            value = "",

            onValueChange = {

            },
            placeholder = stringResource(id = R.string.write_feedback_you),
            keyboardType = KeyboardType.Text
        )



        RatingStars(
            rating = 5,
            onRatingChanged = { viewModel.setRating(it) }
        )


    }
}
