package com.contrast.Contrast.presentation.features.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.itechpro.domain.model.ShareOption

@Composable
fun ShareDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onShareClick: (ShareOption) -> Unit
) {
    if (visible) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Chia sẻ sản phẩm",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ShareOptionItem(
                                iconRes = R.drawable.zalo,
                                title = "Zalo",
                                onClick = { onShareClick(ShareOption.Zalo) }
                            )
                            ShareOptionItem(
                                iconRes = R.drawable.facebook,
                                title = "Facebook",
                                onClick = { onShareClick(ShareOption.Facebook) }
                            )
                            ShareOptionItem(
                                iconRes = R.drawable.copy_link,
                                title = "Copy link",
                                onClick = { onShareClick(ShareOption.CopyLink) }
                            )
                        }
                    }
                }
            }
        }
    }
}
