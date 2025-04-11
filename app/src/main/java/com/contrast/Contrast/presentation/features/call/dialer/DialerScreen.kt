package com.contrast.Contrast.presentation.features.call.dialer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.contrast.Contrast.presentation.components.text.CustomText

import com.contrast.Contrast.presentation.components.bottomAction.BottomActionList

import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.contrast.Contrast.presentation.features.call.viewmodel.DialerViewModel
import com.itechpro.domain.model.navigationEvent.DialerNavigationEvent

@Preview(showBackground = true)
@Composable
fun DialerScreen(
                   viewModel: DialerViewModel,
                   navController: NavHostController
) {
    val dialPad = remember { viewModel.dialPad }
    val inputNumber by viewModel.inputNumber.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is DialerNavigationEvent.GoToContact -> {
                    navController.navigate("contact_screen")
                }
                is DialerNavigationEvent.GoToHistory -> {
                    navController.navigate("history_screen")
                }
                // v.v...
                DialerNavigationEvent.GoToContactPhone ->{
                    navController.navigate("contactphone_screen")
                }
                DialerNavigationEvent.GoToInternalCall -> {
                    navController.navigate("call_internal_screen")
                }
                DialerNavigationEvent.GoToSettings ->{
                    navController.navigate("settings_screen")
                    Log.e("navController","navController")
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top bar
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color(0xFF00BFA5), // màu xanh ngọc
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Gọi điện thoại",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        // Dialpad
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
CustomText( text = inputNumber, textAlign = TextAlign.Center, fontSize= 25.sp)

            dialPad.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { number ->
                        DialButton(number, viewModel)
                    }
                }
            }

            // Bottom row with call + contact + delete
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Add Contact",
                    tint = Color(0xFF64B5F6),
                    modifier = Modifier.size(36.dp).noRippleClickableComposable { viewModel.onAddContact() }
                )
                Spacer(modifier = Modifier.width(32.dp))
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00C853)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = "Delete",
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp).noRippleClickableComposable { viewModel.onBackspace() }
                )
            }
        }

        BottomActionList(
            actions = viewModel.bottomActions,
            onItemClick = { item ->
                viewModel.onBottomActionClick(item.id)
            }
        )


    }
}

@Composable
fun DialButton(text: String, viewModel :DialerViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .size(72.dp)
            .clip(CircleShape).noRippleClickableComposable { viewModel.onNumberClick(text)}
            .background(Color(0xFFF0F0F0))
    ) {
        Text(text = text, fontSize = 24.sp, fontWeight = FontWeight.Medium)
    }
}
