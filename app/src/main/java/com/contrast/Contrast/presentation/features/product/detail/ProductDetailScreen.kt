package com.contrast.Contrast.presentation.features.product.detail




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.media.NetworkImage

import com.contrast.Contrast.presentation.components.searchBar.TopTextNotificationShare

import com.contrast.Contrast.presentation.features.cart.CartViewModel

import com.contrast.Contrast.presentation.features.review.ReviewViewModel

import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductDetailHeader
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductPriceDetailSection
import com.contrast.Contrast.presentation.features.product.detail.ui.WebViewProduct
import com.contrast.Contrast.presentation.features.product.share.ShareBottomSheet

import com.contrast.Contrast.presentation.features.product.viewmodel.ProductViewModel

import com.contrast.Contrast.presentation.features.review.ReviewScreen
import com.contrast.Contrast.presentation.features.review.ui.ReviewHeader
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA

import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.utils.NetworkMonitor
import com.itechpro.domain.model.navigationEvent.CartNavEvent

import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailScreen(
    navHostController: NavHostController,
    id: String,
    idUnit: String,
    introducerId: String,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel()
) {
    val productInfo by viewModel.productInfo.collectAsState()

    val products by viewModel.products.collectAsState()
    val pagedProducts by viewModel.pagedProducts.collectAsState()
    val totalReview by reviewViewModel.totalReview.collectAsState()
    val ratingScore by reviewViewModel.ratingScore.collectAsState()
    val reviews by reviewViewModel.reviews.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val employeeId by viewModel.employeeId.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val promoUiDataMapInfo = viewModel.promoUiDataMapInfo
    val totalCartItems by cartViewModel.totalCartItems.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isOnline by NetworkMonitor.isOnline.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("huuhinh") }
    var bookService by remember { mutableStateOf(false) }
    var shareClickDialog by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf(0) }

    val navEvent by viewModel.navigationEvent.collectAsState()

    val listState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        viewModel.shareIntentFlow.collectLatest { intent ->
//            context.startActivity(intent)
//        }
//    }

    LaunchedEffect(products) { viewModel.setInitialProducts(products) }



    LaunchedEffect(Unit) {
        delay(100) // cho hệ thống khởi động mạng nếu vừa chuyển 4G
        viewModel.loadData(id, idUnit)
        reviewViewModel.loadReview(id, "3")
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
            is NotificationNavEvent.GoToNotifications -> {
                navHostController.navigate(
                    NavRoutes.Notifications.withArgs(
                        startDate = event.startDate,
                        endDate = event.endDate
                    )
                )
                viewModel.resetNavigation()
            }
            is CartNavEvent.GoToCats -> {
                navHostController.navigate(NavRoutes.Carts.route)
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToProductDetail -> {
                navHostController.navigate(
                    NavRoutes.ProductDetail.withArgs(
                        id = event.id,
                        idUnit = event.idUnit,
                        introducerId = event.introducerId,

                        )
                )
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToProductReviews -> {
                navHostController.navigate(
                    NavRoutes.Reviews.withArgs(
                        id = event.id,

                    )
                )
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToAddReviews -> {
                navHostController.navigate(
                    NavRoutes.AddReview.withArgs(
                        id = event.id,
                        fileTxt = event.fileTxt,
                        name = event.name
                    )
                )
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToAddServiceRequest -> {
                navHostController.navigate(
                    NavRoutes.AddServiceRequest.withArgs(
                        id = event.id,
                        serviceName = event.serviceName,
                        idUnit = event.idUnit,
                        discount = event.discount
                    )
                )
                viewModel.resetNavigation()
            }


            else -> Unit
        }
    }

    if(shareClickDialog){
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                ShareBottomSheet(
                    onDismissRequest = {
                        coroutineScope.launch { sheetState.hide() }
                    },
                    onShareClick = { option ->

                        val baseUrl = "$domain/sharelink.html?"
                        val queryParams = "id=$id&iddonvi=$idUnit&idngt=$employeeId&domain=$domain"
                        val shareLink = "$baseUrl$queryParams"
                        coroutineScope.launch { sheetState.hide() }
                        viewModel.share(
                            option = option,
                            shareUrl = shareLink
                        )

                    }
                )
            }
        ) {

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
            onCartClick = { viewModel.onItemCarts()},
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
                        model = fullUrl,
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
                        quantity = productInfo?.soluong?:1.0,
                        increaseQuantity={viewModel.increaseProductDetailQuantity(productInfo!!)},
                        decreaseQuantity={viewModel.decreaseProductDetailQuantity(productInfo!!)},
                    )

                }
            }


            stickyHeader {
                ReviewHeader(
                    rating = ratingScore,
                    totalReviews = totalReview,
                    onViewAllClick = {viewModel.onItemReviewsSelected(id) }
                )

            }
            item {
                if (reviews.isNotEmpty()) {
                    ReviewScreen(
                        reviews = reviews,
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
                        onWriteFeedbackClick = { viewModel.onItemAddReviewsSelected(id, "$domain${productInfo?.filetxt?:""}", productInfo?.ten?:"")}
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
                onClick = { cartViewModel.onItemAddCartToProductDetail(productInfo!!,introducerId)}
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