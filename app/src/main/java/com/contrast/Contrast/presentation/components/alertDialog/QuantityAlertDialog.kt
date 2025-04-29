package com.contrast.Contrast.presentation.components.alertDialog


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.capitalizeEachWord
import com.contrast.Contrast.presentation.components.basicTextfield.QuantityInputField
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.AEA1F27
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.TealGreen

@Composable
fun QuantityAlertDialog(
    title: String = "",
    quantity: Double = 0.0,
    onQuantityChange: (Double) -> Unit,
    onDismiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {}
) {

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(quantity.toInt().toString()))
    }
    val text = remember {"" }
    var shouldSelectAll by remember { mutableStateOf(false) }

    LaunchedEffect(quantity) {
        val newText = quantity.toInt().toString()
        if (textFieldValue.text != newText) {
            textFieldValue = TextFieldValue(
                text = newText,
                selection = TextRange(newText.length) // chỉ đặt cursor cuối
            )
        }
    }
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
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),

                    color = Color(0xFF151515),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth().padding( 20.dp)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFD7D7D7), shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                QuantityInputField(
                    quantity = quantity,
                    onQuantityChange = { newQuantity ->
                        onQuantityChange(newQuantity)

                    }
                )




            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomDividerColor()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),

                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f).noRippleClickableComposable {   onDismiss()}
                )

                Box(
                    Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(AEA1F27)
                )

                Text(

                    text = stringResource(id = R.string.confirm),

                    color = TealGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f).noRippleClickableComposable {  onConfirm(textFieldValue.text)}

                )
            }


    }
    }
}
