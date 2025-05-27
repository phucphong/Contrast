package com.contrast.Contrast.presentation.features.opportunity.detail


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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.ConfirmDeleteDialog
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.bottomAction.BottomActionList
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.searchBar.SearchBar
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.swipeDelete.SwipeRevealItem
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.chat.ChatInputBox
import com.contrast.Contrast.presentation.features.contact.list.ContactItem
import com.contrast.Contrast.presentation.features.customer.viewmodel.CustomerViewModel
import com.contrast.Contrast.presentation.features.detail.InfoItemDetail
import com.contrast.Contrast.presentation.features.opportunity.viewModel.OpportunityDetailViewModel
import com.contrast.Contrast.presentation.features.product_opportutity_project.ProductItem
import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.itechpro.domain.model.contacts.Contacts
import com.itechpro.domain.model.navigationEvent.CustomerNavigationEvent
import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.product.InfoDetail
import com.itechpro.domain.model.product.ProductOpoortutityProject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpportunityDetailScreen(
    navHostController: NavHostController,
    id: String,
    viewModel: OpportunityDetailViewModel = hiltViewModel(),

    ) {

    val state by viewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    val isRefreshing by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var openedItemContact by remember { mutableStateOf<Any?>(null) }
    var objToDeleteContact by remember { mutableStateOf<Any?>(null) }


    if (showDeleteDialog) {
        ConfirmDeleteDialog(show = showDeleteDialog, onDismiss = {
            showDeleteDialog = false
            objToDeleteContact = null
            openedItemContact = null // 💡 đóng nút delete đang mở
        }, onConfirm = {

            var content = "";
//            val name = objToDeleteContact?.ten ?: ""
//            val customerName = "- ${objToDeleteContact?.ten ?: ""}"
//            content = name + customerName

//            viewModel.deleteOpportunity(type,"cohoikinhdoanh",objToDeleteContact?.id?:"", "cohoikinhdoanh", content, startDate, endDate, searchText, state.categoryCode, state.categorys)
//
            showDeleteDialog = false
            objToDeleteContact = null
            openedItemContact = null // 💡 đóng nút delete đang mở
        })


    }

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


            else -> Unit
        }
    }

    LaunchedEffect(Unit) {


        snapshotFlow { selectedTabIndex }.collect { index ->
                when (index) {
//                    0 -> viewModel.getTimelineData(id)  // API load timeline
//                    1 -> viewModel.getTimelineData(id)  // API load timeline
//                    2 -> viewModel.getTimelineData(id)  // API load timeline
//                    3 -> viewModel.getExchangeData(id)  // API load exchange
                }
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
            title = stringResource(R.string.opportunity_detail),
            backgroundColor = FAFAFA,
            fontSize = 14.sp,
            onBackClick = { navHostController.popBackStack() },
        )
        HeaderImageTitle(
            name = state.opportunityName, iconRes = R.drawable.vcohoikinhdoanh
        )
        SegmentTabLocal(tabs = state.tabs,
            selectedTab = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it })


        CustomSwipeRefresh(modifier = Modifier.weight(1f),
            isRefreshing = isRefreshing,
            onRefresh = { callApi(id, state.searchText, viewModel) }) {
            Box( modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .weight(1f),
                 contentAlignment = Alignment.BottomEnd
                ){
                LazyColumn(modifier = Modifier
                    .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {

                        SearchBar(
                            searchText = state.searchText,
                            backgroundColor = FFFFFFFF,

                            placeholder = stringResource(R.string.search_placeholder),
                            onTextChange = { newText ->

                                viewModel.setSearch(newText)
//
//                            if (!searchLocal) {
//                                viewModel.getCategory(newText, typeCheck)
//                            }
                            }
                        )
                    }

                    item {
                        val infoList = when (selectedTabIndex) {
                            0 -> state.objInfoList
                            1 -> state.contactsList
                            2 -> state.productList
                            3 -> state.attachList
                            else -> emptyList()
                        }
                        infoList.forEach { obj ->
                            when (selectedTabIndex) {
                                0 -> InfoItemDetail(info = obj as InfoDetail)
                                1 -> SwipeRevealItem(
                                    item = obj as Contacts,
                                    isOpen = openedItemContact == obj as Contacts,
                                    onSwipeStart = { swipedItem -> openedItemContact = swipedItem },
                                    onDeleteClick = { itemToDelete ->
                                        showDeleteDialog = true
                                        objToDeleteContact =
                                            itemToDelete // 💡 lưu lại để xử lý sau khi confirm
                                    },
                                    paddingTop = 5.dp,
                                    paddingBottom = 5.dp
                                ) { itemModifier ->
                                    ContactItem(obj = obj as Contacts,
                                        state.showPhoneKH,
                                        state.showEmailKH,
                                        onClickItem = {

                                        },
                                        onCallPhone = {

                                        })
                                }   2 -> SwipeRevealItem(
                                item = obj as ProductOpoortutityProject,
                                isOpen = openedItemContact == obj as ProductOpoortutityProject,
                                onSwipeStart = { swipedItem -> openedItemContact = swipedItem },
                                onDeleteClick = { itemToDelete ->
                                    showDeleteDialog = true
                                    objToDeleteContact =
                                        itemToDelete // 💡 lưu lại để xử lý sau khi confirm
                                },
                                paddingTop = 5.dp,
                                paddingBottom = 5.dp
                            ) { itemModifier ->
                                ProductItem( state.domain,obj = obj as ProductOpoortutityProject,


                                    onClickItem = {

                                    })
                            }
//                            2 -> ProductItem(info = info as Product)
//                            3 -> AttachmentItem(info = info as Attachment)
                            }
                        }
                    }
                }

                Image(painter = painterResource(R.drawable.plus_float_button),"", modifier = Modifier.size(70.dp).padding(20.dp))
            }
        }



        BottomActionList(actions = viewModel.bottomActions, onItemClick = { item ->
            viewModel.onBottomActionClick(
                item.id, state.transferredToProject, state.successRate
            )
        })
    }
    if (selectedTabIndex == 3) {
        ChatInputBox(text = "", onTextChange = {}, onSendClick = {}, onAttachClick = {})
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun callApi(id: String, searchText: String, viewModel: OpportunityDetailViewModel) {

    viewModel.getOpportunityDetail(id)
    viewModel.getContactByOpportunity(id, searchText)
    viewModel.getProductByOpportunity(id, searchText)
    viewModel.getAttachByOpportunity(id, searchText)

}

