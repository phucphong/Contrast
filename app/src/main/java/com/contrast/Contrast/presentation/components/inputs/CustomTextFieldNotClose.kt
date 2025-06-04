package com.contrast.Contrast.presentation.components.inputs



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrencyInput


import com.contrast.Contrast.extensions.toCleanDouble
import com.contrast.Contrast.utils.Util
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun CustomTextFieldNotClose(
    value: String,
    textAlign: TextAlign = TextAlign.Left,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    padding: Dp = 0.dp,
    height: Dp = 50.dp,
    modifier: Modifier = Modifier,
) {
    val customFontFamily = FontFamily(
        Font(R.font.inter),
        Font(R.font.inter_18pt_bold, FontWeight.Bold),
    )

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = Util.formatDecimalFormatSymbols(value.toDouble())
            )
        )
    }

    val focusRequester = remember { FocusRequester() }
    var hasFocused by remember { mutableStateOf(false) }

    // Sync khi value thay đổi từ ngoài
    LaunchedEffect(value) {

        Log.e("valueformatted",value);
        val formatted = Util.formatDecimalFormatSymbols(value.toDouble())
        if (formatted != textFieldValue.text) {
            textFieldValue = TextFieldValue(text = formatted)
        }
    }

    Box(
        modifier = modifier
            .height(height)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFD7D7D7), shape = RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val rawText = newValue.text

                if (rawText.isNotEmpty()) {
                    val cursorPosition = newValue.selection.end // Vị trí con trỏ hiện tại

                    // Tách phần nguyên và thập phân
                    val parts = rawText.replace(".", "").split(",")
                    val intPart = parts.getOrNull(0)?.toDoubleOrNull() ?: 0.0
                    val decimalPart = if (parts.size > 1) "," + parts[1] else ""

                    // Format phần nguyên
                    val formattedInt = Util.formatDecimalFormatSymbols(intPart)
                    val formatted = formattedInt + decimalPart
                    Log.e("formatted", formatted)

                    // Tính toán lại vị trí con trỏ sau format
                    val addedLength = formatted.length - rawText.length
                    val newCursor = (cursorPosition + addedLength).coerceIn(0, formatted.length)

                    // Cập nhật TextFieldValue với con trỏ mới
                    textFieldValue = TextFieldValue(
                        text = formatted,
                        selection = TextRange(newCursor)
                    )

                    // Trả ra raw text (không format) nếu cần
                    onValueChange(formatted)
                } else {
                    textFieldValue = TextFieldValue("0", TextRange(1))
                    onValueChange("0.0")
                }
            }

            ,
            placeholder = {
                Text(
                    placeholder,
                    color = Color(0xFFD7D7D7),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        textAlign = textAlign
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            visualTransformation = VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(horizontal = padding)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && !hasFocused) {
                        textFieldValue = textFieldValue.copy(
                            selection = TextRange(0, textFieldValue.text.length)
                        )
                        hasFocused = true
                    }
                },
            textStyle = TextStyle(
                fontSize = 13.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF151515),
                textAlign = textAlign
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}
