package com.contrast.Contrast.presentation.components.dropdown



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.FFD7D7D7
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.TimeTypeOption

@Composable
fun CustomDropdownTimeType(
    options: List<TimeTypeOption>,
    selectedOption: String,
    onOptionSelected: (TimeTypeOption) -> Unit,
    placeholder: String,
    textAlign:TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,


    ) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(10.dp)).border(1.dp,FFD7D7D7,  shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickableComposable { expanded = true }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (selectedOption.isNotEmpty()) selectedOption else placeholder,
                    color = TealGreen,
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



        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(modifier = Modifier.fillMaxWidth() .padding(horizontal = 20.dp),
                    text = {
                        Text(
                            text = option.label,
                            textAlign = textAlign,
                            color = TealGreen,
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
