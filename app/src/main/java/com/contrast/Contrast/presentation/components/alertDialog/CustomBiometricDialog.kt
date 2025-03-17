package com.contrast.Contrast.presentation.components.alertDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
@Preview(showBackground = true)
@Preview(name = "Light Mode", showBackground = true)

// thông báo đăng nhập faceid
@Composable
fun CustomBiometricDialog(
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var doNotShowAgain by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        title = {
            Text(
                text = stringResource(id = R.string.biometric_title),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.faceid),
                        contentDescription = "Biometric Icon",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = message,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = doNotShowAgain,
                        onCheckedChange = { doNotShowAgain = it }
                    )
                    Text(text = stringResource(id = R.string.do_not_show_again))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = stringResource(id = R.string.activate_biometric),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}
