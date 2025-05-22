package com.contrast.Contrast.presentation.features.share

import androidx.compose.foundation.layout.*

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun ParticipationItem(count: String, title: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text =count,

            style = MaterialTheme.typography.titleLarge.copy(
                color = color, fontWeight = FontWeight.Bold, fontSize = 12.sp
            )
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(color = color), fontSize = 12.sp
        )
    }
}