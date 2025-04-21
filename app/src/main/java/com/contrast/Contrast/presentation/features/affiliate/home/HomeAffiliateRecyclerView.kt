package com.contrast.Contrast.presentation.features.affiliate.home

import androidx.recyclerview.widget.RecyclerView
import com.contrast.Contrast.presentation.features.affiliate.home.adapter.HomeAffiliateAdapter
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeAffiliateRecyclerView(
    viewModel: HomeAffiliateViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

//    AndroidView(
//        modifier = modifier,
//        factory = { ctx ->
//            val recyclerView = RecyclerView(ctx).apply {
//                layoutManager = LinearLayoutManager(ctx)
//                itemAnimator = null
//            }
//
//            val adapter = HomeAffiliateAdapter(
//                promoUiDataMap = viewModel.promoUiDataMap.value,
//                onItemClick = { viewModel.onItemProductSelected(it) },
//                onClickCart = { viewModel.onItemCart(it) },
//                onClickAddServiceRequest = { viewModel.onAddServiceRequestSelected(it) },
//                onCategorySelected = { index, tabs ->
//                    viewModel.onCategorySelected(index, tabs)
//                }
//            )
//            recyclerView.adapter = adapter
//
//            // Observe sections
//            lifecycleOwner.lifecycleScope.launch {
//                viewModel.sections.collectLatest {
//                    adapter.submitList(it)
//                }
//            }
//
//            recyclerView
//        }
//    )
}
