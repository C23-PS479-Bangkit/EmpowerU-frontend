package com.capstone.empoweru.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.data.dummy.Umkm
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.R
import com.capstone.empoweru.data.dummy.generateDummyComments
import com.capstone.empoweru.ui.components.CommentCard
import com.capstone.empoweru.ui.components.navigation.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    umkm: Umkm,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val address = "Jl. Siliwangi No.1, Depok, Kec. Pancoran Mas, Kota Depok, Jawa Barat 16431"
    val comment = generateDummyComments()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Review.route) },
                backgroundColor = Color.Red
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = rememberScaffoldState(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = umkm.image),
                contentScale = ContentScale.Crop,
                contentDescription = "Umkm Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = umkm.title,
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = address,
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    text = "Rating:",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating:",
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = umkm.rating.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.width(48.dp))

                Text(
                    text = "Impresi:",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Baik",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Green
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Komentar",
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier.padding(bottom = 24.dp, start = 18.dp, end = 18.dp)
            ) {
                items(comment) { comment ->
                    CommentCard(comment = comment)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val umkm = Umkm(
        image = R.drawable.dummy_umkm,
        title = "Restoran Di Inazuma",
        category = "Kuliner",
        rating = 4.5
    )
    EmpowerUTheme {
        DetailScreen(umkm = umkm, navController = rememberNavController())
    }
}
