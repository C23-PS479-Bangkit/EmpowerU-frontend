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
import com.capstone.empoweru.data.dummy.Comment
import com.capstone.empoweru.R
import com.capstone.empoweru.data.response.CommentList
import com.capstone.empoweru.ui.theme.EmpowerUTheme

@Composable
fun CommentCard(
    comment: CommentList,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = 2.dp
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Username
                    Text(
                        text = comment.username,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )

                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically
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

                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                // Image Placeholder
                /*if (comment.image != null) {
                    Text(
                        text = comment.text,
                        style = MaterialTheme.typography.body2,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Image(
                        painter = painterResource(id = comment.image),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Comment Image",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                } else {*/

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
        comment = "Makanannya sangat enak!"
    )

    EmpowerUTheme() {
        CommentCard(comment = comment)
    }
}