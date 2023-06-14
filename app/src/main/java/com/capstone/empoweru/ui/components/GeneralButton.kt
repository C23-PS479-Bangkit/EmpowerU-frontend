package com.capstone.empoweru.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        modifier = modifier
    ) {
        Text(
            text = "Tambahkan",
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = Color.Red
        ),
        border = BorderStroke(1.dp, Color.Red),
        elevation = ButtonDefaults.elevation(0.dp),
        modifier = modifier
    ) {
        Text(
            text = "Kembali",
            style = MaterialTheme.typography.button
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonPreview() {
    EmpowerUTheme {
        AddButton(onClick = { })
    }
}

@Preview(showBackground = true)
@Composable
fun CancelButtonPreview() {
    EmpowerUTheme {
        CancelButton(onClick = { })
    }
}