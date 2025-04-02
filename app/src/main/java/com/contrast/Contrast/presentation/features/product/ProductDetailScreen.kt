package com.contrast.Contrast.presentation.features.product


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.product.viewmodel.ProductDetailViewModel
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FF637875
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.itechpro.domain.model.product.OptionItem
import androidx.compose.foundation.layout.Row as Row1


@Preview(showBackground = true)
@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel = viewModel()) {
    var selectedSize by remember { mutableStateOf("Size M") }
    var selectedSugar by remember { mutableStateOf("Ngọt bình thường") }
    var selectedIce by remember { mutableStateOf("Đá bình thường") }
    var selectedToppings by remember { mutableStateOf(setOf<String>()) }
    var note by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(2) }

    val basePrice = 59000
    val toppingPrice =
        viewModel.toppings.filter { selectedToppings.contains(it.id) }.sumOf { it.price }
    val totalPrice = (basePrice + toppingPrice) * quantity

    Column(modifier = Modifier
        .fillMaxSize()
        .background(FCFCFC)) {
        val scrollState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color.Transparent),
            state = scrollState,
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Box() {

                    Image(
                        painter = painterResource(R.drawable.product_image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                        painter = painterResource(R.drawable.back),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .noRippleClickableComposable {



                            },
                    )
                        Text(
                            text = "Đồ uống",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFD91E18),
                            letterSpacing = 0.38.sp,
                        ), modifier = Modifier.padding(horizontal = 10.dp).weight(1f)
                        )
                        Box(
                                Modifier
                                .width(45.63665.dp)
                            .height(45.34675.dp)
                            .background(color = Color(0x82343434), shape = RoundedCornerShape(size = 94.10112.dp))
                            .padding(5.dp)){
                            Box(

                            ) {
                                // Icon túi
                                Image(
                                    painter = painterResource(R.drawable.bag_black),
                                    contentDescription = "cart",
                                    modifier = Modifier.align(Alignment.Center).size(52.dp).padding(top = 8.dp, end = 5.dp)
                                )

                                // ✅ Badge đỏ
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.TopEnd)
                                        .offset(x = (-3).dp, y = 3.dp) // ✅ đẩy nhẹ vào trong
                                        .background(FFD91E18, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "15",
                                        style = TextStyle(
                                            fontSize = 9.sp,
                                            lineHeight = 9.sp,
                                            fontFamily = FontFamily(Font(R.font.inter)),
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        ),
                                        maxLines = 1,
                                        modifier = Modifier.wrapContentSize(Alignment.Center)
                                    )
                                }

                            }


                        }

                    }

                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Nhãn Hoa Mộc Tê", style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),

                            letterSpacing = 0.38.sp,
                        )
                    )
                    Text(
                        "59.000 đ", style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFF4747),

                            ), modifier = Modifier.padding(vertical = 5.dp)
                    )
                    Text(
                        "Lorem ipsum is simply dummy text of the printing and typesetting industry...",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = FF637875,
                            textAlign = TextAlign.Justify,
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            item {
                OptionGroup("Size", viewModel.sizes.map { it.label }, selectedSize) {
                    selectedSize = it
                }
            }

            item {
                OptionGroup(
                    "Chọn mức đường",
                    viewModel.sugarLevels.map { it.label },
                    selectedSugar
                ) { selectedSugar = it }
            }

            item {
                OptionGroup(
                    "Chọn mức đá",
                    viewModel.iceLevels.map { it.label },
                    selectedIce
                ) { selectedIce = it }
            }

            item {
                ToppingGroup(viewModel.toppings, selectedToppings) { selectedToppings = it }
            }

            item {

                Column(modifier = Modifier.fillMaxWidth()) {

                    CustomDividerColor(padding = 10.dp)
                    Text(
                        text = "Lưu ý cho món",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333),
                        ),
                        modifier = Modifier.padding(  8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp).padding(10.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFF6F6F6),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Image(
                            painter = painterResource(R.drawable.solid_pen),
                            contentDescription = "edit icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(15.dp)
                        )

                        BasicTextField(
                            value = note,
                            onValueChange = { note = it },
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp),
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF333333)
                            ),
                            decorationBox = { innerTextField ->
                                if (note.isEmpty()) {
                                    Text(
                                        text = "Nhập thông tin tại đây",
                                        color = Color(0xFFBDBDBD),
                                        fontSize = 14.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()

                .background(Color.White)
                .border(0.dp, Color.White, shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
        ) {

            Text(
                text = "2 sản phẩm",
                modifier = Modifier.padding(horizontal = 10.dp),
                style = TextStyle(
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF615F5F),
                )
            )
            Row1(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    "Tổng: ${totalPrice.formatCurrency()}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF333333),

                        )
                )

                Row1(verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painter = painterResource(R.drawable.minus_square),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .noRippleClickableComposable {
                                if (quantity > 1) quantity--


                            },
                    )


                    Text(
                        quantity.toString(), style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.25.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3C3C3C),

                            textAlign = TextAlign.Center,
                        ), modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.plus_square_black),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .noRippleClickableComposable {
                                quantity++


                            },
                    )

                }
            }

            CustomButton(stringResource(id = R.string.addcart), onClick = {
//                    isRegisterButton = true;
//                    viewModel.validateAndRegister(phoneNumber, fullName, password, confirmPassword, email,"off")
//

            })
        }
    }
}

@Composable
fun OptionGroup(
    title: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            title, style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),

                ), modifier = Modifier.padding(horizontal = 8.dp)
        )
        options.forEach { item ->
            Row1(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelected(item) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 19.5.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF424242),

                        )
                )
                CheckBoxColor(checked = selected == item, onCheckedChange = { onSelected(item) })

            }
        }
    }
}

@Composable
fun ToppingGroup(
    toppings: List<OptionItem>,
    selected: Set<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            "Thêm Topping", style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),

                ), modifier = Modifier.padding(horizontal = 8.dp)
        )
        toppings.forEach { item ->
            val isChecked = selected.contains(item.id)
            Row1(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val updated = selected.toMutableSet()
                        if (isChecked) updated.remove(item.id) else updated.add(item.id)
                        onSelectionChange(updated)
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    Text(
                        text = item.label, style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 19.5.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF424242),

                            )
                    )
                    if (item.price > 0) {
                        Text(
                            "${item.price.formatCurrency()}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFF25252),

                                ), modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }


                CheckBoxColor(checked = isChecked, onCheckedChange = { null })


            }
        }
    }
}
