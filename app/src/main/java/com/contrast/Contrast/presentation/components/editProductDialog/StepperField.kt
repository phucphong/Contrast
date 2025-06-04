package com.contrast.Contrast.presentation.components.editProductDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.extensions.toCleanDouble

import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldNotClose
import com.contrast.Contrast.presentation.components.text.CustomText

@Composable
fun StepperField(label: String, value: Double, onValueChange: (String) -> Unit) {
    Column {
        CustomText(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp)
        ) {
            IconButton(modifier = Modifier.padding(5.dp),onClick = { if (value > 0) onValueChange((value - 1.0).toString()) }) {
                Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.padding(5.dp))
            }



            CustomTextFieldNotClose(
                height = 45.dp,
                value = value.toString(),
                onValueChange = {
                    if (it.isNotEmpty()) {
                        onValueChange(it)
                    }
                },
                placeholder = "",
                keyboardType = KeyboardType.Number,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                textAlign = TextAlign.Center,
            )


            IconButton( modifier = Modifier.padding(5.dp),onClick = { onValueChange((value + 1).toString()) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}
