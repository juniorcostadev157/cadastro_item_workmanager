package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ListItemUseCaseTest {

    private val repository = mockk<ItemRepository>(relaxed = true)


    @Test
    fun `should list all items and call the repository once`() = runTest {
        val items = listOf(
            Item("id1", "Item 1", "user1"),
            Item("id2", "Item 2", "user2")
        )

        coEvery { repository.getAllItem() } returns flowOf(items)


        val useCase = ListItemUseCase(repository)
        val result = mutableListOf<List<Item>>()

        useCase().collect { result.add(it) }

        assert(result.first().size == 2)
        assert(result.first()[0].name == "Item 1")
        assert(result.first()[1].name == "Item 2")

    }



}
