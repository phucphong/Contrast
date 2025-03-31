package com.contrast.Contrast.presentation.features.store.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color


import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign


import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun StoreSelectionDialog(
    stores: List<Store>,
    selectedStoreId: String?,
    onSelectStore: (Store) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .navigationBarsPadding(), // 👉 tránh che thanh điều hướng
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .navigationBarsPadding()

                ) {

                   Row (modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){

                       Image(
                           painter = painterResource(id = R.drawable.back),
                           contentDescription = "image description",
                           contentScale = ContentScale.None,
                           modifier = Modifier.size(30.dp).noRippleClickableComposable { onDismiss }
                       )
                       // Tiêu đề
                       Text(
                           text = stringResource(R.string.choose_store_pickup),
                           color = Color(0xFFD91E18),
                           fontSize = 16.sp,
                           fontWeight = FontWeight.Bold,
                           modifier = Modifier
                               .fillMaxWidth()
                             ,
                           textAlign = TextAlign.Center
                       )
                   }

                    // Search
                    var searchText by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            Text("Tìm kiếm cửa hàng", fontSize = 14.sp, color = Color(0xFF9E9E9E))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                tint = Color(0xFF9E9E9E)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    )

                    val filtered = stores.filter {
                        it.name.contains(searchText, ignoreCase = true) ||
                                it.address.contains(searchText, ignoreCase = true)
                    }

                    LazyColumn(modifier = Modifier.fillMaxHeight(0.6f)) {
                        items(filtered) { store ->
                            StoreItem(
                                store = store,
                                isSelected = store.id == selectedStoreId,
                                onClick = { onSelectStore(store) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoreItem(
    store: Store,
    isSelected: Boolean,
    onClick: () -> Unit
) {
Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Column(modifier = Modifier.weight(1f)) {


            Text(
                text = store.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2F2E2E),
                )
            )


            Text(
                text = store.address,
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF424242),
                ),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        // Icon vị trí
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isSelected) Color(0xFF212121) else Color(0xFFE0E0E0),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
    CustomDividerColor()

}

}

data class Store(
    val id: String,
    val name: String,
    val address: String
)
