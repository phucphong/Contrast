package com.contrast.Contrast.presentation.components.copyrightInfoSection

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText

import com.contrast.Contrast.presentation.theme.TealGreen
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CopyrightInfoSection(
    modifier: Modifier = Modifier,
    textColor: Color = TealGreen,
    versionColor: Color = Color.Gray
) {
  val  context: Context = LocalContext.current
    val currentYear = remember { LocalDate.now().year.toString() }
    val versionName = remember { getAppVersion(context) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(
            text = "${stringResource(R.string.copyright)} $currentYear by ITECHPRO",
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(5.dp))
        CustomText(
            text = "${stringResource(R.string.version)} $versionName",
            color = versionColor,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(5.dp))
    }
}
fun getAppVersion(context: Context): String {
    val packageManager = context.packageManager
    val packageName = context.packageName
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName ?: "1.0"
    } catch (e: Exception) {
        "1.0"
    }
}
