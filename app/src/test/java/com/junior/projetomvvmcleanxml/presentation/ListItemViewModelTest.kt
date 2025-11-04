package com.junior.projetomvvmcleanxml.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.LogoutUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.item.ListItemUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.ClearUseSessionUseCase
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_firebase_fragment.ListItemUiState
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_firebase_fragment.ListItemViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListItemViewModelTest {

    /*
         Checklist mental antes de testar viewmodel

        1 Minha ViewModel tem dependências externas? → mocka elas
        2 Usa LiveData? → coloca InstantTaskExecutorRule
        3  Usa corrotinas? → MainDispatcherRule + runTest
        4  Preciso controlar ordem dos estados? → verifyOrder()
        5  É necessário simular erro? → lança exceção nos mocks
        6  Sai do init? → evita side effects no construtor (pra facilitar testes)
     */

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAllItem = mockk<ListItemUseCase>(relaxed = true)
    private val logoutUseCase = mockk<LogoutUseCase>(relaxed = true)
    private val clearUseSessionUseCase = mockk<ClearUseSessionUseCase>(relaxed = true)

    private lateinit var viewModel: ListItemViewModel

    @Before
    fun setup() {
        viewModel = ListItemViewModel(getAllItem, logoutUseCase, clearUseSessionUseCase)
    }

    //  Sucesso
    @Test
    fun `should emit Loading and Success when items list is not empty`() = runTest {
        val items = listOf(
            Item("1", "Martelo", "user1"),
            Item("2", "Chave de Fenda", "user2")
        )

        coEvery { getAllItem() } returns flowOf(items)

        val observer = mockk<Observer<ListItemUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        // Ação
        viewModel.loadItems()
        advanceUntilIdle()

        // Verificação
        verifyOrder {
            observer.onChanged(ListItemUiState.Loading)
            observer.onChanged(ListItemUiState.Success(items))
        }
    }

    //  Lista vazia
    @Test
    fun `should emit Loading and Empty when items list is empty`() = runTest {
        coEvery { getAllItem() } returns flowOf(emptyList())

        val observer = mockk<Observer<ListItemUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        viewModel.loadItems()
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(ListItemUiState.Loading)
            observer.onChanged(ListItemUiState.Empty)
        }
    }

    //  Exceção
    @Test
    fun `should emit Loading and Error when exception occurs`() = runTest {
        coEvery { getAllItem() } returns flow {
            throw RuntimeException("Falha na rede")
        }

        val observer = mockk<Observer<ListItemUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        viewModel.loadItems()
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(ListItemUiState.Loading)
            observer.onChanged(match { it is ListItemUiState.Error && it.message.contains("Falha") })
        }
    }

    //  Logout
    @Test
    fun `should call logout and clearSession when logout is invoked`() = runTest {
        viewModel.logout()
        coVerify(exactly = 1) { logoutUseCase() }
        coVerify(exactly = 1) { clearUseSessionUseCase() }
    }
}
