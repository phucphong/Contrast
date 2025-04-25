package com.contrast.Contrast.presentation.features.review.ui
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.image.NetworkImage
import com.contrast.Contrast.presentation.components.image.ReviewCreateViewModel
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave

import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FF7C7C7C


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReviewScreen(navHostController: NavHostController, id: String, fileTxt: String, name: String, viewModel: ReviewViewModel = hiltViewModel(), reviewCreateViewModel: ReviewCreateViewModel = hiltViewModel()) {
    val input by viewModel.reviewInput.collectAsState()
    val reviews by viewModel.reviews.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val context = LocalContext.current



    val rating by viewModel.rating.collectAsState()
    val noteRating by viewModel.noteRating.collectAsState()
    var note by remember { mutableStateOf("") }


    Column{
        CustomTopAppBarBackTitleSave(
            title = stringResource(R.string.evaluate_product),
            painter=painterResource(id = R.drawable.quaylai),
            fontSize = 14.sp,
            onBackClick = {navHostController.popBackStack() },
            onSaveClick = { },
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
                    .size(60.dp).padding(10.dp) // ✅ full cả chiều ngang + cao


            )

            CustomText(name, fontSize = 12.sp, color = FF7C7C7C)

        }
        CustomDividerColor()


        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashedBoxButton(
                icon = Icons.Default.PhotoCamera,
                label = "Camera",
                onClick = {



                    navHostController.navigate(
                        NavRoutes.MediaPicker.withArgs(
                            maxCount = 5,
                            allowImage = true,
                            allowVideo = true,
                            compressedFiles = true,
                        )
                    )


                }
            )


            DashedBoxButton(
                icon = Icons.Default.Videocam,
                label = "Video",
                onClick = {     navHostController.navigate(
                    NavRoutes.MediaPicker.withArgs(
                        maxCount = 1,
                        allowImage = false,
                        allowVideo = true,
                        compressedFiles = true,
                    )
                ) }
            )
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(reviews) { item ->
                ReviewItem(
                    evaluate = item,
                    domain = domain,
                    onDownloadClick = { fileUrl ->
                        // onDownloadClick(fileUrl)
                    }
                )
            }
        }


        CustomTextField(

            modifier=Modifier.heightIn(120.dp).padding(horizontal = 10.dp),

            value = note,
            onValueChange = { note = it },
            placeholder = stringResource(id = R.string.write_feedback_you),
            keyboardType = KeyboardType.Text
        )



       Row (modifier = Modifier.padding( 10.dp), verticalAlignment = Alignment.CenterVertically){
           Text(text = stringResource(R.string.rank_evaluate), fontSize = 16.sp, color = Color.Black)

           Row(
               horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(horizontal = 10.dp)
           ) {
               (1..5).forEach { star ->
                   Icon(
                       imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                       contentDescription = "Star $star",
                       tint = if (star <= rating) Color.Black else Color.Gray, // vàng & xám
                       modifier = Modifier
                           .size(16.dp)
                           .clickable { viewModel.setRatingNote(star) }
                   )
               }
           }
          Text(text =noteRating , fontSize = 16.sp, color = FF7C7C7C)

       }


    }
}
