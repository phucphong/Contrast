package com.contrast.Contrast.presentation.features.contact.detail

import com.contrast.Contrast.presentation.features.opportunity.detail.AttachFileItemView


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.bottomAction.BottomActionList
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabViewLocal


import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.contact.list.ContactItem
import com.contrast.Contrast.presentation.features.contact.viewModel.ContactDetailViewModel
import com.contrast.Contrast.presentation.features.detail.InfoItemDetail
import com.contrast.Contrast.presentation.features.product_opportutity_project.ProductItem
import com.contrast.Contrast.presentation.navigator.routers.ContactRoutes
import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.utils.Util
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.navigationEvent.ContactNavEvent
import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.InfoDetail
import com.itechpro.domain.model.product.ProductOpoortutityProject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContactDetailScreen(
    navHostController: NavHostController,
    id: String,
    customer: String,
    customerEdit: String,
    viewModel: ContactDetailViewModel = hiltViewModel(),

    ) {

    val state by viewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    val isRefreshing by remember { mutableStateOf(false) }



    LaunchedEffect(id) {
        if (id != "0") {
            callApi(id, state.searchText, viewModel)
            viewModel.loadDefaultTabs()
        }
    }

    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {
            is OpportunityNavEvent.GoToEdit -> {
                navHostController.navigate(
                    OpportunityRoutes.OpportunityEdit.withArgs(
                        id = id,
                    )
                )
                viewModel.resetNavigation()
            }

            is ContactNavEvent.GoToContactOpportunityProject -> {
                navHostController.navigate(
                    ContactRoutes.ContactOpportunityProject.withArgs(
                        id = id,
                        type = "cohoikinhdoanh",
                    )
                )
                viewModel.resetNavigation()
            }


            else -> Unit
        }
    }


    if (state.errorMessage.isNotEmpty()) {
        CustomOkAlertDialog(message = state.errorMessage, onDismiss = {
            viewModel.clearErrorMessage()

        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomTopAppBarBackTitleSave(
            painter = painterResource(R.drawable.quaylai),
            iconTint = TealGreen,
            title = stringResource(R.string.contact_detail),
            backgroundColor = FAFAFA,
            fontSize = 14.sp,
            onBackClick = { navHostController.popBackStack() },
        )
        HeaderImageTitle(
            name = state.contactName, iconRes = R.drawable.vlienhe
        )




        SegmentTabViewLocal(
            tabs = state.tabs,
            selectedTab = selectedTabIndex,
            onTabSelected = {
                selectedTabIndex = it
                viewModel.hideAddButton(selectedTabIndex)
            },
            isCount = true,
        )


        CustomSwipeRefresh(modifier = Modifier.weight(1f),
            isRefreshing = isRefreshing,
            onRefresh = { callApi(id, state.searchText, viewModel) }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .weight(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    item {
                        val infoList = when (selectedTabIndex) {
                            0 -> state.objInfoList
                            1 -> state.emailList
                            2 -> state.callList
                            3 -> state.smsList
                            else -> emptyList()
                        }
                        infoList.forEach { obj ->
                            when (selectedTabIndex) {
                                0 -> InfoItemDetail(info = obj as InfoDetail)
                                1 ->
                                    ContactItem(obj = obj as Contact,
                                    state.showPhoneKH,
                                    state.showEmailKH,
                                    state.domain,
                                    onClickItem = {},
                                    onCallPhone = {

                                    })

                                2 -> ProductItem(state.domain,
                                    obj = obj as ProductOpoortutityProject,
                                    onClickItem = {

                                    })

                                3 -> {
                                    obj as AttachFile
                                    val fileUrl = "${state.domain}${obj.dinhkem ?: ""}"

                                    AttachFileItemView(fileName = obj.tenfilehienthi ?: "",
                                        fileSize = obj.dungluong ?: "",
                                        personCreate = obj.nguoidang ?: "",
                                        createdDate = obj.lud ?: "",
                                        fileUrl = fileUrl,
                                        onDownloadClick = {
                                            viewModel.downloadImage(it)
                                        })
                                }

                            }
                        }
                    }
                }


            }
        }
        BottomActionList(actions = viewModel.bottomActions, onItemClick = { item ->
            viewModel.onBottomActionClick(
                item.id, state.contact!!, customer, customerEdit
            )
        })
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun callApi(id: String, searchText: String, viewModel: ContactDetailViewModel) {

    viewModel.getContactDetail(id)
//    viewModel.getEmailByContact(id)
//    viewModel.getCallByContact(id)
//    viewModel.getSMSByContact(id)


}

