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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.capstone.empoweru.data.dummy.Umkm
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.R
import com.capstone.empoweru.data.dummy.generateDummyComments
import com.capstone.empoweru.data.repository.ListCommentRepository
import com.capstone.empoweru.data.response.Location
import com.capstone.empoweru.ui.components.CommentCard
import com.capstone.empoweru.ui.components.navigation.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    location: Location,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: DetailScreenViewModel = viewModel(
        factory = DetailScreenViewModelFactory(
            ListCommentRepository(),
            location.GMapsID
        )
    )

    val comments by viewModel.comments.collectAsState(emptyList())

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
                painter = rememberImagePainter(location.urlPhoto),
                contentScale = ContentScale.Crop,
                contentDescription = "Umkm Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = location.name,
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = location.address,
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
                    tint = Color(0xFFFFCC00),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = String.format("%.1f", location.rating),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
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
                    text = location.impression,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
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
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                items(comments  ) { comment ->
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
    val location = Location(
        address = "Jl. Margonda Raya No.358, Kemiri Muka, Kecamatan Beji, Kota Depok, Jawa Barat 16423, Indonesia",
        name = "MargoCity",
        type = "Restoran",
        rating = 7.3,
        GMapsID = "abcd1234",
        impression = "Netral",
        urlPhoto = "dummy_umkm"
    )

    EmpowerUTheme {
        DetailScreen(location, navController = rememberNavController())
    }
}
