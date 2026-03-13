package com.example.dogbreedsproject.presentation.dogbreedsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedSearchScreen(viewModel: DogBreedSearchViewModel = hiltViewModel(),
                         onBackClicked: () -> Unit,
                         onSearchTriggered:(String) -> Unit = {},
                         ) {

    val searchResultUIState by viewModel.searchQueryUIState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    DogBreedSearchContent(
        onSearchQueryChanged = viewModel::onQueryChanged,
        onSearchTriggered = onSearchTriggered,
        onBackClicked = onBackClicked,
        searchQuery = searchQuery,
        searchResultUIState = searchResultUIState
    )




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedSearchContent(onSearchQueryChanged:(String) -> Unit,
                          onSearchTriggered:(String) -> Unit,
                          onBackClicked: () -> Unit,
                          searchQuery: String,
                          searchResultUIState: SearchResultUIState){

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
                        Text("Search")
                    },
                    navigationIcon = {
                        IconButton(onBackClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back arrow"
                            )
                        }
                    })
            }
    ) { paddingValues ->

        when(searchResultUIState){
            is SearchResultUIState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth().padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(80.dp)
                    )
                }
            }

            is SearchResultUIState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth().padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error displaying search via: $searchResultUIState")
                }
            }

            is SearchResultUIState.Success -> {

                val focusRequester = remember{ FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current

                val onSearchExplicitlyTriggered = {
                    keyboardController?.hide()
                    onSearchTriggered(searchQuery)
                }

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(paddingValues)){
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedTextColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        onSearchQueryChanged("")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "Clear search text",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        },
                        onValueChange = {
                            if ("\n" !in it) onSearchQueryChanged(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .focusRequester(focusRequester)
                            .onKeyEvent {
                                if (it.key == Key.Enter) {
                                    if (searchQuery.isBlank()) return@onKeyEvent false
                                    onSearchExplicitlyTriggered()
                                    true
                                } else {
                                    false
                                }
                            }
                            .testTag("searchTextField"),
                        shape = RoundedCornerShape(32.dp),
                        value = searchQuery,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (searchQuery.isBlank()) return@KeyboardActions
                                onSearchExplicitlyTriggered()
                            }
                        ),
                        maxLines = 1,
                        singleLine = true,
                    )
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }

                LazyColumn(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally){

                    items(searchResultUIState.dogList){ dogs ->
                        Text("$dogs")
                    }
                }
            }
            is SearchResultUIState.Empty -> {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(paddingValues)){
                    IconButton(
                        onClick = { onBackClicked()}
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Empty")
                }
            }

        }
    }

}