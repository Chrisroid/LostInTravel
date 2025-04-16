package com.chrisroid.lostintravel.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chrisroid.lostintravel.R

@Composable
fun DetailScreen(placeTitle: String, navController: NavController) {
    val place = (recommendedPlaces + frequentlyVisited).find { it.title == placeTitle }

    if (place == null) {
        Text("Destination not found")
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Destination Detail",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = { }) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0x0D007AFF), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }

        // Image with overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star), // your star icon
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Frequently Visited",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = place.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "100k+ people have explored",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp
                )
            }

            // Group avatars (dummy)
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                repeat(5) {
                    Image(
                        painter = painterResource(id = R.drawable.sample_user), // dummy avatar
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .offset(x = (-it * 12).dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title and Price Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = null, tint = Color.Red, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = place.location, color = Color.Gray)
            }

            Row {
                Text(
                    text = "$${place.price}",
                    color = Color(0xFF007AFF),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "/person",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // View on Map
        Text(
            text = "View on map",
            color = Color(0xFF007AFF),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Description Destination", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Mount Sumbing or Gunung Sumbing is an active stratovolcano in Central Java. Indonesia that is symmetrical like its neighbour, Mount Sindoro.",
                color = Color.Gray
            )
            Text("Read More...", color = Color(0xFF007AFF), fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gallery
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Gallery Photo", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(4) {
                    Image(
                        painter = painterResource(id = R.drawable.gallery1), // replace with gallery images
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Today’s weather", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(4) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFEAF1FF), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_weather), // replace with weather icon
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Text("24°C", fontWeight = FontWeight.Bold)
                        Text("1:00PM", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Book Button
        Button(
            onClick = { /* Handle booking */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Book Now", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
