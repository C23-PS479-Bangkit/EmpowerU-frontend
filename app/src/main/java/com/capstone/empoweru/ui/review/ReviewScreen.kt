package com.capstone.empoweru.ui.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.data.dummy.Rating
import com.capstone.empoweru.data.dummy.ratings
import com.capstone.empoweru.ui.components.CommentText
import com.capstone.empoweru.ui.components.ImageButton
import com.capstone.empoweru.ui.components.navigation.AddButton
import com.capstone.empoweru.ui.components.navigation.CancelButton
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun ReviewScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedRating by remember { mutableStateOf<Rating?>(null) }
    var isImageSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tambah Komentar",
            style = MaterialTheme.typography.h1,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ratings.forEach { rating ->
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = if (rating in ratings.subList(0, ratings.indexOf(selectedRating) + 1)) Color.Yellow else Color.LightGray,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            selectedRating = rating
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val ratingText = selectedRating?.description ?: ""

        Text(
            text = ratingText,
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        ImageButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CommentText()
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CancelButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            AddButton(
                onClick = { /* TODO: Handle add button click */ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    EmpowerUTheme {
        ReviewScreen(navController = rememberNavController())
    }
}