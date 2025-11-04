package com.junior.projetomvvmcleanxml.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.usecase.item.ListLocalItemsUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.sync_data.GetSyncStatusUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.sync_data.StartSyncWorkUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.sync_data.ToggleSyncUseCase
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_firebase_fragment.ListItemUiState
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_room_fragment.ListRoomViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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

class ListRoomViewModelTest {
    /*
      Checklist mental antes de testar viewmodel

     1 Minha ViewModel tem dependências externas? mocka elas
     2 Usa LiveData? coloca InstantTaskExecutorRule
     3  Usa corrotinas?  MainDispatcherRule + runTest
     4  Preciso controlar ordem dos estados?  verifyOrder()
     5  É necessário simular erro?  lança exceção nos mocks
     6  Sai do init?  evita side effects no construtor (pra facilitar testes)
  */
    @get: Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcher = MainDispatcherRule()

    private val listLocal = mockk<ListLocalItemsUseCase>(relaxed = true)
    private val toggleSync = mockk<ToggleSyncUseCase>(relaxed = true)
    private val getSyncStatus = mockk<GetSyncStatusUseCase>(relaxed = true)
    private val startSync = mockk<StartSyncWorkUseCase>(relaxed = true)

    private lateinit var viewModel:  ListRoomViewModel

    @Before
    fun setup(){

        every { getSyncStatus() } returns true

        viewModel = ListRoomViewModel(
            listLocal,
            toggleSync,
            getSyncStatus,
            startSync
        )


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should emit Loading and Success when items list is not empty`() = runTest{
            val mockItems = listOf(
                Item("1", "Martelo", "user1"),

            )
        coEvery { listLocal() } returns flowOf(mockItems)

        val observer = mockk<Observer<ListItemUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        viewModel.loadItems()
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(ListItemUiState.Loading)
            observer.onChanged(ListItemUiState.Success(mockItems))
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should emit Error when listLocal throws exception`() = runTest{
        coEvery { listLocal() } returns flow { throw RuntimeException("falha") }
        val observer = mockk<Observer<ListItemUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        viewModel.loadItems()
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(ListItemUiState.Loading)
            observer.onChanged(match { it is ListItemUiState.Error && it.message.contains("falha") })
        }
    }

    @Test
    fun `should start sync when toggleSync true`()=runTest {
        viewModel.toggleSync(true)
        coVerify { toggleSync(true) }
        coVerify { startSync.startPeriodicSync() }
        assert(viewModel.syncEnabled.value == true)


    }

    @Test
    fun `should cancel sync when toggleSync false`() = runTest{
        viewModel.toggleSync(false)
        coVerify { toggleSync(false) }
        coVerify { startSync.cancelSync() }
        assert(viewModel.syncEnabled .value == false)
    }

}