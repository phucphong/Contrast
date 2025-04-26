package com.contrast.Contrast.presentation.components.media

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.isLimitedAccessGranted
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.theme.FF0967DF
import com.itechpro.domain.model.media.CustomMedia

enum class MediaPermissionStatus { GRANTED_FULL, GRANTED_LIMITED, DENIED }

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaPickerScreenNew(
    navHostController: NavHostController,
    maxCount: Int = 5,
    allowImage: Boolean = true,
    allowVideo: Boolean = true,
    compressedFiles: Boolean = true,
    onSendClick: (List<CustomMedia>) -> Unit, // 🔥 trả về List<CustomMedia>
    viewModel: MediaPickerViewModelNew = hiltViewModel(),
) {
    val context = LocalContext.current
    val mediaItems by viewModel.mediaItems.collectAsState()
    val selectedMedia by viewModel.selectedMedia.collectAsState() // 🔥 lấy selectedMedia
    val gridState = rememberLazyGridState()

    var permissionStatus by remember { mutableStateOf(MediaPermissionStatus.DENIED) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.any { it } || isLimitedAccessGranted(context)
        if (granted) viewModel.loadMedia(allowImage, allowVideo)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val allGranted = checkAllPermissionsGranted(context, allowImage, allowVideo)
                permissionStatus = when {
                    allGranted -> MediaPermissionStatus.GRANTED_FULL
                    isLimitedAccessGranted(context) -> MediaPermissionStatus.GRANTED_LIMITED
                    else -> MediaPermissionStatus.DENIED
                }
                if (permissionStatus != MediaPermissionStatus.DENIED) {
                    viewModel.loadMedia(allowImage, allowVideo)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        val allGranted = checkAllPermissionsGranted(context, allowImage, allowVideo)
        if (allGranted || isLimitedAccessGranted(context)) {
            viewModel.loadMedia(allowImage, allowVideo)
        } else {
            permissionLauncher.launch(getPermissionsArray(allowImage, allowVideo))
        }
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisible ->
                if (lastVisible != null && lastVisible >= mediaItems.size - 6) {
                    viewModel.loadMore()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        CustomBackTitle(
            title = stringResource(R.string.all_image),
            tint = Color.Black,
            textColor = Color.Black,
            fontSize = 14.sp,
            painter = painterResource(id = R.drawable.ic_close),
            onBackPress = { navHostController.popBackStack() }
        )

        if (permissionStatus == MediaPermissionStatus.GRANTED_LIMITED) {
            LimitedAccessBanner(
                appName = "Contrast",
                onPickMorePhotos = {
                    val intent = Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                        putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 20)
                    }
                    context.startActivity(intent)
                }
            )
        }

        Box(contentAlignment = Alignment.BottomCenter) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(mediaItems) { item ->
                    val isSelected = selectedMedia.any { it.uri == item.uri }

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable {
                                if (maxCount > 0 && !isSelected && selectedMedia.size >= maxCount) return@clickable
                                viewModel.toggleSelect(item) // 🔥 toggle bằng MediaItem
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(item.uri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        if (item.isVideo) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = formatDuration(item.durationMs),
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(20.dp)
                                    .background(Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.Blue,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            CustomButton(
                text = buildString {
                    append(stringResource(R.string.select))
                    append(" (")
                    append(selectedMedia.size)
                    if (maxCount > 0) {
                        append("/$maxCount")
                    }
                    append(")")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                enabled = selectedMedia.isNotEmpty(),
                textColor = if (selectedMedia.isNotEmpty()) Color.White else Color.Black,
                containerColor = FF0967DF,
                roundedCornerShape = 10.dp,
                onClick = { onSendClick(selectedMedia) } // 🔥 trả List<CustomMedia>
            )
        }
    }
}

private fun checkAllPermissionsGranted(context: android.content.Context, allowImage: Boolean, allowVideo: Boolean): Boolean {
    val permissions = getPermissionsArray(allowImage, allowVideo)
    return permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
}

private fun getPermissionsArray(allowImage: Boolean, allowVideo: Boolean): Array<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        buildList {
            if (allowImage) add(Manifest.permission.READ_MEDIA_IMAGES)
            if (allowVideo) add(Manifest.permission.READ_MEDIA_VIDEO)
        }.toTypedArray()
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}

private fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
