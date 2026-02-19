package com.example.dogbreedsproject

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dogbreedsproject.MockedDogBreeds.mockedDogBreedUIModel
import com.example.dogbreedsproject.MockedDogBreeds.mockedDogBreeds
import com.example.dogbreedsproject.domain.DogBreedListUseCase
import com.example.dogbreedsproject.presentation.dogbreedlist.DogBreedListUIState
import com.example.dogbreedsproject.presentation.dogbreedlist.DogBreedListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.android.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DogBreedListViewModelTest {

    @get:Rule
    val rule = CoroutineTestRule(StandardTestDispatcher())

    val useCase: DogBreedListUseCase = mock()

    lateinit var viewModel: DogBreedListViewModel

    @Test
    fun `test loading state`() = runTest {
        viewModel = DogBreedListViewModel(useCase)
        assertThat(viewModel.state.value).isEqualTo(DogBreedListUIState.Loading)
    }

    @Test
    fun `test success state`() = runTest {
        whenever(useCase()).thenReturn(Result.success(mockedDogBreeds))

        viewModel = DogBreedListViewModel(useCase)
        viewModel.loadAllDogBreeds()

        advanceUntilIdle()

        assertThat(viewModel.state.value).isEqualTo(DogBreedListUIState.Success(mockedDogBreedUIModel))
    }

    @Test
    fun `test error state`() = runTest {
        whenever(useCase()).thenReturn(Result.failure(Exception("Data error")))

        viewModel = DogBreedListViewModel(useCase)
        viewModel.loadAllDogBreeds()

        advanceUntilIdle()

        assertThat(viewModel.state.value).isEqualTo(DogBreedListUIState.Error("Data error"))

    }
}