package com.contrast.Contrast.presentation.features.account.personalInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdownUnderline
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PersonalInfoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomTopAppBarBackTitle (
            title = stringResource(id = R.string.membership_policy_title),
            Color.Red,
            onBackClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {


            Spacer(modifier = Modifier.height(16.dp))
            CustomText(
                text = stringResource(id = R.string.personal_info_heading),
                fontSize = 16.sp,
                )



            Spacer(modifier = Modifier.height(16.dp))


            CustomText(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.full_name))
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("*")
                    }
                }.toString(),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )


            CustomText(
                text = "Lương Duy Long",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))



            CustomText(
                text = stringResource(id = R.string.occupation),
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )


            CustomText(
                text = "Sinh viên",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.warning),
                            contentDescription = "Warning",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.note),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.personal_info_warning),
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.birthday),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDropdownUnderline(
                    options = (1..31).map { it.toString() },
                    selectedOption = "DD",
                    onOptionSelected = {},
                    placeholder = stringResource(id = R.string.day_placeholder),
                    modifier = Modifier.weight(1f)
                )
                CustomDropdownUnderline(
                    options = (1..12).map { it.toString() },
                    selectedOption = "MM",
                    onOptionSelected = {},
                    placeholder = stringResource(id = R.string.month_placeholder),
                    modifier = Modifier.weight(1f)
                )
                CustomDropdownUnderline(
                    options = (1950..2024).map { it.toString() },
                    selectedOption = "YY",
                    onOptionSelected = {},
                    placeholder = stringResource(id = R.string.year_placeholder),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.contact_info),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))



            CustomText(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.email))
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("*")
                    }
                }.toString(),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )

            CustomText(
                text = "long@gmail.com",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )


            Spacer(modifier = Modifier.height(16.dp))


            CustomText(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.phone_number))
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("*")
                    }
                }.toString(),
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )


            CustomText(
                text = "0964931225",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign= TextAlign.Right,
                modifier = Modifier.weight(1f)
            )

        }
    }
}
