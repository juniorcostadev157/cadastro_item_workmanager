package com.junior.projetomvvmcleanxml.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.junior.projetomvvmcleanxml.domain.usecase.item.CreateItemUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.GetUserIdSessionUseCase
import com.junior.projetomvvmcleanxml.presentation.principal.createitem.CreateItemUiState
import com.junior.projetomvvmcleanxml.presentation.principal.createitem.CreateItemViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CreateItemViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val createItemUseCase = mockk<CreateItemUseCase>(relaxed = true)
    private val getUserIdSessionUseCase = mockk<GetUserIdSessionUseCase>(relaxed = true)
    private lateinit var viewModel: CreateItemViewModel

    val name = "test"
    val id = "test123"

    @Before
    fun setup(){
        viewModel = CreateItemViewModel(
            createItemUseCase,
            getUserIdSessionUseCase
        )
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should verify userID and call method create item once`() = runTest{
        every { getUserIdSessionUseCase() } returns id
        coEvery { createItemUseCase(name, id) } returns Unit

        val observer = mockk<Observer<CreateItemUiState>>(relaxed = true)
        viewModel.uiState.observeForever (observer)

        val result = getUserIdSessionUseCase()
        viewModel.createItem(name)
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(CreateItemUiState.Loading)
            observer.onChanged(CreateItemUiState.Success)

        }

        assert(result != null)
        assertEquals(result, id)
        coVerify(exactly = 1) { createItemUseCase(name, id) }

    }

}