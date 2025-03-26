package com.contrast.Contrast.presentation.features.feedback

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FCFCFC

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FeedbackDialog( showDialog: Boolean, onDismiss: () -> Unit, onContinue: (String) -> Unit) {
    var showDialogBranch by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            backgroundColor = FCFCFC,
            shape = RoundedCornerShape(12.dp),
            buttons = {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Header + Close Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f))

                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(R.string.close))
                        }
                    }

                    Text(
                        text = stringResource(R.string.feedback_title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Dropdown Selection
                    var selectedBranch by remember { mutableStateOf("") }
                    var expanded by remember { mutableStateOf(false) }
                    val branchList = listOf("Văn Chương", "Tô Hiệu", "Trường Chinh", "Vũ Tông Phan")

                    Text(
                        text = stringResource(R.string.choose_branch),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF1E1E1E),
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        // Tạo nền trắng với viền 1dp
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, FFAFAFAF, shape = RoundedCornerShape(8.dp))
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable { expanded = true }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Hiển thị text đã chọn hoặc placeholder
                            Text(
                                text = if (selectedBranch.isNotEmpty()) selectedBranch else stringResource(R.string.select_branch),
                                modifier = Modifier.weight(1f)
                            )

                            // Icon xổ lên/xuống
                            Icon(
                                painter = painterResource(
                                    if (expanded) R.drawable.up else R.drawable.down
                                ),
                                contentDescription = "Dropdown icon"
                            )
                        }

                        // Dropdown Menu
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            branchList.forEach { branch ->
                                val isSelected = selectedBranch == branch

                                DropdownMenuItem(
                                    onClick = {
                                        selectedBranch = branch
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f) // Giới hạn chiều rộng 80%
                                        .background(if (isSelected) Color.Red else Color.Transparent)
                                ) {
                                    Text(
                                        text = branch,
                                        fontSize = 14.sp,
                                        style = TextStyle(
                                            color = if (isSelected) Color.White else Color.Black
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(100.dp))

                    // Continue Button
                    Button(
                        onClick = { onContinue(selectedBranch)

                            showDialogBranch= true},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        enabled = selectedBranch.isNotEmpty()
                    ) {
                        Text(
                            text = stringResource(R.string.continue_button),
                            color = Color.White,
                            modifier = Modifier.padding(5.dp)
                        )
                    }


                    // Hiển thị Dialog khi `showDialog = true`
                    FeedbackBranchDialog (
                        showDialog = showDialog,
                        branchName = selectedBranch,
                        onDismiss = { showDialogBranch = false },
                        onSendFeedback = { feedback ->
                            showDialogBranch = false
                            println("Người dùng đã gửi phản hồi: $feedback") // Xử lý phản hồi
                        }
                    )
                }
            }
        )
    }
}

