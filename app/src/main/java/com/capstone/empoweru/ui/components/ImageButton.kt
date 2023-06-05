package com.capstone.empoweru.ui.components

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.capstone.empoweru.ui.theme.EmpowerUTheme
import com.capstone.empoweru.R

@Composable
fun ImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedImageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    Box(
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
    ) {
        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (selectedImageUri != null) {
                Log.d("ImageButton", "Selected Image URI: $selectedImageUri")
                Image(
                    painter = rememberImagePainter(selectedImageUri),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_photos),
                        contentDescription = "Upload Image",
                        modifier = Modifier
                            .size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Klik disini untuk menambahkan Gambar",
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun ImageButtonPreview() {
    EmpowerUTheme() {
        ImageButton(
            onClick = { /*TODO*/ },
            onImageSelected = { },
            selectedImageUri = null
        )
    }
}