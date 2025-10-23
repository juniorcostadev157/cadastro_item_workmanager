package com.junior.projetomvvmcleanxml.data

import com.junior.projetomvvmcleanxml.data.datasource.local.room.RoomItemDataSource
import com.junior.projetomvvmcleanxml.data.datasource.remote.FirebaseItemDataSource
import com.junior.projetomvvmcleanxml.data.model.item.ItemLocalEntity
import com.junior.projetomvvmcleanxml.data.repository.ItemRepositoryImpl
import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.model.item.toLocalEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ItemRepositoryImplTest {

    private val remote = mockk<FirebaseItemDataSource>(relaxed = true)
    private val local = mockk<RoomItemDataSource>(relaxed = true)
    private val repository = ItemRepositoryImpl(remote, local)

    val item = Item(
        id = "1",
        name = "Item 1",
        createdBy = "junior",
        isSynchronized = false
    )

    val itemEntityLocalList = listOf(
        ItemLocalEntity(id = "1", name = "Chave 10", createdBy = "junior", isSynchronized = false),
        ItemLocalEntity(id = "2", name = "Chave 12", createdBy = "junior", isSynchronized = false)
    )


    @Test
    fun `must create items`() = runTest {
        repository.createItem(item)

        coVerify { local.insertItem(item.toLocalEntity(false)) }

    }

    @Test
    fun `must get list of  items and convert to domain `() = runTest{

        coEvery { local.getAllItems() } returns flowOf(itemEntityLocalList)
        //quando
        val result = repository.getAllLocalItems().first()

        //entao
        assertEquals(2,  result.size)
        assertEquals("Chave 10", result[0].name)
        assertEquals("Chave 12", result[1].name)


    }

    @Test
    fun `must synchronize pending items and send to db remote`() = runTest {

        coEvery { local.getPendingItems() } returns itemEntityLocalList
        coEvery { remote.createItem(any()) } returns Unit
        coEvery { local.updateItem(any()) } returns Unit

        repository.syncPendingItems()
        coVerify (exactly = 2) { remote.createItem(any())}
        coVerify(exactly = 2) {local.updateItem(match { it.isSynchronized })  }
    }
}