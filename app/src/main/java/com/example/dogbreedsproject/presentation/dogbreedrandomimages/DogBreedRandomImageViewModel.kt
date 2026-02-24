package com.example.dogbreedsproject.presentation.dogbreedrandomimages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsproject.domain.GetDogBreedImageListUseCase
import com.example.dogbreedsproject.presentation.dogbreedrandomimages.mappers.DogBreedImageListUIModel
import com.example.dogbreedsproject.presentation.dogbreedrandomimages.mappers.toDogBreedImageListUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DogBreedRandomImageUIState{
    data object Loading: DogBreedRandomImageUIState()
    data class Success(
        val images: DogBreedImageListUIModel
    ): DogBreedRandomImageUIState()
    data class Error(
        val message: String
    ): DogBreedRandomImageUIState()
}

@HiltViewModel
class DogBreedRandomImageViewModel @Inject constructor(private val useCase: GetDogBreedImageListUseCase): ViewModel() {

    private val _state = MutableStateFlow<DogBreedRandomImageUIState>(DogBreedRandomImageUIState.Loading)
    val state = _state.asStateFlow()

    fun loadAllImages(breedName: String){
        viewModelScope.launch {
            _state.value = DogBreedRandomImageUIState.Loading
            val result = useCase(breedName)
            _state.value = result.fold(
                onSuccess = { result ->
                    DogBreedRandomImageUIState.Success(result.toDogBreedImageListUIModel())
                },
                onFailure = { error ->
                    DogBreedRandomImageUIState.Error(error.message ?: "Unable to load")
                }
            )
        }
    }
}

