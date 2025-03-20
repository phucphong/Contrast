package com.contrast.Contrast.presentation.features.feedback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.contrast.Contrast.presentation.theme.FFFEF4F4

@Composable
fun FeedbackBranchDialog(showDialog: Boolean, branchName: String, onDismiss: () -> Unit, onSendFeedback: (String) -> Unit) {
    if (showDialog) {
        var selectedIssue by remember { mutableStateOf<String?>(null) }
        var feedbackText by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,
            backgroundColor = Color.White,
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

                    // Tiêu đề phản hồi
                    Text(
                        text = stringResource(R.string.feedback_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    // Cơ sở được chọn
                    Text(
                        text = stringResource(R.string.branch_name_placeholder, branchName),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Câu hỏi "Bạn đang gặp vấn đề về"
                    Text(
                        text = stringResource(R.string.issue_prompt),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Các lựa chọn vấn đề (Nhân viên, Không gian, Đồ uống)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start // 🔥 Canh trái thay vì dàn đều
                    ) {
                        val issues = listOf(
                            stringResource(R.string.issue_staff),
                            stringResource(R.string.issue_space),
                            stringResource(R.string.issue_drink)
                        )

                        issues.forEachIndexed { index, issue ->
                            Button(
                                onClick = { selectedIssue = issue },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (selectedIssue == issue) FFFEF4F4 else Color.White // 🔥 Chỉ đổi màu khi được chọn
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(0.5.dp, if (selectedIssue == issue) Color.Red else FF7C7C7C), // 🔥 Viền đỏ khi chưa chọn
                                modifier = Modifier
                                    .padding(end = if (index != issues.lastIndex) 8.dp else 0.dp) // 🔥 Khoảng cách giữa các nút
                            ) {
                                Text(
                                    text = issue,
                                    color = if (selectedIssue == issue) Color.Red else FF7C7C7C // 🔥 Chữ đỏ khi chưa chọn, trắng khi đã chọn
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp)) // 🔥 Viền xám nhạt
                            .background(Color.White, shape = RoundedCornerShape(8.dp)) // 🔥 Nền trắng
                            .padding(12.dp) // 🔥 Padding tổng thể
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            // 🔥 Icon bút chì bên trái
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pencil),
                                contentDescription = "Edit",
                                tint = Color.Gray,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            // 🔥 Ô nhập phản hồi
                            BasicTextField(
                                value = feedbackText,
                                onValueChange = { feedbackText = it },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Black
                                ),
                                minLines = 3,
                                decorationBox = { innerTextField ->
                                    if (feedbackText.isEmpty()) {
                                        Text(
                                            text = stringResource(R.string.feedback_hint),
                                            color = Color.Gray
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(8.dp))

                    // Ghi chú về phản hồi
                    Text(
                        text = stringResource(R.string.feedback_note),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nút "Gửi đi"
                    Button(
                        onClick = { onSendFeedback(feedbackText) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (feedbackText.isNotEmpty()) Color.Red else Color.LightGray
                        ),
                        enabled = feedbackText.isNotEmpty()
                    ) {
                        Text(
                            text = stringResource(R.string.send_feedback),
                            color = Color.White
                        )
                    }
                }
            }
        )
    }
}
