package com.atlys.presentation.view

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.atlys.R
import com.atlys.ui.Screens

@Composable
fun CardViewMovie(
    modifier: Modifier = Modifier,
    id: Int,
    imageUrl: String,
    title: String,
    onItemClick: (Screens.MovieDetailScreen) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .clickable {
                onItemClick(Screens.MovieDetailScreen(id = id))
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
            )
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 8.dp)
                    .basicMarquee(),
            )
        }
    }
}


@Composable
@Preview
fun CardViewMoviePreview() {
    CardViewMovie(
        imageUrl = "https://picsum.photos/seed/picsum/200/300",
        title = "test title",
        id = 11,
        onItemClick = {}
    )
}