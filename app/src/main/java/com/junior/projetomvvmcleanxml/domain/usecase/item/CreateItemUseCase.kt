package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.di.IoDispatcher
import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateItemUseCase @Inject constructor(
    private val repository: ItemRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(name: String, idUser: String?) = withContext(ioDispatcher){
        val item = Item(
            id = "",
            name = name,
            createdBy = idUser
        )
       repository.createItem(item)
    }
}