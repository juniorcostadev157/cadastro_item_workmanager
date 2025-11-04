package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CreateItemUseCaseTest {

    private val repository = mockk<ItemRepository>(relaxed = true)


    @Test
    fun `should call createItem method once and call repository`() = runTest{


        val dispatcher = StandardTestDispatcher(testScheduler)

        CreateItemUseCase(repository, dispatcher).invoke("name", "id")
        coVerify (exactly = 1){ repository.createItem(any()) }



    }

}