package com.contrast.Contrast.presentation.components.alertDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
// mật khẩu thành công
@Composable
fun PasswordResetSuccessDialog(
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_security),
                    contentDescription = "Security Icon",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.password_reset_success),
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        text = {
            Text(
                text = stringResource(id = R.string.password_reset_message),
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = stringResource(id = R.string.continue_button),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}
