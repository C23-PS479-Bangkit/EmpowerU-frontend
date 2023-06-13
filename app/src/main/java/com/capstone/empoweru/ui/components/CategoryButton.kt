package com.capstone.empoweru.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.empoweru.data.dummy.CategoryItem
import com.capstone.empoweru.data.dummy.getCategory
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun CategoryButton(
    category: CategoryItem,
    selectedCategory: CategoryItem?,
    onCategorySelected: (CategoryItem) -> Unit
) {

    val whiteColor = MaterialTheme.colors.background
    var isSelected = selectedCategory == category

    Button(
        onClick = { onCategorySelected(category) },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) Color.Red else whiteColor,
            contentColor = if (isSelected) Color.White else Color.Red,
            disabledBackgroundColor = whiteColor,
            disabledContentColor = Color.Red
        ),
        border = if (!isSelected) BorderStroke(1.dp, Color.Red) else null,
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

@Preview(showBackground = true)
@Composable
fun CategoryButtonPreview() {
    val category = CategoryItem.FASHION

    EmpowerUTheme {
        CategoryButton(
            category = category,
            selectedCategory = category,
            onCategorySelected = {  }
        )
    }
}