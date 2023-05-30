package com.capstone.empoweru.ui.components

import android.icu.number.Scale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.empoweru.data.dummy.Umkm
import com.capstone.empoweru.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UmkmCard(
    umkm: Umkm,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = umkm.image),
                contentScale = ContentScale.Crop,
                contentDescription = "Gambar UMKM",
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(8.dp))

            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column() {
                Text(
                    text = umkm.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = umkm.category,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = umkm.rating.toString(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UmkmCardPreview() {
    val umkm = Umkm(
        image = R.drawable.dummy_umkm,
        title = "Restoran Di Inazuma",
        category = "Kuliner",
        rating = 4.5
    )
    
    UmkmCard(
        umkm = umkm,
        onClick = { }
    )
}