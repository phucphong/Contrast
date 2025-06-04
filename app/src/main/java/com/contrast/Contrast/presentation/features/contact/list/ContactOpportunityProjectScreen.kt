package com.contrast.Contrast.presentation.features.contact.list


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog

import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.contact.viewModel.ContactViewModel
import com.contrast.Contrast.presentation.navigator.routers.ContactRoutes
import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes


import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.SearchDialog
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.navigationEvent.ContactNavEvent
import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContactOpportunityProjectScreen(
    navHostController: NavHostController,
    id: String,
    type: String,
    viewModel: ContactViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }

    var selectedType by remember { mutableStateOf("Ngày") }
    var searchText by remember { mutableStateOf("") }
    var statusId by remember { mutableStateOf("0") }
    var ido by remember { mutableStateOf("0") }

    val listState = rememberLazyListState()
    var typeDate by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }
    var openedItem by remember { mutableStateOf<Contact?>(null) }
    var objToDelete by remember { mutableStateOf<Contact?>(null) }
    LaunchedEffect(state.contactList) {
        viewModel.setInitialContacts(state.pageContact)
    }

    LaunchedEffect(Unit) {
        delay(100)
        viewModel.getContactByType(id,type)

    }



    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }.distinctUntilChanged().debounce(300).collect { (lastIndex, total) ->
            if (lastIndex != null && total > 0 && lastIndex >= total - 2) {

                viewModel.loadNextPage()
            }
        }
    }




    if (isFinterDialog) {
        var obj = SearchDialog(
            startDate = startDate, endDate = endDate, selectedType = selectedType
        )

        SearchConditionDialog(onDismiss = { isFinterDialog = false },
            search = obj,
            type = typeDate,
            onSearch = { search ->
                startDate = search.startDate ?: ""
                endDate = search.endDate ?: ""
                selectedType = search.selectedType ?: ""
                viewModel.getContactByType(id,type)
                isFinterDialog = false
            })

    }

    if (state.errorMessage.isNotEmpty()) {
        CustomOkAlertDialog(message = state.errorMessage, onDismiss = {
            viewModel.clearErrorMessage()

        })
    }
    Column {

      


        CustomTopAppBarBackTitleSave(painter = painterResource(R.drawable.quaylai),
            title = stringResource(id = R.string.customer_add),
            onBackClick = {navHostController.popBackStack()  },
            onSaveClick = {
viewModel.saveContactToOpportunityProject(type, id,state.contactList)
            })

        HeaderImageTitle(

            name = if (type == "cohoikinhdoanh") stringResource(R.string.contact_opportunity) else stringResource(
                R.string.contact_project
            ), iconRes = R.drawable.vlienhe

        )

        CustomSwipeRefresh(isRefreshing = isRefreshing, onRefresh = {

            viewModel.getContactByType(id,type)
        }) {
            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {


                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                } else {
                    if (state.pageContact.isEmpty()) {
                        item {
                            EmptyStateScreen(
                                imageRes = R.drawable.nodata,
                                size = 90.dp,
                                title = stringResource(R.string.youNotHaveOpportunities),
                                background = FFFAFAFA,
                                modifier = Modifier.padding(40.dp)
                            )
                        }

                    } else {
                        val rows = state.pageContact.chunked(1)
                        items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                            val item = row.firstOrNull()
                            var checked by remember { mutableStateOf(false) }
                            if (item != null) {
                                ContactItemChecked(obj = item,
                                    state.showPhoneKH,
                                    state.showEmailKH,
                                    checked = checked,
                                    state.domain,
                                    onCallPhone = {
                                    },
                                    onCheckedChange = { newChecked -> checked = newChecked
                                        item.checked = newChecked


                                    }
                                )


                            }
                        }

                    }
                }
            }
        }
    }
}

