package com.contrast.Contrast.presentation.features.detail

import com.itechpro.domain.model.InfoDetail
import androidx.compose.foundation.layout.*


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.iOSUnderlineGray


@Composable
fun InfoItemDetail(
    info: InfoDetail
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            CustomText(text = info.label, color = PlaceholderGray, fontSize = 14.sp)
            Spacer(Modifier.size(10.dp))
            if (info.showCheckbox && info.onCheckedChange != null) {
                CheckBoxColor(checked = info.isChecked, onCheckedChange = { info.onCheckedChange })
            }else{
                CustomText(
                    text = info.value.ifBlank { "" },
                    color = if (info.highlight) TealGreen else Color.Black,
                    fontSize = 14.sp,


                )
            }
        }

        CustomDividerColor(iOSUnderlineGray)


    }
}
