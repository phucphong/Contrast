package com.contrast.Contrast.presentation.features.main.store.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.slider.ImageSlider

@Preview(showBackground = true)
@Composable
fun StoreDetailScreen() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        // Header Image
        ImageSlider()

        // Content Card
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.store_name),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Address
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.location_store),
                        contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.store_address))
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Hotline
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0333687462"))
                    context.startActivity(intent)
                }) {
                    Image(painter = painterResource(id = R.drawable.call), contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "0333687462", color = Color.Blue)
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Notification
                Text(text = stringResource(id = R.string.store_note))
                Spacer(modifier = Modifier.height(8.dp))

                // Open Hours
                Card(
                    backgroundColor = Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = stringResource(id = R.string.store_open), fontWeight = FontWeight.Bold, color = Color.Red)
                            Text(text = stringResource(id = R.string.store_open_24))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = { /* TODO: Handle report */ }) {
                        Text(text = stringResource(id = R.string.report))
                    }
                    Button(onClick = { /* TODO: Handle share */ }) {
                        Text(text = stringResource(id = R.string.share))
                    }
                }
            }
        }
    }
}
