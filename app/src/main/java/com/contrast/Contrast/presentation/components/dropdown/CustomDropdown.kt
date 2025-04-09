package com.contrast.Contrast.presentation.components.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.contrast.Contrast.presentation.theme.FFD7D7D7

@Composable
fun CustomDropdown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    placeholder: String,
    textAlign:TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
    showUnderline: Boolean = true,
    fullWith: Boolean = true,

) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .then(
                if (!showUnderline) Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp)).border(1.dp,FFD7D7D7,  shape = RoundedCornerShape(10.dp))
                else Modifier
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (selectedOption.isNotEmpty()) selectedOption else placeholder,
                    color = if (selectedOption.isNotEmpty()) Color.Black else Color.Gray,
                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                    textAlign =textAlign
                )
                Image(
                    painter = if(expanded)painterResource(id = R.drawable.up)else painterResource(id = R.drawable.arrowdown),
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 10.dp)
                )
            }
        }

        // ✅ Nếu showUnderline thì mới hiện line
        if (showUnderline) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 8.dp)
                    .background(color = FFD7D7D7)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = if(fullWith){
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            }else{
                Modifier
                    .background(Color.White)
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp)
            }
        ) {
            options.forEach { option ->
                DropdownMenuItem(modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            text = option,
                            textAlign = textAlign,
                            modifier = Modifier.fillMaxWidth()
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
