package com.contrast.Contrast.presentation.components.swipeDelete

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import kotlinx.coroutines.launch

@Composable
fun <T> SwipeRevealItem(
    item: T,
    modifier: Modifier = Modifier,
    paddingTop: Dp = 5.dp,
    paddingBottom: Dp = 5.dp,
    isOpen: Boolean,
    onSwipeStart: (T) -> Unit,
    onDeleteClick:  (T) -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val deleteWidth = 100.dp
    val deleteWidthPx = with(LocalDensity.current) { deleteWidth.toPx() }

    var contentHeight by remember { mutableStateOf(0) }

    // Nếu isOpen false → tự động đóng lại
    LaunchedEffect(isOpen) {
        if (!isOpen && swipeOffset.value != 0f) {
            swipeOffset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val dragModifier = Modifier
            .offset { IntOffset(swipeOffset.value.toInt(), 0) }
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        val newOffset = (swipeOffset.value + dragAmount).coerceIn(-deleteWidthPx, 0f)
                        scope.launch {
                            swipeOffset.snapTo(newOffset)
                        }

                        if (dragAmount < 0) {
                            onSwipeStart(item) // thông báo item đang vuốt
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            if (swipeOffset.value <= -deleteWidthPx / 2) {
                                swipeOffset.animateTo(-deleteWidthPx)
                            } else {
                                swipeOffset.animateTo(0f)
                            }
                        }
                    }
                )
            }

        // Nội dung chính
        Box(
            modifier = dragModifier
                .onGloballyPositioned {
                    contentHeight = it.size.height
                }
        ) {
            content(Modifier)
        }

        // Nút Delete chỉ hiển thị khi đang mở
        if (swipeOffset.value < 0f && contentHeight > 0) {
            val deleteHeight = with(LocalDensity.current) {
                contentHeight.toDp() - paddingTop - paddingBottom
            }

            Box(
                modifier = Modifier
                    .height(deleteHeight)
                    .fillMaxWidth()
                    .align(Alignment.TopEnd)
                    .padding(top = paddingTop),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .width(deleteWidth)
                        .fillMaxHeight()
                        .background(Color.Red)
                        .noRippleClickableComposable { onDeleteClick(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
