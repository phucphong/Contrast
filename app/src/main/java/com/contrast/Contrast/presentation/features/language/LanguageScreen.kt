package com.contrast.Contrast.presentation.features.language
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.LanguageSelectionDialog
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack
import com.contrast.Contrast.presentation.theme.FFD91E18

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LanguageScreen(
    selectedLanguage: String,
    onBackPressed: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {

    var selectedLanguage by remember { mutableStateOf("vi") } // Mặc định: Tiếng Việt
    var showDialog by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Spacer(modifier = Modifier.height(30.dp))
        CustomTopAppBarTittleBack(
            title = stringResource(R.string.language),
            FFD91E18,
            onBackClick = { }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Label
        Text(
            text = stringResource(R.string.choose_language),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray,
            fontSize = 14.sp
        )

        // Language selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if(selectedLanguage=="vi") stringResource(R.string.language_vietnamese) else stringResource(R.string.language_english) , fontSize = 16.sp)
            Image(
                painter =if(showDialog) painterResource(R.drawable.up) else painterResource(R.drawable.down),
                contentDescription = null,
                modifier = Modifier.size(20.dp)

            )
        }

        CustomDividerColor()

    }

    // Dialog chọn ngôn ngữ
    if (showDialog) {
        LanguageSelectionDialog(
            selectedLanguage = selectedLanguage,
            onSelectLanguage = { langCode ->
                selectedLanguage = langCode
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}
