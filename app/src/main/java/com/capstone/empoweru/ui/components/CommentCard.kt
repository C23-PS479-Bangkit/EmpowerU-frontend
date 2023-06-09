package com.capstone.empoweru.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.capstone.empoweru.R
import com.capstone.empoweru.data.response.CommentList
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CommentCard(
    comment: CommentList
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(14.dp)
        ) {
            // Profile image
            Image(
                painter = painterResource(R.drawable.example_profile),
                contentScale = ContentScale.Crop,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .padding(4.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        // Username
                        Text(
                            text = comment.username,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp)
                        )

                        Text(
                            text = comment.comment,
                            style = MaterialTheme.typography.body2,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))

                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = comment.starRating.toString(),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
                }

                // Image Placeholder
                if (comment.urlPhoto.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Image(
                        painter = rememberImagePainter(comment.urlPhoto),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Comment Image",
                        modifier = Modifier
                            .size(240.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CommentCardPreview() {
    val comment = CommentList(
        username = "User1",
        starRating = 4.5,
        comment = "Makanannya sangat enak!",
        urlPhoto = "Some URL photo"
    )

    EmpowerUTheme() {
        CommentCard(comment = comment)
    }
}