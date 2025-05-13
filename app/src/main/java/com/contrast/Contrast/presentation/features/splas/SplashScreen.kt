package com.contrast.Contrast.presentation.features.splas



import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.navigator.router.NavRoutes

import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(

    navHostController: NavHostController,
    viewModel: SplashViewModel, startIntent: Intent?
) {

    val navEvent by viewModel.navigationEvent.collectAsState()
    // Bắt đầu delay và xử lý luồng Splash
    LaunchedEffect(Unit) {
        delay(1500) // Delay splash giống activity cũ

        if (startIntent?.action == Intent.ACTION_VIEW) {
            viewModel.handleShareIntent(startIntent)
        } else {
            viewModel.checkLoginState()
        }
    }

    // Xử lý navigation event
    LaunchedEffect(navEvent) {
        when (val event = navEvent) {
                is SplashNaEvent.GoToLogIn -> {
                    navHostController.navigate(NavRoutes.Login.route)
                     {
                        popUpTo("splash") { inclusive = true }
                    }
                }
                is SplashNaEvent.GoToMain -> {
                    navHostController.navigate(
                        NavRoutes.Main.withArgs(
                            id = event.id,
                            idUnit = event.idUnit,
                            introducerId = event.introducerId
                        )
                    )
                }


                else -> Unit

        }
    }

    // UI của splash screen (logo, màu nền, animation nếu cần)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center
    ) {
     Image(painter = painterResource(R.drawable.logo), contentDescription = "", modifier = Modifier.padding(20.dp))
    }
}
