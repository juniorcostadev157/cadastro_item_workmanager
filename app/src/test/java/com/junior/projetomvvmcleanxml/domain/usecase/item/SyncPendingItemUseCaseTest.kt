package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SyncPendingItemUseCaseTest {

    private val repository = mockk<ItemRepository>(relaxed = true)

    @Test
    fun`should call repository method once`() = runTest {

        coEvery { repository.syncPendingItems() } returns Unit

        SyncPendingItemUseCase(repository).invoke()

        coVerify(exactly = 1) { repository.syncPendingItems() }
    }
}