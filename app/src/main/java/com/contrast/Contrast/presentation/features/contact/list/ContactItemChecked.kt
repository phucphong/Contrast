package com.contrast.Contrast.presentation.features.contact.list


import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.itechpro.domain.model.contact.Contact
import com.contrast.Contrast.R


import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.utils.Util

@Composable
fun ContactItemChecked(
    obj: Contact,
    showPhoneKH: Boolean,
    showEmailKH: Boolean,
    checked: Boolean,
    domain: String,
    onCallPhone: (String) -> Unit = {},
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier

) {
    Column(modifier = modifier
        .padding()
        .noRippleClickableComposable { onCheckedChange(!checked) }) {


        val name = obj.ten ?: ""
        val contactName = obj.tenlienhe ?: ""
        val phone = obj.dienthoai ?: ""
        val phone1 = obj.dienthoai1 ?: ""

        val email = obj.email ?: ""
        val email1 = obj.email1 ?: ""
        var fullname = "";
        var fullPhone = "";
        var fullEmail = "";
        if (name.isNotEmpty()) {
            fullname = name
        }
        if (contactName.isNotEmpty()) {
            fullname = contactName
        }

        if (phone1.isNotEmpty()) {
            fullPhone = phone1
        } else {
            if (phone.isNotEmpty()) {
                fullPhone = phone
            }
        }

        if (email1.isNotEmpty()) {
            fullEmail = email1
        } else {
            if (email.isNotEmpty()) {
                fullEmail = email
            }
        }

        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fileUrl = "${domain}${obj.hinhanhtxt ?: ""}"
            NetworkImage(
                model = fileUrl,
                error = painterResource(R.drawable.user_contact),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 8.dp)
            )
            Column(
                Modifier

                    .padding(start = 8.dp)
                    .weight(1f)
            ) {

                CustomText(
                    text = "${obj.xungho ?: ""}: $fullname",
                    color = TealGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Image(
                        painter = painterResource(R.drawable.telephone),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    CustomText(text = if (showPhoneKH) fullPhone else Util.hide4PhoneEnd(fullPhone),
                        color = TealGreen,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .noRippleClickableComposable { onCallPhone(fullPhone) }

                    )
                }
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Image(
                        painter = painterResource(R.drawable.email),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    CustomText(
                        text = if (showEmailKH) fullEmail else Util.hideEmail(fullEmail),
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)

                    )
                }
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Image(
                        painter = painterResource(R.drawable.contact),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    CustomText(
                        text = obj.tenkhachhang ?: "",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            // Icon tick hoặc khối màu
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = TealGreen,
                    modifier = Modifier.size(16.dp)
                )
            }


        }



        CustomDividerColor()
    }
}
