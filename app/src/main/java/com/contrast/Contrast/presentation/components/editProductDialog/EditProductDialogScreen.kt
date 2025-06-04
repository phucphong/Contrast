package com.contrast.Contrast.presentation.components.editProductDialog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.toCleanDouble

import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldNotClose
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.switch_custom.DiscountTypeSwitch
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.TealGreen


@Composable
fun EditProductDialogScreen(
    fullUrl: String,
    productName: String,
    unitPrice: Double,
    quantity: Double,
    vat: Double,
    discount: Double,
    totalMoney: Double,
    isPercent: Boolean,
    onUnitPriceChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onVatChange: (String) -> Unit,
    onDiscountChange: (Double) -> Unit,
    onToggleDiscountType: () -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {}, // không gọi gì => không đóng
        properties = DialogProperties(dismissOnClickOutside = false)
    ){
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column(  modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {


                  CustomText(
                  text = stringResource(R.string.edit_product),
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                      modifier = Modifier.weight(1f),
                      textAlign = TextAlign.Center
              )

                  Image(painter = painterResource(R.drawable.close), contentDescription = "",
                      modifier = Modifier.size(35.dp).padding(5.dp).noRippleClickableComposable { onDismiss() })
              }

                Spacer(modifier = Modifier.height(12.dp))

                // Tên và ảnh sản phẩm
                Row {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                    ) {
                        NetworkImage(
                            model = fullUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(70.dp)


                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    CustomText(
                        text = productName,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextFieldNotClose(
                    height = 45.dp,
                    value = unitPrice.toString(),
                    onValueChange = {
                        val cleaned = it.replace(".", "").replace(",", ".")

                        onUnitPriceChange(cleaned)
                                    },
                    placeholder = "",
                    keyboardType = KeyboardType.Number,

                )

                Spacer(modifier = Modifier.height(16.dp))
                // Số lượng
                StepperField(stringResource(R.string.quantity), quantity, onQuantityChange)

                Spacer(modifier = Modifier.height(16.dp))
                CustomDividerColor()
                Spacer(modifier = Modifier.height(5.dp))
                // VAT
                StepperField(stringResource(R.string.vat), vat, onVatChange)

                Spacer(modifier = Modifier.height(16.dp))
                CustomDividerColor()
                // Chiết khấu
                CustomText(
                    stringResource(R.string.discount),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 5.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    CustomTextFieldNotClose(
                        value = discount.toString(),
                        height = 45.dp,
                        onValueChange = { onDiscountChange(it.toCleanDouble()) },
                        placeholder = "",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                    )


                    Spacer(modifier = Modifier.width(8.dp))

                    DiscountTypeSwitch(
                        isPercent = isPercent,
                        onToggle = { onToggleDiscountType() },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(120.dp)
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))
                CustomDividerColor()
                // Chiết khấu
                CustomText(
                    stringResource(R.string.total_amount),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 5.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .height(40.dp)
                        .background(FAFAFA, RoundedCornerShape(5.dp))
                        .clip(RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CustomText(
                        text = totalMoney.formatCurrency(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))



                CustomButton(text = stringResource(id = R.string.save),
                    textColor = Color.White,
                    containerColor = TealGreen,
                    modifier = Modifier.fillMaxWidth(),
                    roundedCornerShape = 10.dp,
                    fontSize = 12.sp,
                    onClick = {
                        onSave()
                    })

            }
        }
    }
}
