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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.R
import com.capstone.empoweru.ui.theme.PoppinsTypography
import com.capstone.empoweru.data.local.UserPreferences

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel,
) {
    val profilePicture = painterResource(R.drawable.example_profile)

    val context = LocalContext.current
    val username = viewModel.userPreferences.username ?: ""
    val email = viewModel.userPreferences.email ?: ""

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
                            onClick = { navController.popBackStack() },
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

                    Spacer(modifier = Modifier.height(48.dp))

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
                        text = username,
                        style = MaterialTheme.typography.h1,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Email terdaftar",
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = email,
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
    val userPreferences = UserPreferences.getInstance(LocalContext.current)
    val viewModel = ProfileViewModel(userPreferences)

    val navController = rememberNavController()

    ProfileScreen(navController, viewModel)
}
