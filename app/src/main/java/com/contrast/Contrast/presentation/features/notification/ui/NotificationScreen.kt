package com.contrast.Contrast.presentation.features.notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.core.GrayD7
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack


import com.itechpro.domain.model.sampleNotifications

@Composable
fun NotificationScreen(onBackPress: () -> Unit) {
    val notifications = remember { sampleNotifications() }

    Scaffold(
        topBar = {
            CustomTopAppBarTittleBack(
                title = stringResource(id = R.string.notification_title),
                Color.Red,
                GrayD7,
                onBackClick = { onBackPress }
            )
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).
                background(GrayD7),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications, key = { it.id }) { notification ->
                NotificationItem(notification)
            }
        }
    }
}