package com.capstone.empoweru.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun CommentText(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
    ) {
        TextField(
            value = "",
            onValueChange = { /* TODO: Handle text change */ },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            textStyle = MaterialTheme.typography.body1.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent, // Set the indicator color to transparent
                unfocusedIndicatorColor = Color.Transparent // Set the indicator color to transparent
            ),
            placeholder = {
                Text(
                    text = "Tambahkan Komentar disini",
                    style = MaterialTheme.typography.body1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommentTextPreview() {
    EmpowerUTheme {
        CommentText()
    }
}