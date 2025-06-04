package com.contrast.Contrast.presentation.features.opportunity.detail


import android.os.Build
import android.util.Log
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
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.toCleanDouble

import com.contrast.Contrast.presentation.components.alertDialog.ConfirmDeleteDialog
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.bottomAction.BottomActionList
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.editProductDialog.EditProductDialogScreen

import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabViewLocal
import com.contrast.Contrast.presentation.components.successLabel.DateRangeRow
import com.contrast.Contrast.presentation.components.successLabel.SucceccSeekBar
import com.contrast.Contrast.presentation.components.successLabel.SuccessLabel
import com.contrast.Contrast.presentation.components.swipeDelete.SwipeRevealItem
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.text.CustomText

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.contact.list.ContactItem
import com.contrast.Contrast.presentation.features.detail.InfoItemDetail
import com.contrast.Contrast.presentation.features.opportunity.viewModel.OpportunityDetailViewModel
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
fun OpportunityDetailScreen(
    navHostController: NavHostController,
    id: String,
    customer: String,
    customerEdit: String,
    viewModel: OpportunityDetailViewModel = hiltViewModel(),

    ) {

    val state by viewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }


    var showDialog by remember { mutableStateOf(false) }
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


    var fullUrl by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(3.0) }
    var vat by remember { mutableStateOf(0.0) }
    var discount by remember { mutableStateOf(0.0) }
    var unitPrice by remember { mutableStateOf(0.0) }
    var isPercent by remember { mutableStateOf(true) }

    if (showDialog) {
        EditProductDialogScreen(
            fullUrl = "",
            productName = productName,
            unitPrice = unitPrice,
            quantity = quantity,
            vat = vat,
            discount = discount,
            totalMoney = state.totalMoney,
            isPercent = isPercent,
            onUnitPriceChange = {
                unitPrice =it.toDouble()

                viewModel.updateProductDetails(unitPrice, quantity, vat, discount, isPercent)
            },
            onQuantityChange = {
                quantity =it.toDouble()
                viewModel.updateProductDetails(unitPrice, quantity, vat, discount, isPercent)
            },
            onVatChange = {
//                vat = it
//                viewModel.updateProductDetails(unitPrice.toString(), quantity.toString(), vat.toString(), discount.toString(), isPercent)
            },
            onDiscountChange = {
//                discount = it
//                viewModel.updateProductDetails(unitPrice.toString(), quantity.toString(), vat.toString(), discount.toString(), isPercent)
            },
            onToggleDiscountType = {
                isPercent = !isPercent
//                viewModel.updateProductDetails(unitPrice.toString(), quantity.toString(), vat.toString(), discount.toString(), isPercent)
            },
            onSave = { /* Lưu xử lý */ },
            onDismiss = { showDialog = false }
        )
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
            is ContactNavEvent.GoToContactOpportunityProject -> {
                navHostController.navigate(
                    ContactRoutes.ContactOpportunityProject.withArgs(
                        id = id,
                        type = "cohoikinhdoanh",
                    )
                )
                viewModel.resetNavigation()
            }
            is ContactNavEvent.GoToContactDetail -> {
                navHostController.navigate(
                    ContactRoutes.ContactDetail.withArgs(
                        id = event.id,
                        customer =customer,
                        customerEdit =customerEdit,
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
            title = stringResource(R.string.opportunity_detail),
            backgroundColor = FAFAFA,
            fontSize = 14.sp,
            onBackClick = { navHostController.popBackStack() },
        )
        HeaderImageTitle(
            name = state.opportunityName, iconRes = R.drawable.vcohoikinhdoanh
        )

       Row {  SuccessLabel(
           state.opportunity?.tentrangthai ?: "",
           state.opportunity?.mautrangthai ?: "#1bb635"
       )
           CustomText(
               text =state.totalMoney.formatCurrency(),
               color = FFFF5722,
               fontSize = 14.sp,
               textAlign = TextAlign.Right,
               fontWeight = FontWeight(500),
               modifier = Modifier.padding(start = 5.dp, end = 10.dp).weight(1f)
           ) }
        Column {
            val percent = (state.opportunity?.tylethanhcong ?: 0f)


               SucceccSeekBar(
                   progress = percent / 100f,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(horizontal = 10.dp)
               )




            DateRangeRow(Util.ddMMYYY(state.opportunity?.ngaybatdau ?: ""), Util.ddMMYYY(state.opportunity?.ngayketthuc ?: ""))

        }

        SegmentTabViewLocal(
            tabs = state.tabs,
            selectedTab = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it
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
                            1 -> state.contactsList
                            2 -> state.productList
                            3 -> state.attachList
                            else -> emptyList()
                        }
                        infoList.forEach { obj ->
                            when (selectedTabIndex) {
                                0 -> InfoItemDetail(info = obj as InfoDetail)
                                1 -> SwipeRevealItem(
                                    item = obj as Contact,
                                    isOpen = openedItemContact == obj as Contact,
                                    onSwipeStart = { swipedItem -> openedItemContact = swipedItem },
                                    onDeleteClick = { itemToDelete ->
                                        showDeleteDialog = true
                                        objToDeleteContact =
                                            itemToDelete // 💡 lưu lại để xử lý sau khi confirm
                                    },
                                    paddingTop = 5.dp,
                                    paddingBottom = 5.dp
                                ) { itemModifier ->


                                    ContactItem(obj = obj as Contact,
                                        state.showPhoneKH,
                                        state.showEmailKH,
                                        state.domain,
                                        onClickItem = {

                                            viewModel.onClickItem(state.selectedTabIndex, it, customer,customerEdit)
                                        },
                                        onCallPhone = {

                                        })
                                }

                                2 -> SwipeRevealItem(
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
                                    ProductItem(state.domain,
                                        obj = obj as ProductOpoortutityProject,


                                        onClickItem = {

                                            fullUrl = "${state.domain}${it.avata?:""}"
                                              productName = it.tensanpham?:""
                                            var  discountPercent = it.phantramgiamgia?:0.0
                                            showDialog = true
                                            discount = if(discountPercent>0)  (it.phantramgiamgia?:0.0) else  (it.sotiengiamgia?:0.0)
                                            unitPrice = (it.dongia?:0.0)
                                            vat = (it.phantramthue?:0.0)
                                            quantity = (it.soluong?:0.0)
                                            isPercent = if(discountPercent>0)  true else false
                                            viewModel.updateProductDetails(unitPrice, quantity, vat, discount, isPercent)

                                        })
                                }

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

              if(state.isShowAddButton){
                  Image(
                      painter = painterResource(R.drawable.plus_float_button),
                      "",
                      modifier = Modifier
                          .size(80.dp)
                          .padding(20.dp).noRippleClickableComposable { viewModel.onClickAddButton(state.selectedTabIndex, state.opportunity?.id?:"0"
                          ) }
                  )
              }
            }
        }
        BottomActionList(actions = viewModel.bottomActions, onItemClick = { item ->
            viewModel.onBottomActionClick(
                item.id, state.opportunity!!, customer, customerEdit
            )
        })
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun callApi(id: String, searchText: String, viewModel: OpportunityDetailViewModel) {

    viewModel.getOpportunityDetail(id)
    viewModel.getContactByOpportunity(id, searchText)
    viewModel.getProductByOpportunity(id, searchText)
    viewModel.getAttachByOpportunity(id, searchText)

}

