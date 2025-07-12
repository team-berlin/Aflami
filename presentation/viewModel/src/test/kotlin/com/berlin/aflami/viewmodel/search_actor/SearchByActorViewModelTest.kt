package com.berlin.aflami.viewmodel.search_by_actor

import android.util.Log
import com.berlin.aflami.viewmodel.search_actor.SearchByActorViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import usecase.SearchByActorNameUseCase

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SearchByActorViewModelTest {

    private lateinit var viewModel: SearchByActorViewModel
    private lateinit var searchByActorNameUseCase: SearchByActorNameUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchByActorNameUseCase = mockk()
        viewModel = SearchByActorViewModel(searchByActorNameUseCase)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when onActorNameChanged should update actorName in uiState`() = runTest {
        // Given
        val actorName = "Tom Hanks"

        // When
        viewModel.onActorNameChanged(actorName)
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.first()
        assertThat(uiState.actorName).isEqualTo(actorName)
    }

    @Test
    fun `when onSearchClick should set isLoading to true initially`() = runTest {
        // Given
        val actorName = "Tom"
        val language = "Tom"
        viewModel.onActorNameChanged(actorName)
        coEvery { searchByActorNameUseCase(actorName,language) } returns emptyList()

         //When
        viewModel.onSearchClicked()
        val initialUiState = viewModel.uiState.first()

         //Then
        assertThat(initialUiState.isLoading).isTrue()
        advanceUntilIdle()
    }

//    @Test
//    fun `when onSearchClick should update uiState with error on failure`() = runTest {
//        // Given
//        val actorName = "Tom "
//        val language = "Tom"
//        val errorMessage = "error"
//        coEvery { searchByActorNameUseCase(any(),any()) } throws Exception(errorMessage)
//        viewModel.onActorNameChanged(actorName)
//
//        // When
//        viewModel.onSearchClicked()
//        advanceUntilIdle()
//
//        // Then
//        val uiState = viewModel.uiState.first()
//        assertThat(uiState.isLoading).isFalse()
//        assertThat(uiState.error).isEqualTo(errorMessage)
//        assertThat(uiState.movies).isEmpty()
//        //coVerify(exactly = 1) { searchByActorNameUseCase(actorName,language) }
//    }
}