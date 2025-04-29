package com.contrast.Contrast.presentation.features.cart.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FF9E9E9E
import com.contrast.Contrast.presentation.theme.FFF5F5F5
import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.UltraLightGray
@Composable
fun QuantitySelectorCart(
    quantity: Double,
    increaseQuantity: () -> Unit,
    onQuantityChange: (Double) -> Unit,
    decreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(quantity.toInt().toString()))
    }
    val focusRequester = remember { FocusRequester() }
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
   Column {  Box (Modifier.height(10.dp))

       Row(
           verticalAlignment = Alignment.CenterVertically,
           modifier = modifier
               .clip(RoundedCornerShape(4.dp))
               .border(1.dp, FF9E9E9E, RoundedCornerShape(4.dp))
               .background(Color.White)
       ) {
           // Nút trừ
           Box(
               modifier = Modifier
                   .width(21.dp)
                   .height(21.dp)

                   .clickable { decreaseQuantity() },
               contentAlignment = Alignment.Center
           ) {
               Text("-", fontSize = 14.sp, color = TealGreen)
           }
           Box (Modifier.width(1.dp).height(20.dp).background(FF9E9E9E))
           // TextField
           BasicTextField(
               value = textFieldValue,
               onValueChange = { newValue ->
                   textFieldValue = newValue
                   val input = newValue.text.filter { it.isDigit() }
                   val newQuantity = input.toDoubleOrNull()
                   if (newQuantity != null && newQuantity > 0) {
                       onQuantityChange(newQuantity)
                   } else {
                       onQuantityChange(quantity)
                   }
               },
               modifier = Modifier
                   .width(40.dp)
                   .height(21.dp)
                   .focusRequester(focusRequester)
                   .onFocusChanged { focusState ->
                       if (focusState.isFocused) {
                           shouldSelectAll = true
                       }
                   },
               keyboardOptions = KeyboardOptions(
                   keyboardType = KeyboardType.Number,
                   imeAction = ImeAction.Done
               ),
               textStyle = TextStyle(
                   fontSize = 11.sp,
                   color = Color.Black,
                   textAlign = TextAlign.Center
               ),
               singleLine = true,
               decorationBox = { innerTextField ->
                   Box(
                       modifier = Modifier.fillMaxSize(),
                       contentAlignment = Alignment.Center
                   ) {
                       // ⭐ Sau khi click xong thì bôi đen toàn bộ
                       if (shouldSelectAll) {
                           LaunchedEffect(Unit) {
                               textFieldValue = textFieldValue.copy(
                                   selection = TextRange(0, textFieldValue.text.length)
                               )
                               shouldSelectAll = false // reset để không lặp
                           }
                       }
                       innerTextField()
                   }
               }
           )
           Box (Modifier.width(1.dp).height(20.dp).background(FF9E9E9E))
           // Nút cộng
           Box(
               modifier = Modifier
                   .width(21.dp)
                   .height(21.dp)

                   .clickable { increaseQuantity() },
               contentAlignment = Alignment.Center
           ) {
               Text("+", fontSize = 11.sp,  color = TealGreen)
           }
       } }
}
