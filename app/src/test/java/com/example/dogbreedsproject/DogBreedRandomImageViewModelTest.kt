package com.example.dogbreedsproject

import com.example.dogbreedsproject.MockedDogBreeds.mockedDogImageList
import com.example.dogbreedsproject.MockedDogBreeds.mockedDogImageListUIModel
import com.example.dogbreedsproject.domain.DogBreedImageListUseCase
import com.example.dogbreedsproject.presentation.dogbreedrandom.DogBreedRandomImageUIState
import com.example.dogbreedsproject.presentation.dogbreedrandom.DogBreedRandomImageViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DogBreedRandomImageViewModelTest {
    @get:Rule
    val rule = CoroutineTestRule(StandardTestDispatcher())

    val useCase : DogBreedImageListUseCase = mock()

    lateinit var viewModel : DogBreedRandomImageViewModel

    @Test
    fun `test image loading state`() = runTest {
        viewModel = DogBreedRandomImageViewModel(useCase)

        assertThat(viewModel.state.value).isEqualTo(DogBreedRandomImageUIState.Loading)
    }

    @Test
    fun `test image success state`() = runTest {
        val breedName = "dalmatian"

        whenever(useCase(breedName)).thenReturn(Result.success(mockedDogImageList))

        viewModel = DogBreedRandomImageViewModel(useCase)
        viewModel.loadAllImages(breedName)

        advanceUntilIdle()

        assertThat(viewModel.state.value).isEqualTo(DogBreedRandomImageUIState.Success(mockedDogImageListUIModel))

    }

    @Test
    fun `test image error state`() = runTest {
        val breedName = "dalmatian"

        whenever(useCase(breedName)).thenReturn(Result.failure(Exception("Image unavailable")))
        viewModel = DogBreedRandomImageViewModel(useCase)

        viewModel.loadAllImages(breedName)

        advanceUntilIdle()

        assertThat(viewModel.state.value).isEqualTo(DogBreedRandomImageUIState.Error("Image unavailable"))
    }
}