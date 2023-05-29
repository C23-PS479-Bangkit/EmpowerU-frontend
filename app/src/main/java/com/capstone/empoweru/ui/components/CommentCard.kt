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

@Composable
fun CommentCard(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(12.dp)
        ) {
            // Profile image
            Image(
                painter = painterResource(id = comment.profileImage),
                contentScale = ContentScale.Crop,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

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
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = comment.rating.toString(),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }

                // Comment text or image
                if (comment.image != null) {
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

                } else {
                    Text(
                        text = comment.text,
                        style = MaterialTheme.typography.body2,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentCardPreview() {
    val comment = Comment(
        username = "User1",
        profileImage = R.drawable.profile_dummy,
        text = "This place is amazing!",
        image = R.drawable.dummy_umkm,
        rating = 4.5f
    )
    CommentCard(comment = comment)
}