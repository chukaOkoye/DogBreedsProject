package com.example.dogbreedsproject.presentation.dogbreedrandom

import android.graphics.Color.red
import android.graphics.drawable.Icon
import android.text.style.IconMarginSpan
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets.add
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dogbreedsproject.R

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DogBreedRandomImageScreen(
    viewModel: DogBreedRandomImageViewModel = hiltViewModel(),
    breedName: String,
    onBackClick: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(breedName) {
        viewModel.loadAllImages(breedName)
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar =
            {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(breedName)
                    },
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back arrow"
                            )
                        }
                    })
            }) { paddingValues ->

        when (state) {
            is DogBreedRandomImageUIState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(80.dp)
                    )
                }
            }

            is DogBreedRandomImageUIState.Error -> {
                val error = state as DogBreedRandomImageUIState.Error
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Error loading images: ${error.message}",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button({ viewModel.loadAllImages(breedName) }) {
                        Text("Try again")
                    }

                }
            }

            is DogBreedRandomImageUIState.Success -> {
                val success = state as DogBreedRandomImageUIState.Success

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(success.images.message) { dogs ->
                        GlideImage(
                            model = dogs,
                            contentDescription = "Dog images",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .size(200.dp),
                            failure = placeholder(R.drawable.ic_launcher_foreground),
                            loading = placeholder(R.drawable.ic_launcher_foreground),
                        )
                    }
                }
            }
        }

    }


}