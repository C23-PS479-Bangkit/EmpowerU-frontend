package com.capstone.empoweru.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.empoweru.R
import com.capstone.empoweru.ui.theme.PoppinsTypography

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val profilePicture = painterResource(R.drawable.profile_dummy)
    val name = "Pom-Pom"
    val review = 28

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MaterialTheme(typography = PoppinsTypography) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }

                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.h1,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )

                        IconButton(
                            onClick = { viewModel.logout(context) },
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Logout"
                            )
                        }

                        /*Spacer(modifier = Modifier.width(48.dp))*/
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Image(
                        painter = profilePicture,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Username",
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = name,
                        style = MaterialTheme.typography.h1,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Reviews",
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = review.toString(),
                        style = MaterialTheme.typography.h1,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfileScreen(ProfileViewModel())
}