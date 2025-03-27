package com.contrast.Contrast.presentation.components.alertDialog

import android.content.res.Configuration
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R

import com.contrast.Contrast.presentation.theme.FFFEF4F4
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LanguageSelectionDialog(
    selectedLanguage: String,
    onSelectLanguage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        "en" to stringResource(id = R.string.language_english),
        "vi" to stringResource(id = R.string.language_vietnamese)
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(languages) { (code, label) ->
                    Text(
                        text = label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (selectedLanguage == code) FFFEF4F4 else Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onSelectLanguage(code) }
                            .padding(16.dp),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
