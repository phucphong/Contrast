package com.contrast.Contrast.presentation.components.inputs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.capitalizeEachWord
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    padding: Dp = 0.dp,
    isShowIcon: Boolean = false,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    onClickReadOnly: (() -> Unit)? = null
) {
    val customFontFamily = FontFamily(
        Font(R.font.inter),
        Font(R.font.inter_18pt_bold, FontWeight.Bold),
    )

    if (readOnly) {
        // 👉 Nếu readOnly, thì show Text + click
        Box(
            modifier = modifier
                .padding(vertical = 5.dp)
                .height(50.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFD7D7D7), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .clickable {
                    Log.d("SHOW_DIALOG", "Clicked on readOnly box")
                    onClickReadOnly?.invoke()
                }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (value.isNotEmpty()) value else placeholder,
                fontSize = 14.sp,
                fontFamily = customFontFamily,
                color = if (value.isEmpty()) Color(0xFFD7D7D7) else Color(0xFF151515),
            )
        }
    } else {
        // 👉 Nếu không readOnly, dùng TextField như bình thường
        Box(
            modifier = modifier
                .padding(vertical = 5.dp)
                .height(50.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFD7D7D7), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
              ,
            contentAlignment = Alignment.CenterStart
        ) {
        TextField(
            value = value,
            onValueChange = { onValueChange(it.capitalizeEachWord()) },
            placeholder = {
                Text(
                    placeholder,
                    color = Color(0xFFD7D7D7),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isShowIcon) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(padding),
            textStyle = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF151515),
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Image(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Clear",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        )
    }}
}
