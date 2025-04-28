package com.contrast.Contrast.presentation.components.alertDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.AEA1F27
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.TealGreen

@Preview(showBackground = true)

@Composable
fun CustomOkAlertDialog(
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { /* Không làm gì khi click ra ngoài */ },
        properties = DialogProperties(
            dismissOnClickOutside = false, // ⭐ Không cho click ra ngoài để đóng
            dismissOnBackPress = true       // ⭐ Vẫn cho nhấn nút back để đóng (hoặc chỉnh false nếu cần)
        )
    )  {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp).background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = stringResource(id = R.string.alert_title),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF151515),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth().padding( 20.dp)
            )



            Text(
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Normal,
                    color = FF151515,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))



            Text(
                text = stringResource(id = R.string.cancel),
                fontWeight = FontWeight.Bold,
                color = TealGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).noRippleClickableComposable {   onDismiss()}
            )
        }

    }
}
