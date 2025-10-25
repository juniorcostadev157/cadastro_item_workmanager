package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test


class ListLocalItemsUseCaseTest {

    private val repository = mockk<ItemRepository>(relaxed = true)

    @Test
    fun `should all items from all items`() = runTest {
        val items = listOf(
            Item("id1", "Item 1", "user1"),
            Item("id2", "Item 2", "user2")
        )

        coEvery {  repository.getAllItem()} returns flowOf(items)

        val useCase = ListLocalItemsUseCase(repository)

        useCase().collect {
            assert(it.size == 2)
            assert(it[0].name == "Item 1")
            assert(it[1].id == "id2")
        }
    }
}