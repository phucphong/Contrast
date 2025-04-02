package com.contrast.Contrast.presentation.components.category

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.category.viewmodel.CategoryViewModel
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.searchBar.SearchBar

import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFFCFCFC
import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.itechpro.domain.enumApp.CategoryType
@Composable
fun CategoryDialog(
    title: String,
    typeCheck: CategoryType,
    searchLocal: Boolean = true, // ✅ mặc định là false
    preSelectedId:String ="",
    onOptionSelectedId: (String) -> Unit,
    onOptionSelectedName: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val viewModel: CategoryViewModel = hiltViewModel()

    // Gọi API ban đầu
    LaunchedEffect(Unit) {
        viewModel.setSearch("") // ✅ Reset lại searchText khi mở dialog
        viewModel.getCategory("", typeCheck)
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val search by remember { derivedStateOf { viewModel.searchText } }
    val categories by if (searchLocal) {
        viewModel.filteredCategories.collectAsState()
    } else {
        viewModel.categories.collectAsState()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = FFFCFCFC,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(text = title, textAlign = TextAlign.Center, fontSize = 16.sp)

                Spacer(Modifier.size(20.dp))

                SearchBar(
                    searchText = search,
                    backgroundColor = FFFFFFFF,

                    placeholder = stringResource(R.string.search_placeholder),
                    onTextChange = { newText ->
                        viewModel.setSearch(newText)

                        if (!searchLocal) {
                            viewModel.getCategory(newText, typeCheck)
                        }
                    }
                )

                Spacer(Modifier.size(20.dp))
                Column(Modifier.height(300.dp)) {
                when {
                    isLoading -> {
                        CustomCircularProgressIndicator()
                    }
                    categories.isEmpty() -> {
                        EmptyStateScreen(
                            imageRes = R.drawable.nodata,
                            size = 60.dp,
                            title = stringResource(R.string.no_data),
                        )
                    }
                    else -> {
                                            LazyColumn {
                        items(categories) { category ->
                            Column {
                                Text(
                                    text = category.name ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                        .noRippleClickableComposable {
                                            onOptionSelectedId(category.id.orEmpty())
                                            onOptionSelectedName(category.name.orEmpty())
                                            onDismiss()
                                        }
                                )
                                CustomDividerColor()
                            }
                        }
                    }
                    }
                }}


                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = stringResource(id = R.string.close),
                    textColor = Color.White,
                    containerColor = FFAFAFAF,
                    paddingStart = 5.dp,
                    paddingTop = 20.dp,
                    paddingEnd = 5.dp,
                    paddingBottom = 5.dp,
                    roundedCornerShape = 10.dp,
                    onClick = { onDismiss() }
                )
            }
        }
    }
}
