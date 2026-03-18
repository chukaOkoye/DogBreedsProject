package com.example.dogbreedsproject.presentation.dogbreedsearch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsproject.domain.usecases.GetDogBreedSearchResultUseCase
import com.example.dogbreedsproject.domain.model.DogBreedSearchResult
import com.example.dogbreedsproject.presentation.dogbreedsearch.mappers.DogBreedSearchResultUIModel
import com.example.dogbreedsproject.presentation.dogbreedsearch.mappers.toSearchResultUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class SearchResultUIState {
    data object Loading : SearchResultUIState()
    data class Success(
        val dogList: DogBreedSearchResultUIModel
    ) : SearchResultUIState()

    data class Error(
        val message: String
    ) : SearchResultUIState()

    data object Empty : SearchResultUIState()
}

private const val SEARCH_QUERY = "searchQuery"

@HiltViewModel
class DogBreedSearchViewModel @Inject constructor(
    private val getDogBreedSearchUseCase: GetDogBreedSearchResultUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchQueryUIState: StateFlow<SearchResultUIState> = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest{query ->
//            if(query.isEmpty()){
//                flowOf(SearchResultUIState.Empty)
//            } else {
                getDogBreedSearchUseCase(query)
                    .map<DogBreedSearchResult, SearchResultUIState>{
                            result ->
                        SearchResultUIState.Success(result.toSearchResultUIState())
                    }
                    .onStart { emit(SearchResultUIState.Loading) }
                    .catch { SearchResultUIState.Error("Error loading") }

//            }

        }
        .onEach { println("Current State: $it") }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchResultUIState.Loading
        )

    fun onQueryChanged(input: String){
        savedStateHandle[SEARCH_QUERY] = input
    }



}













//   val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
//    val searchUIState: StateFlow<SearchResultUIState> = searchQuery
//        .debounce(300)
//        .distinctUntilChanged()
//        .flatMapLatest { query ->
//            if(query.isEmpty()){
//                flowOf(SearchResultUIState.Empty)
//            } else {
//                getDogBreedSearchUseCase(query)
//                    .map<DogBreedSearchResult, SearchResultUIState>{ result ->
//                    SearchResultUIState.Success(result)
//                }
//                    .onStart { emit(SearchResultUIState.Loading) }
//                    .catch { SearchResultUIState.Error("Error") }
//            }
//        }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            SearchResultUIState.Loading
//        )







// val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
//    // By adding this type here, Kotlin knows the whole chain should be the parent type
//    val searchUIState: StateFlow<SearchResultUIState> = searchQuery
//        .debounce(300)
//        .distinctUntilChanged()
//        .flatMapLatest { query ->
//            if (query.isEmpty()) {
//                flowOf(SearchResultUIState.Empty)
//            } else {
//                getDogBreedSearchUseCase(query)
//                    .map<DogBreedSearchResult, SearchResultUIState> { result ->
//                        // Kotlin now knows this must be the parent type because of the variable above
//                        SearchResultUIState.Success(result )
//                    }
//                    .onStart { emit(SearchResultUIState.Loading) }
//                    .catch { emit(SearchResultUIState.Error("Error")) }
//            }
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = SearchResultUIState.Loading
//        )



















//val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
//
//val searchUIState: StateFlow<SearchResultUIState> = searchQuery
//    .debounce(300)
//    .distinctUntilChanged()
//    .flatMapLatest { query ->
//        getDogBreedSearchUseCase(query).map { result ->
//            SearchResultUIState.Success(result)
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = SearchResultUIState.Empty
//
//
//    )
//
//
//fun onSearchQueryChanged(query: String) {
//    savedStateHandle[SEARCH_QUERY] = query
//}



//
//@OptIn(ExperimentalCoroutinesApi::class)
//val searchResultUIState: StateFlow<SearchResultUIState> =
//    searchQuery.debounce(300)
//        .flatMapLatest { query ->
//            getDogBreedSearchUseCase(query).map {
//
//            }
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = SearchResultUIState.Loading
//        )