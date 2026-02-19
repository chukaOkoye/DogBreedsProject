package com.example.dogbreedsproject.presentation.dogbreedlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedListScreen(
    viewModel: DogBreedListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.loadAllDogBreeds()
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
                        Text("Dog Breeds")
                    })
            }) { paddingValues ->

        when (state) {
            is DogBreedListUIState.Loading -> {
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

            is DogBreedListUIState.Error -> {
                val error = state as DogBreedListUIState.Error
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Error loading dog breed list: ${error.message}",
                        textAlign = Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(viewModel::loadAllDogBreeds) {
                        Text("Try again")
                    }

                }

            }

            is DogBreedListUIState.Success -> {
                val success = state as DogBreedListUIState.Success

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(success.dogs.message.keys.toList()) { breed ->

                        Column(modifier = Modifier.clickable {
                            onNavigate(breed)
                        }) {
                            Text(
                                text = breed,
                                fontSize = 26.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp)
                            )
                        }


                    }
                }
            }
        }
    }


}