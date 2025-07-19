package com.berlin.aflami.viewmodel.search_by_actor

import android.util.Log
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
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

        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher

        searchByActorNameUseCase = mockk()
        viewModel = SearchByActorViewModel(searchByActorNameUseCase)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }


}
