package com.contrast.Contrast.presentation.features.opportunity.list.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.LightGrayBackground
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.components.text.CustomText

@Composable
fun OpportunityItem(
    name: String,
    key: String,
    total: String,
    customer: String,
    blankWork: String,
    complete: String,
    date: String,
    personInCharge: String,
    onClickOpportunity: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding()
            .noRippleClickableComposable { onClickOpportunity() }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            CustomText(
                text = name,
                color = TealGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(modifier = Modifier.padding(top = 8.dp)) {
                CustomText(
                    text = key,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                CustomText(
                    text = total,
                    color = Color.Red,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = customer,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 17.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 2.dp)
                            .background(LightGrayBackground)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 2.dp)
                        )

                        CustomText(
                            text = blankWork,
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.LightGray)
                                .padding(horizontal = 2.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = date,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                CustomText(
                    text = personInCharge,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1.2f)
                )
            }
        }

        CustomDividerColor()
    }
}
