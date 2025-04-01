package com.contrast.Contrast.presentation.components.category
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.category.viewmodel.CategoryViewModel
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.itechpro.domain.model.Category

@Composable
fun CategoryDialog(
    title: String,
    options: List<Category>,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val viewModel: CategoryViewModel = hiltViewModel()
    val categories = viewModel.filteredCategories
    val search by remember { derivedStateOf { viewModel.searchText } }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Title
                CustomText(text = title)



                CustomTextField(
                    value = search,
                    onValueChange = { viewModel::setSearch },
                    placeholder = stringResource(R.string.search_placeholder),

                    )


                LazyColumn {
                    items(options) { category ->
                        Text(
                            text = category.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .noRippleClickableComposable {
                                    onOptionSelected(category.id.toString())
                                    onDismiss()
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Close button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Đóng")
                }
            }
        }
    }
}
