package com.contrast.Contrast.presentation.features.cart.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.EmptyStateScreen

import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertOkCancelDialog
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.profile.viewModel.ProfileViewModel
import com.contrast.Contrast.presentation.components.toast.CustomToast
import com.contrast.Contrast.presentation.components.toast.toastCollect

import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.product.detail.callApi
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen

import com.contrast.Contrast.presentation.theme.UltraLightGray
import com.itechpro.domain.model.ToastPosition
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

@Composable
fun CartScreen(
    navHostController: NavHostController,
    viewModel: CartViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {


    val navEvent by viewModel.navigationEvent.collectAsState()

    var isDeleteAll by remember { mutableStateOf(false) }
    var isOpportitue by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    var toastMessage by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {

        viewModel.onCheckedChangeAll(uiState.carts, uiState.isAllSelected)
    }
    LaunchedEffect(Unit) {

        viewModel.getCarts(true)
    }


    LaunchedEffect(navEvent) {
        when (val event = navEvent) {

            is CartNavEvent.GoToPayment -> {
                navHostController.navigate(
                    NavRoutes.Payment.withArgs(
                        totalIntoMoney = event.totalIntoMoney,
                        oderKey = event.oderKey,
                        idOder = event.idOder,
                        discount = event.discount,
                        address = event.address,
                        isOpportunity = event.isOpportunity,

                        )
                )
                viewModel.resetNavigation()
            }



            else -> Unit
        }
    }

    viewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getCarts(true)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
   Box {  Column(
       modifier = Modifier
           .fillMaxSize()
           .background(FFFAFAFA) // nền xám nhạt
   ) {
       // Header
       CartHeader(onBackPress = { navHostController.popBackStack() }, onBackHome = {

       })


       // Địa chỉ nhận hàng
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .background(Color.White)
               .padding(vertical = 6.dp, horizontal = 10.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Icon(Icons.Default.LocationOn, contentDescription = null, tint = TealGreen)
           Spacer(modifier = Modifier.width(6.dp))


           BasicTextField(value = address,

               onValueChange = { address = it },
               modifier = Modifier
                   .fillMaxWidth()
                   .background(Color.White)
                   .padding(8.dp),
               decorationBox = { innerTextField ->
                   if (address.isEmpty()) {
                       Text(
                           text = stringResource(R.string.cart_delivery_address),
                           color = Color.Gray
                       )
                   }
                   innerTextField()
               })
       }


       // Danh sách sản phẩm
       if (uiState.carts.isNotEmpty()) {

           // Chọn tất cả / Xóa tất cả
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .background(Color(0xFFFAFAFA))
                   .padding(10.dp),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ) {
               Row(verticalAlignment = Alignment.CenterVertically) {
                   CheckBoxColor(checked = uiState.isAllSelected,
                       padding = 8.dp,
                       size = 15.dp,
                       backgroundChecked = TealGreen,
                       backgroundUnChecked = TealGreen,
                       onCheckedChange = { isChecked ->
                           viewModel.isAllSelected(uiState.carts, isChecked)
                       })
                   Spacer(modifier = Modifier.width(6.dp))
                   Text(stringResource(R.string.cart_select_all),
                       color = TealGreen,
                       modifier = Modifier.noRippleClickableComposable {
                           viewModel.isAllSelected(uiState.carts, !uiState.isAllSelected)
                       }

                   )
               }
               Text(text = stringResource(R.string.cart_delete_all),
                   color = Color.Red,
                   modifier = Modifier.noRippleClickableComposable { isDeleteAll = true })
           }


           LazyColumn(
               modifier = Modifier
                   .weight(1f)
                   .background(Color(0xFFFAFAFA))
                   .padding(horizontal = 10.dp),
           ) {
               items(uiState.carts) { cart ->

                   CartItemRow(
                       cart = cart,
                       domain = uiState.domain,
                       onCheckedChange = {},
                       increaseQuantity = { viewModel.increaseQuantity(cart, "update") },
                       onQuantityChange = { viewModel.onQuantityChange(cart, "update", it) },
                       decreaseQuantity = { viewModel.decreaseQuantity(cart, "update") },
                   )
                   Spacer(modifier = Modifier.height(12.dp))
               }
           }
       } else {
           Box(modifier = Modifier.weight(1f)) {

               EmptyStateScreen(
                   imageRes = R.drawable.emptycart,
                   size = 90.dp,
                   title = stringResource(R.string.empty_cart),
                   background = FFFAFAFA,
                   modifier = Modifier.padding(40.dp)
               )
           }
       }

       // Ghi chú đơn hàng
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .background(Color.White)
               .padding(horizontal = 12.dp, vertical = 8.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {


           Image(
               painter = painterResource(R.drawable.note_pad), contentDescription = null,

               modifier = Modifier.size(20.dp)
           )
           Spacer(modifier = Modifier.width(6.dp))
           BasicTextField(value = note,

               onValueChange = { note = it },
               modifier = Modifier
                   .fillMaxWidth()
                   .background(Color.White)
                   .padding(8.dp),
               decorationBox = { innerTextField ->
                   if (note.isEmpty()) {
                       Text(
                           text = stringResource(R.string.cart_item_note), color = Color.Gray
                       )
                   }
                   innerTextField()
               })
       }


       // Tổng tiền + nút
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .background(Color.White)

       ) {
           RowAmount(
               stringResource(R.string.cart_total_amount), "", uiState.totalValue.formatDouble(), true
           )
           RowAmount(
               stringResource(R.string.cart_discount),
               "-",
               uiState.amountMoneyDiscount.formatDouble(),
               textColor = FFFF5722
           )
           RowAmount(
               stringResource(R.string.cart_final_amount), "",uiState. totalIntoMoney.formatDouble(), true
           )



           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(top = 5.dp),
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               Button(
                   onClick = { isOpportitue = true},
                   modifier = Modifier
                       .weight(1f)
                       .height(44.dp),
                   shape = RoundedCornerShape(0.dp),
                   colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
               ) {
                   Text(
                       text = stringResource(R.string.cart_need_consultation), color = Color.White
                   )
               }

               Button(
                   onClick = {

                       var  customerId = ""
                       if(uiState.typeAccount=="daily"){
                           customerId =uiState.employeeId
                       }else{
                           customerId = uiState.customerId
                       }
                       viewModel.payment(uiState.typeAccount,address,customerId, note,uiState.carts,isOpportitue)

                   },
                   modifier = Modifier
                       .weight(1f)
                       .height(44.dp),
                   shape = RoundedCornerShape(0.dp),
                   colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
               ) {
                   Text(text = stringResource(R.string.cart_checkout), color = Color.White)
               }
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

       } }

    if (isDeleteAll) {
        CustomAlertOkCancelDialog(
            message = stringResource(R.string.you_want_to_deleta_all_cart),
            onOk = {
                isDeleteAll = false
                viewModel.deleteAll(uiState.carts)
            },
            onDismiss = { isDeleteAll = false },
        )

    }
}
