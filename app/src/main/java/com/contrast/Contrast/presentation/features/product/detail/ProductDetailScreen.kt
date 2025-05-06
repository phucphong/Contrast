package com.contrast.Contrast.presentation.features.product.detail


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.isLimitedAccessGranted
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.alertDialog.QuantityAlertDialog
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.media.MediaPermissionStatus
import com.contrast.Contrast.presentation.components.media.NetworkImage


import com.contrast.Contrast.presentation.components.searchBar.TopTextNotificationShare
import com.contrast.Contrast.presentation.components.toast.CollectToast
import com.contrast.Contrast.presentation.components.toast.CustomToast
import com.contrast.Contrast.presentation.components.toast.toastCollect
import com.contrast.Contrast.presentation.components.webview.WebViewProductXml

import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.login.ui.LoginActivity

import com.contrast.Contrast.presentation.features.review.ReviewViewModel

import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductDetailHeader
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductPriceDetailSection
import com.contrast.Contrast.presentation.features.product.detail.ui.WebViewProduct
import com.contrast.Contrast.presentation.features.product.ui.ProductRow


import com.contrast.Contrast.presentation.features.product.viewmodel.ProductViewModel

import com.contrast.Contrast.presentation.features.review.ReviewScreen
import com.contrast.Contrast.presentation.features.review.ui.ReviewHeader
import com.contrast.Contrast.presentation.features.share.ShareDialog
import com.contrast.Contrast.presentation.features.splas.SplashNavigation
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA


import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen

import com.itechpro.domain.model.ToastPosition
import com.itechpro.domain.model.navigationEvent.CartNavEvent
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
    val productsCategory by viewModel.productsCategory.collectAsState()

    val pagedProducts by viewModel.pagedProducts.collectAsState()
    val totalReview by reviewViewModel.totalReview.collectAsState()
    val ratingScore by reviewViewModel.ratingScore.collectAsState()
    val reviews by reviewViewModel.reviews.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val employeeId by viewModel.employeeId.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val promoUiDataMapInfo = viewModel.promoUiDataMapInfo
    val totalCartItems by cartViewModel.totalCartItems.collectAsState()
    val isOfflineMode by viewModel.isOfflineMode.collectAsState()

    var type by remember { mutableStateOf("huuhinh") }
    var bookService by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    var isShowQuantity by remember { mutableStateOf(false) }
    val navEvent by viewModel.navigationEvent.collectAsState()
    val listState = rememberLazyListState()
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
        callApi(viewModel, reviewViewModel, cartViewModel, id, idUnit)
    }
    LaunchedEffect(productInfo) {

        viewModel.getProductsCategory(productInfo?.idnhom ?: "", id)

    }
    var toastMessage by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }

// Thu thập từ nhiều ViewModel
    viewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }

    cartViewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            Log.d("LifecycleObserver", "Event = $event") // ✅ Log ra xem
            if (event == Lifecycle.Event.ON_RESUME) {
                Log.d("LifecycleObserver", "Calling API")
                callApi(viewModel, reviewViewModel, cartViewModel, id, idUnit)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }.distinctUntilChanged().debounce(300) // ✅ ngăn spam trigger khi scroll nhanh
            .collect { (lastIndex, total) ->
                if (lastIndex != null && total > 0 && lastIndex >= total - 2) {

                    viewModel.loadNextPage()
                }
            }
    }
    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.shareIntentFlow.collectLatest { intent ->
            context.startActivity(intent)
        }
    }
    LaunchedEffect(navEvent) {
        when (val event = navEvent) {


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
                        idUnit = event.idUnit,
                        fileTxt = event.fileTxt,
                        name = event.name

                    )
                )
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToReportProduct -> {
                navHostController.navigate(
                    NavRoutes.AddReportProduct.withArgs(

                        id = event.id,
                        idUnit = event.idUnit,
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

    if (showShareDialog) {
        ShareDialog(visible = showShareDialog,
            onDismissRequest = { showShareDialog = false },
            onShareClick = { option ->
                showShareDialog = false
                val shareLink =
                    "$domain/sharelink.html?id=$id&iddonvi=$idUnit&idngt=$employeeId&domain=$domain"
                viewModel.share(option, shareLink)

            })

    }

    if (isShowQuantity) {

        QuantityAlertDialog(title = stringResource(R.string.quantity),
            quantity = productInfo?.soluong ?: 0.0,
            onQuantityChange = {
                viewModel.updateQuantityProductDetailById(productInfo!!, it)
            },
            onConfirm = {
                isShowQuantity = false
            }, // ✅ Đóng Dialog khi bấm OK
            onDismiss = {
                isShowQuantity = false
            } // ✅ Đóng Dialog khi bấm OK
        )

    }
    Box {

        Column(modifier = Modifier.fillMaxSize()) {
            type = productInfo?.loaichitiet ?: ""
            bookService = productInfo?.cothedatlich ?: false

            var isFavoriteInit: Boolean = false

            val favorite = productInfo?.yeuthich ?: 0
            if (favorite == 0) {
                isFavoriteInit = false
            } else {
                isFavoriteInit = true
            }
            TopTextNotificationShare(painter = painterResource(R.drawable.quaylai),

                placeholder = if (type == "huuhinh") stringResource(R.string.product_detail) else stringResource(
                    R.string.service_detail
                ),

                totalCartItems = totalCartItems,
                onShareClick = {
                    val shareLink =
                        "$domain/sharelink.html?id=$id&iddonvi=$idUnit&idngt=$employeeId&domain=$domain"
                    viewModel.shareProduct(shareLink)
//                showShareDialog = true
                    Log.e("showShareDialog", "showShareDialog")
                },
                onCartClick = { viewModel.onItemCarts() },
                onBackStack = { navHostController.popBackStack() })

            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(FAFAFA)
            ) {
                item {
                    if (productInfo != null) {

                        val fullUrl = domain.trimEnd('/') + (productInfo?.filetxt ?: "")
                        NetworkImage(
                            model = fullUrl, modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                        )
                    }
                }
                item {
                    if (productInfo != null) {

                        var isFlashSale: Boolean = false
                        val discountPercent: Double = productInfo?.khuyenmai ?: 0.0
                        if (discountPercent != 0.0) {
                            isFlashSale = true
                        }
                        ProductPriceDetailSection(
                            productName = productInfo?.ten ?: "",
                            priceDisCount = productInfo?.sotiensaukm ?: 0.0,
                            price = productInfo?.sotien ?: 0.0,
                            discountPercent = productInfo?.khuyenmai ?: 0.0,
                            isFlashSale = isFlashSale,
                            remainingTime = "40:00:40:18",
                            quantity = productInfo?.soluong ?: 1.0,
                            increaseQuantity = { viewModel.increaseProductDetailQuantity(productInfo!!) },
                            showQuantity = { isShowQuantity = true },
                            decreaseQuantity = { viewModel.decreaseProductDetailQuantity(productInfo!!) },
                        )

                    }
                }


                stickyHeader {
                    ReviewHeader(rating = ratingScore,
                        totalReviews = totalReview,
                        onViewAllClick = { viewModel.onItemReviewsSelected(id) })

                }
                item {
                    if (reviews.isNotEmpty()) {
                        ReviewScreen(reviews = reviews,
                            domain = domain,
                            onDownloadClick = { fileUrl ->
                                viewModel.downloadImage(fileUrl)
                            }

                        )
                    }
                }

                if (productInfo != null) {
                    stickyHeader {
                        ProductDetailHeader(type = productInfo?.loaichitiet ?: "",
                            isFavoriteInit = isFavoriteInit,
                            isOfflineMode = isOfflineMode,
                            onFavorite = {

                                viewModel.onFavorite(it, id, idUnit)

                            },
                            onReportClick = {
                                viewModel.onItemReportSelected(
                                    id,
                                    idUnit,
                                    "$domain${productInfo?.filetxt ?: ""}",
                                    productInfo?.ten ?: ""
                                )

                            },

                            onWriteReviewClick = {
                                viewModel.onItemAddReviewsSelected(
                                    id,
                                    idUnit,
                                    "$domain${productInfo?.filetxt ?: ""}",
                                    productInfo?.ten ?: ""
                                )
                            })


                    }
                }
                item {
                    if (productInfo != null) {
                        WebViewProduct(htmlContent = productInfo?.noidung ?: "")
                    }
                }

                stickyHeader {
                    Text(
                        text = if (type == "huuhinh") stringResource(R.string.product_category) else stringResource(
                            R.string.service_category
                        ),
                        fontWeight = FontWeight(450),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp)
                            .fillMaxWidth()
                    )


                }
                val rows = productsCategory.chunked(2)
                items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                    ProductRow(domain = domain,
                        rowProducts = row,
                        promoUiDataMap = promoUiDataMap,
                        onItemClick = { viewModel.onItemProductSelected(it) },
                        onClickCart = { cartViewModel.onItemAddCart(it) },
                        onClickAddServiceRequest = { viewModel.onAddServiceRequestSelected(it) })
                }

                stickyHeader {
                    Text(
                        text = if (type == "huuhinh") stringResource(R.string.product_other) else stringResource(
                            R.string.service_other
                        ),
                        fontWeight = FontWeight(450),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp)
                            .fillMaxWidth()
                    )


                }
                val rowsOther = pagedProducts.chunked(2)
                items(rowsOther, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                    ProductRow(domain = domain,
                        rowProducts = row,
                        promoUiDataMap = promoUiDataMap,
                        onItemClick = { viewModel.onItemProductSelected(it) },
                        onClickCart = { cartViewModel.onItemAddCart(it) },
                        onClickAddServiceRequest = { viewModel.onAddServiceRequestSelected(it) })
                }


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,


                ) {
                CustomButton(text = stringResource(id = R.string.add_to_cart),
                    textColor = Color.White,
                    containerColor = TealGreen,
                    modifier = Modifier.weight(1f),
                    roundedCornerShape = 10.dp,
                    fontSize = 12.sp,
                    onClick = {
                        cartViewModel.onItemAddCartToProductDetail(
                            productInfo!!, introducerId
                        )
                    })
                if (bookService) {
                    Spacer(modifier = Modifier.width(10.dp))
                    CustomButton(text = stringResource(id = R.string.bookService),
                        textColor = Color.White,
                        containerColor = FFFF9800,
                        modifier = Modifier.weight(1f),
                        roundedCornerShape = 10.dp,
                        fontSize = 12.sp,
                        onClick = {})
                }

            }

        }
        if (showToast) {
            CustomToast(
                message = toastMessage,
                textAlign = TextAlign.Center,
                background = FFFFFFFF,
                textColor = Color.Black,
                showToast = true,
                toastPosition = ToastPosition.CENTER,
                durationMillis = 2000,
                onDismiss = {
                    showToast = false
                },
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .wrapContentHeight()
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp)) // ✅ Đổ bóng
                    .clip(RoundedCornerShape(8.dp)) // ✅ Bo góc sau khi đổ bóng
                    .background(FFFFFFFF) // ✅ Bắt buộc: set lại màu nền sau khi clip
                    .border(1.dp, FFFFFFFF, RoundedCornerShape(8.dp))

            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun callApi(
    viewModel: ProductViewModel,
    reviewViewModel: ReviewViewModel,
    cartViewModel: CartViewModel,
    id: String,
    idUnit: String
) {
    viewModel.loadData(id, idUnit)
    reviewViewModel.loadReview(id, "3")
    cartViewModel.getCarts(false)
    viewModel.getProductsOther(id)
}

