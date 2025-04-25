package com.contrast.Contrast.presentation.components.image

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatDuration
import com.contrast.Contrast.extensions.isLimitedAccessGranted
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.theme.FF037BFF
import com.contrast.Contrast.presentation.theme.FF0967DF

enum class MediaPermissionStatus {
    GRANTED_FULL,
    GRANTED_LIMITED,
    DENIED
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaPickerScreenNew(
    navHostController: NavHostController,
    maxCount: Int = 5,
    allowImage: Boolean = true,
    allowVideo: Boolean = true,
    compressedFiles: Boolean = true,
    onSendClick: (List<Uri>) -> Unit,
    viewModel: MediaPickerViewModelNew = hiltViewModel(),
) {
    val context = LocalContext.current
    val mediaItems by viewModel.mediaItems.collectAsState()
    val selectedUris by viewModel.selectedUris.collectAsState()
    val gridState = rememberLazyGridState()

    var permissionStatus by remember { mutableStateOf(MediaPermissionStatus.DENIED) }

    val selectedUrisLimited = remember { mutableStateListOf<Uri>() }



    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val hasMediaPermission = permissions.entries.any {
            it.key == Manifest.permission.READ_MEDIA_IMAGES ||
                    it.key == Manifest.permission.READ_MEDIA_VIDEO
        }

        val granted = permissions.values.any { it } || isLimitedAccessGranted(context)

        if (granted) {
            viewModel.loadMedia(allowImage, allowVideo)
        }
    }

    LaunchedEffect(Unit) {
        val hasLimited = isLimitedAccessGranted(context)

        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            buildList {
                if (allowImage) add(Manifest.permission.READ_MEDIA_IMAGES)
                if (allowVideo) add(Manifest.permission.READ_MEDIA_VIDEO)
            }.toTypedArray()
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        permissionStatus = when {
            allGranted -> MediaPermissionStatus.GRANTED_FULL
            hasLimited -> MediaPermissionStatus.GRANTED_LIMITED
            else -> MediaPermissionStatus.DENIED
        }

        if (permissionStatus != MediaPermissionStatus.DENIED) {
            viewModel.loadMedia(allowImage, allowVideo)
        } else {
            permissionLauncher.launch(permissions)
        }
    }

    LaunchedEffect(Unit) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            buildList {
                if (allowImage) add(Manifest.permission.READ_MEDIA_IMAGES)
                if (allowVideo) add(Manifest.permission.READ_MEDIA_VIDEO)
            }.toTypedArray()
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (allGranted) {
            viewModel.loadMedia(allowImage, allowVideo)
        } else {
            permissionLauncher.launch(permissions)
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val hasLimited = isLimitedAccessGranted(context)
                val allGranted = listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                ).all {
                    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }

                permissionStatus = when {
                    allGranted -> MediaPermissionStatus.GRANTED_FULL
                    hasLimited -> MediaPermissionStatus.GRANTED_LIMITED
                    else -> MediaPermissionStatus.DENIED
                }

                if (permissionStatus != MediaPermissionStatus.DENIED) {
                    viewModel.loadMedia(allowImage, allowVideo)
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
            tint =  Color.Black,
            textColor =  Color.Black,
            fontSize =  14.sp,

            painter =painterResource(id = R.drawable.ic_close),
            onBackPress = {navHostController.popBackStack() }
        )

        if (permissionStatus == MediaPermissionStatus.GRANTED_LIMITED) {
            LimitedAccessBanner(appName = "Contrast", onPickMorePhotos = {

                val intent = Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                    putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 20)
                }
                context.startActivity(intent)
            },)
        }


        Box(contentAlignment = Alignment.BottomCenter) {
            val displayUris = if (mediaItems.isNotEmpty()) mediaItems.map { it.uri } else selectedUrisLimited

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(displayUris) { uri ->
                    val selected = selectedUris.contains(uri)
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable {
                                if (maxCount > 0 && !selected && selectedUris.size >= maxCount) return@clickable
                                viewModel.toggleSelect(uri)
                            }
                    ) {
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

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

            val allSelectedUris = (selectedUris + selectedUrisLimited).distinct()

            CustomButton(
                text = if(maxCount>0)"${stringResource(R.string.select)} (${allSelectedUris.size}/$maxCount)" else "${stringResource(R.string.select)} (${allSelectedUris.size})",
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                enabled = allSelectedUris.isNotEmpty(),
                textColor = if (allSelectedUris.isNotEmpty()) Color.White else Color.Black,
                containerColor = FF0967DF,
                roundedCornerShape = 10.dp,
                onClick = {
                    onSendClick(allSelectedUris)

                }
            )
        }
    }
}

