package com.capstone.empoweru.ui.review

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.empoweru.data.dummy.Rating
import com.capstone.empoweru.data.dummy.ratings
import com.capstone.empoweru.data.response.Location
import com.capstone.empoweru.ui.components.AddButton
import com.capstone.empoweru.ui.components.CancelButton
import com.capstone.empoweru.ui.components.CommentText
import com.capstone.empoweru.ui.components.ImageButton
import com.capstone.empoweru.ui.components.navigation.Screen
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import java.io.ByteArrayOutputStream

@Composable
fun ReviewScreen(
    navController: NavHostController,
    location: Location,
    context: Context
) {
    val viewModel: ReviewScreenViewModel = viewModel(
        factory = ReviewScreenViewModelFactory(LocalContext.current))

    var selectedRating by remember { mutableStateOf<Rating?>(null) }
    var commentQuery by remember { mutableStateOf("") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val openGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { result ->
            if (result != null) {
                selectedImageUri = result
            }
        }
    )

    fun openGallery() {
        openGalleryLauncher.launch("image/*")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = location.name,
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
                    tint = if (rating in ratings.subList(0, ratings.indexOf(selectedRating) + 1)) Color(0xFFFFCC00) else Color.LightGray,
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
            onClick = { openGallery() },
            selectedImageUri = selectedImageUri,
            onImageSelected = { uri -> selectedImageUri = uri },
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CommentText(
                query = commentQuery,
                onQueryChanged = { commentQuery = it }
            )
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
                onClick = {
                    val imageBase64 = selectedImageUri?.compressAndEncodeToBase64(context)
                    Log.d("ReviewScreen", "Base64 Image: $imageBase64")
                    viewModel.addComment(location, selectedRating, commentQuery, imageBase64) { success ->
                        if (success) {
                            val name = location.name
                            navController.popBackStack(Screen.Detail.route, inclusive = false)
                            navController.navigate("${Screen.Detail.route}/$name") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
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
        val context = LocalContext.current
        ReviewScreen(
            navController = rememberNavController(),
            location = location,
            context = context
        )
    }
}
fun Uri.compressAndEncodeToBase64(context: Context): String? {
    try {
        val inputStream = context.contentResolver.openInputStream(this)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val compressedBitmap = compressBitmap(bitmap)
        val byteArrayOutputStream = ByteArrayOutputStream()
        compressedBitmap?.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()

        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

fun compressBitmap(bitmap: Bitmap?): Bitmap? {
    val maxWidth = 1024
    val maxHeight = 1024

    val width = bitmap?.width ?: 0
    val height = bitmap?.height ?: 0

    if (width <= maxWidth && height <= maxHeight) {
        return bitmap
    }

    val aspectRatio = width.toFloat() / height.toFloat()
    val targetWidth: Int
    val targetHeight: Int

    if (aspectRatio > 1) {
        targetWidth = maxWidth
        targetHeight = (maxWidth / aspectRatio).toInt()
    } else {
        targetHeight = maxHeight
        targetWidth = (maxHeight * aspectRatio).toInt()
    }

    return bitmap?.let { Bitmap.createScaledBitmap(it, targetWidth, targetHeight, true) }
}

