package com.atlys.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.atlys.R
import com.atlys.utils.Constants

@Composable
fun DetailScreen(
    id: Int,
    viewModel: MovieDetailsScreenViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(id)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            ToolBar(onBackPress = onBackPress)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            CoverImage(imageUrl = Constants.IMAGE_BASE_URL + uiState.movieDetails?.backdropPath)
            Text(
                text = uiState.movieDetails?.title ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = uiState.movieDetails?.overview ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun ToolBar(
    onBackPress: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "",
            modifier = Modifier.clickable {
                onBackPress()
            }
        )
    }
}

@Composable
fun CoverImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .height(350.dp),
    ) {
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
                .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
            /*modifier = modifier
                .sharedElement(
                    state = rememberSharedContentState(
                        key = sharedTransitionKey
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .fillMaxSize()
                .clip(shape = MaterialTheme.shapes.medium)*/
        )
    }
}

@Composable
@Preview
fun CoverImagePreview() {
    CoverImage(imageUrl = "")
}

@Composable
@Preview
fun ToolbarPreview() {
    ToolBar(onBackPress = {})
}