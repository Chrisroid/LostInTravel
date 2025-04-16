package com.chrisroid.lostintravel.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.chrisroid.lostintravel.domain.model.Destination
import com.chrisroid.lostintravel.viewmodel.HomeViewModel
import com.chrisroid.lostintravel.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val destinations by viewModel.destinations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    HomeScreen2()
}

@Composable
fun HomeScreen2(userName: String = "Samira") {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Welcome $userName,",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = GoogleSansDisplay,
                            fontWeight = FontWeight.W400,
                            fontSize = 18.sp,
                            lineHeight = 14.sp,
                            letterSpacing = (-0.17).sp,
                            textAlign = TextAlign.Center
                        ),
                    )
                    Text(
                        text = "Where do you want to go?",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.google_sans_regular)), // Make sure font is in res/font
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 10.sp,
                            letterSpacing = (-0.17).sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                    )
                }
            }
            Row {
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(id = R.drawable.message), // replace with your actual drawable name
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Notifications, contentDescription = null)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F7FA), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search for places", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Frequently Visited
        Text("Frequently Visited", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(frequentlyVisited) {
                PlaceCardHorizontal(place = it)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Recommended Places
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recommended Places", style = MaterialTheme.typography.titleMedium)
            Text("Explore All", color = Color.Blue, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(recommendedPlaces) {
                PlaceCardVertical(place = it)
            }
        }
    }
}


@Composable
fun DestinationCard(destination: Destination) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = destination.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = destination.country,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}


data class Place(
    val title: String,
    val location: String,
    val price: String,
    val image: Int // drawable resource
)

val frequentlyVisited = listOf(
    Place("Mykonos", "Chora, Greece", "$1800", R.drawable.kigali),
    Place("Waterfort", "Venesia, Italy", "$1800", R.drawable.maldives),
    Place("Delli", "Chora, Greece", "$1800", R.drawable.sumbing)
)

val recommendedPlaces = listOf(
    Place("Kigali Resort", "Kigali, Rwanda", "$1350", R.drawable.kigali),
    Place("Maldives", "Rep of Maldives", "$1350", R.drawable.maldives),
    Place("Sumbing Mount", "Chora, Greece", "$1350", R.drawable.sumbing)
)

@Composable
fun PlaceCardHorizontal(place: Place) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(place.image),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.padding(8.dp)) {
            Text(place.title, style = MaterialTheme.typography.bodyMedium)
            Text(place.location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text("${place.price}/person", color = Color.Blue, fontSize = 12.sp)
        }
    }
}

@Composable
fun PlaceCardVertical(place: Place) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(place.image),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(place.title, style = MaterialTheme.typography.bodyMedium)
            Text(place.location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Text("${place.price}/person", color = Color.Blue, fontSize = 12.sp)
    }
}

val GoogleSansDisplay = FontFamily(
    Font(R.font.google_sans_regular, FontWeight.W400)
)

