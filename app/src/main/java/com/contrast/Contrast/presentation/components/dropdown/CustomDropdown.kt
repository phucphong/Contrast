package com.contrast.Contrast.presentation.components.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R

@Composable
fun CustomDropdown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 12.dp), // Giữ padding phù hợp với TextField
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // Căn giữa toàn bộ nội dung
            ) {
                Text(
                    text = if (selectedOption.isNotEmpty()) selectedOption else placeholder,
                    color = if (selectedOption.isNotEmpty()) Color.Black else Color.Gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center // Căn giữa chữ
                )
                Image(
                    painter =  painterResource(id = R.drawable.arrowdown), // Thay bằng ảnh trong drawable
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 10.dp)
                )

            }
        }

        // Line màu xám phía dưới
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp) // Chỉnh padding để line không sát mép
                .background(
                    color = Color(0xFFD7D7D7)) // Đường màu xám
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            textAlign = TextAlign.Center, // Căn giữa nội dung
                            modifier = Modifier.fillMaxWidth() // Bắt Text chiếm toàn bộ chiều rộng để căn giữa
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )

            }
        }
    }
}
