package com.contrast.Contrast.presentation.components.countdownTimer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



@Composable
fun rememberCountdownTimer(
    endTime: String, // định dạng "dd/MM/yyyy HH:mm"
    onTick: (String) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val endDateTime = remember(endTime) { LocalDateTime.parse(endTime, formatter) }

    LaunchedEffect(endTime) {
        while (true) {
            val now = LocalDateTime.now()
            val duration = Duration.between(now, endDateTime)

            if (!duration.isNegative) {
                val days = duration.toDays()
                val hours = duration.toHours() % 24
                val minutes = duration.toMinutes() % 60
                val seconds = duration.seconds % 60

                val remainingTime = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds)
                onTick(remainingTime)
            } else {
                onTick("00:00:00:00")
                break
            }

            delay(1000)
        }
    }
}
