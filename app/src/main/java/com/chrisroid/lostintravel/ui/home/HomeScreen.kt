package com.chrisroid.lostintravel.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import kotlin.random.Random


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

@OptIn(ExperimentalMaterial3Api::class)
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
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0x0D007AFF), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.message),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp)) // Optional spacing between buttons

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0x0D007AFF), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null)
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        var searchText by remember { mutableStateOf("") }

        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search for places", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F7FA), shape = RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                selectionColors = LocalTextSelectionColors.current,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFF007AFF).copy(alpha = 0.05f),
                unfocusedContainerColor = Color(0xFF007AFF).copy(alpha = 0.05f),
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Frequently Visited
        Text(
            text = "Frequently Visited",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.google_sans_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                lineHeight = 14.sp,
                letterSpacing = (-0.17).sp,
                textAlign = TextAlign.Center
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))

        val favorites = remember {
            List(frequentlyVisited.size) { Random.nextBoolean() }
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(frequentlyVisited) { index, place ->
                PlaceCardHorizontal(
                    place = place,
                    isFavorite = favorites[index]
                )
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

        val favorites1 = remember {
            List(frequentlyVisited.size) { Random.nextBoolean() }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(recommendedPlaces) {index, place ->
                PlaceCardVertical(
                    place = place,
                    isFavorite = favorites1[index]
                )
            }
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
    Place("Mykonos", "Chora, Greece", "1800", R.drawable.kigali),
    Place("Waterfort", "Venesia, Italy", "1800", R.drawable.maldives),
    Place("Delli", "Chora, Greece", "1800", R.drawable.sumbing)
)

val recommendedPlaces = listOf(
    Place("Kigali Resort", "Kigali, Rwanda", "1350", R.drawable.kigali),
    Place("Maldives", "Rep of Maldives", "1350", R.drawable.maldives),
    Place("Sumbing Mount", "Chora, Greece", "1350", R.drawable.sumbing)
)

@Composable
fun PlaceCardHorizontal(place: Place, isFavorite: Boolean) {
    Card(
        modifier = Modifier.width(180.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .width(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = place.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = place.title,
                        fontFamily = FontFamily(Font(R.font.google_sans_regular)),
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "$${place.price}",
                        color = Color(0xFF007AFF),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                }


                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = place.location,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }

                    Text(
                        text = " /person",
                        color = Color.LightGray,
                        fontSize = 16.sp,
                    )
                }






            }
        }
    }
}


@Composable
fun PlaceCardVertical(place: Place, isFavorite: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = place.title,
                    fontFamily = FontFamily(Font(R.font.google_sans_regular)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.location,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$${place.price}",
                        color = Color(0xFF007AFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "/person",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}

val GoogleSansDisplay = FontFamily(
    Font(R.font.google_sans_regular, FontWeight.W400)
)

