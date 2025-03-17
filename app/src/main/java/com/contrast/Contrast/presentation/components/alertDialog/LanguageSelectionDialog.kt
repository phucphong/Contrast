package com.contrast.Contrast.presentation.components.alertDialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LanguageSelectionDialog(
    selectedLanguage: String,
    onSelectEnglish: () -> Unit,
    onSelectVietnamese: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.language_english),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectEnglish() }
                        .padding(16.dp),
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
                Divider(color = Color.Gray, thickness = 0.5.dp)
                Text(
                    text = stringResource(id = R.string.language_vietnamese),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectVietnamese() }
                        .background(if (selectedLanguage == "vi") Color(0xFFFFF0F0) else Color.Transparent)
                        .padding(16.dp),
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
