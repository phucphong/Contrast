package com.contrast.Contrast.presentation.features.membership

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack


@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MembershipPolicyScreenPreview() {
    val navController = rememberNavController()
    MembershipPolicyScreen(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipPolicyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        CustomTopAppBarTittleBack(
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
            Text(
                text = stringResource(id = R.string.membership_policy_heading),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.points_policy_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            CustomText(
                text = stringResource(id = R.string.what_is_kat),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            CustomText(stringResource(id = R.string.kat_definition))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(
                text = stringResource(id = R.string.point_types),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            CustomText(stringResource(id = R.string.point_types_description))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(
                text = stringResource(id = R.string.rounding_rules),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            CustomText(stringResource(id = R.string.rounding_rules_description))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(
                text = stringResource(id = R.string.how_to_earn_points),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            CustomText(stringResource(id = R.string.how_to_earn_points_description))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(
                text = stringResource(id = R.string.point_accumulation_rules),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            CustomText(stringResource(id = R.string.point_accumulation_rules_description))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.rank_policy_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomText(
                text = stringResource(id = R.string.rank_policy_heading),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            CustomText(stringResource(id = R.string.rank_policy_description))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(stringResource(id = R.string.rank_evaluation_time))
            CustomText(stringResource(id = R.string.rank_evaluation_description))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(stringResource(id = R.string.how_to_keep_rank))
            CustomText(stringResource(id = R.string.how_to_keep_rank_description))
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(stringResource(id = R.string.rank_drop_rules))
            CustomText(stringResource(id = R.string.rank_drop_rules_description))
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
