package com.capstone.empoweru.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.empoweru.data.dummy.CategoryItem
import com.capstone.empoweru.data.dummy.getCategory
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun CategoryButton(
    category: CategoryItem
) {
    Button(
        onClick = {  },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
    ) {
        Text(
            text = category.value,
            style = MaterialTheme.typography.button,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun CategoryButtonPreview() {
    val category = CategoryItem.FASHION

    EmpowerUTheme {
        CategoryButton(category = category)
    }
}