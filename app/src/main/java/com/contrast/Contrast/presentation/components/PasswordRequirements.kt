package com.contrast.Contrast.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.ui.Modifier

import androidx.compose.material3.Text


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Composable
fun PasswordRequirements(password: String) {
    val requirements = listOf(
        stringResource(id = R.string.password_length) to (password.length in 8..15),
        stringResource(id = R.string.password_digit) to password.any { it.isDigit() },
        stringResource(id = R.string.password_uppercase) to password.any { it.isUpperCase() },
        stringResource(id = R.string.password_lowercase) to password.any { it.isLowerCase() },
        stringResource(id = R.string.password_special) to password.any { "!@#$%^&*()".contains(it) }
    )


    val customFontFamily = FontFamily(
        Font(R.font.inter), // Font thường
        Font(R.font.inter_18pt_bold, FontWeight.Bold),
    )
    Column(modifier = Modifier.padding(10.dp)) {
        requirements.forEach { (text, isValid) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (isValid) R.drawable.ic_check else R.drawable.ic_cross // Icon dấu ✓ hoặc ❌
                    ),
                    contentDescription = if (isValid) "Valid" else "Invalid",
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = text,

                    style = TextStyle(
                        fontFamily = customFontFamily, // Thiết lập fontFamily
                        fontSize =14.sp,  // Kích thước chữ
                        color = Color.Black
                    )
                )
            }
        }
    }
}
