package com.contrast.Contrast.presentation.features.product.detail




import android.os.Build
import android.util.Log
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.image.NetworkImage

import com.contrast.Contrast.presentation.components.searchBar.TopTextNotificationShare

import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.evaluate.EvaluateScreen
import com.contrast.Contrast.presentation.features.evaluate.EvaluateViewModel
import com.contrast.Contrast.presentation.features.flashSale.FlashSaleHome

import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductDetailHeader
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductPriceDetailSection
import com.contrast.Contrast.presentation.features.product.detail.ui.WebViewProduct

import com.contrast.Contrast.presentation.features.product.viewmodel.ProductViewModel
import com.contrast.Contrast.presentation.features.rating.RatingHeader
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA

import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.utils.NetworkMonitor
import com.itechpro.domain.model.evaluate.EvaluateDetail
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailScreen(
    navHostController: NavHostController,
    id: String,
    idUnit: String,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    evaluateViewModel: EvaluateViewModel = viewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val productInfo by viewModel.productInfo.collectAsState()
    val evaluates by evaluateViewModel.evaluates.collectAsState()
    val products by viewModel.products.collectAsState()
    val pagedProducts by viewModel.pagedProducts.collectAsState()
    val totalEvaluate by evaluateViewModel.totalEvaluate.collectAsState()
    val ratingScore by evaluateViewModel.ratingScore.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val promoUiDataMapInfo = viewModel.promoUiDataMapInfo
    val totalCartItems by cartViewModel.totalCartItems.collectAsState()
    val totalNotificationItems by notificationViewModel.totalNotificationItems.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isOnline by NetworkMonitor.isOnline.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("huuhinh") }
    var bookService by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf(0) }

    val navEvent by viewModel.navigationEvent.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(products) { viewModel.setInitialProducts(products) }

    LaunchedEffect(Unit) {
        delay(100) // cho hệ thống khởi động mạng nếu vừa chuyển 4G
        viewModel.loadData(id, idUnit)
        evaluateViewModel.loadEvaluate(id, "3")
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }
            .distinctUntilChanged()
            .debounce(300) // ✅ ngăn spam trigger khi scroll nhanh
            .collect { (lastIndex, total) ->
                if (lastIndex != null && total > 0 && lastIndex >= total - 2) {

                    viewModel.loadNextPage()
                }
            }
    }


    LaunchedEffect(navEvent) {
        when (val event = navEvent) {
            is ProductNavEvent.GoToProductsCategory -> {
                navHostController.navigate(NavRoutes.ProductByCategory.createRoute(event.categoryId))
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToProductDetail -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("id", event.id)
                    set("idUnit", event.idUnit)
                }
                navHostController.navigate(NavRoutes.ProductDetail.createRoute(event.id, event.idUnit))
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToProductEvaluates -> {
                navHostController.navigate(NavRoutes.Evaluates.createRoute(event.id))
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToAddServiceRequest -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("id", event.id)
                    set("serviceName", event.serviceName)
                    set("idUnit", event.idUnit)
                    set("discount", event.discount)
                }
                navHostController.navigate(NavRoutes.AddServiceRequest.route)
                viewModel.resetNavigation()
            }

            is NotificationNavEvent.GoToNotifications -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("startDate", event.startDate)
                    set("endDate", event.endDate)
                }
                navHostController.navigate(NavRoutes.Notifications.route)
                viewModel.resetNavigation()
            }

            else -> Unit
        }
    }



    Column(modifier = Modifier.fillMaxSize()) {
        type = productInfo?.loaichitiet?:""
        bookService = productInfo?.cothedatlich?:false
        TopTextNotificationShare(
            painter=painterResource(R.drawable.quaylai),

            placeholder =if(type=="huuhinh") stringResource(R.string.product_detail)else stringResource(R.string.service_detail),

            totalCartItems = totalCartItems,
            onShareClick = { viewModel.onItemNotificationSelected() },
            onCartClick = { },
                    onBackStack = { navHostController.popBackStack()}
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize().weight(1f)
                .background(FAFAFA)
        ) {
            item {
                if (productInfo!=null) {

                    val fullUrl = domain.trimEnd('/') + (productInfo?.filetxt ?: "")
                    NetworkImage(
                        imageUrl = fullUrl,
                        modifier = Modifier.fillMaxWidth().height(250.dp)
                    )
                }
            }
            item {
                if (productInfo!=null) {

                    var  isFlashSale: Boolean = false
                    val  discountPercent: Double = productInfo?.khuyenmai?:0.0
                    if(discountPercent!=0.0){
                        isFlashSale = true
                    }
                    ProductPriceDetailSection (
                        productName =productInfo?.ten?:"",
                        priceDisCount = productInfo?.sotiensaukm?:0.0,
                        price = productInfo?.sotien?:0.0,
                        discountPercent =productInfo?.khuyenmai?:0.0,
                        isFlashSale = isFlashSale,
                        remainingTime = "40:00:40:18",
                        quantity = 1,
                        onQuantityChange = { /* logic */ }
                    )

                }
            }


            stickyHeader {
                RatingHeader(
                    rating = ratingScore,
                    totalReviews = totalEvaluate,
                    onViewAllClick = { }
                )

            }
            item {
                if (evaluates.isNotEmpty()) {
                    EvaluateScreen(
                        evaluates = evaluates,
                        domain = domain,
                        onDownloadClick = { fileUrl ->
                            viewModel.downloadImage(fileUrl)
                        }

                    )
                }
            }

            if (productInfo!=null) {
                stickyHeader {
                    ProductDetailHeader(
                        type= productInfo?.loaichitiet?:"",
                        favorite= productInfo?.yeuthich?:0,
                        onFavorite = { },
                        onReportClick = { },
                        onWriteFeedbackClick = { viewModel.onItemEvaluatesSelected(id)}
                    )

                }
            }
            item {
                if (productInfo!=null) {
                    WebViewProduct(
                        htmlContent = productInfo?.noidung?:"",

                        )
                }
            }
//            val rows = pagedProducts.chunked(2)
//            items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
//                ProductRow(
//                    domain = domain,
//                    rowProducts = row,
//                    promoUiDataMap = promoUiDataMap,
//                    onItemClick = { viewModel.onItemProductSelected(it) },
//                    onClickCart = { viewModel.onItemCart(it) },
//                    onClickAddServiceRequest = { viewModel.onAddServiceRequestSelected(it) }
//                )
//            }


        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,


        ) {
            CustomButton(
                text = stringResource(id = R.string.add_to_cart),
                textColor = Color.White,
                containerColor = TealGreen,
                modifier = Modifier.weight(1f),
                roundedCornerShape = 10.dp,
                fontSize = 12.sp,
                onClick = {}
            )
            if(bookService){
                Spacer(modifier = Modifier.width(10.dp))
                CustomButton(
                    text = stringResource(id = R.string.bookService),
                    textColor = Color.White,
                    containerColor = FFFF9800,
                    modifier = Modifier.weight(1f),
                    roundedCornerShape = 10.dp,
                    fontSize = 12.sp,
                    onClick = {}
                )
            }

        }

    }
}