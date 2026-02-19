package com.example.dogbreedsproject.presentation.dogbreedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsproject.domain.DogBreedListUseCase
import com.example.dogbreedsproject.presentation.dogbreedlist.mappers.DogBreedListUIModel
import com.example.dogbreedsproject.presentation.dogbreedlist.mappers.toDogBreedListUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DogBreedListUIState{
    data object Loading: DogBreedListUIState()
    data class Success(
        val dogs: DogBreedListUIModel
    ): DogBreedListUIState()
    data class Error(
        val message: String
    ): DogBreedListUIState()
}

@HiltViewModel
class DogBreedListViewModel @Inject constructor(private val useCase: DogBreedListUseCase): ViewModel() {

    private val _state = MutableStateFlow<DogBreedListUIState>(DogBreedListUIState.Loading)
    val state = _state.asStateFlow()

    fun loadAllDogBreeds(){
        viewModelScope.launch {
            _state.value = DogBreedListUIState.Loading
            val result = useCase()
            _state.value = result.fold(
                onSuccess = { result ->
                    DogBreedListUIState.Success(result.toDogBreedListUIModel())
                },
                onFailure = { error ->
                    DogBreedListUIState.Error(error.message ?: "Data unavailable")
                }
            )
        }
    }
}

