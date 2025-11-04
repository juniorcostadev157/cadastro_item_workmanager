package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ListLocalItemsUseCase @Inject constructor(
    private val repository: ItemRepository,
    ) {

    operator fun invoke(): Flow<List<Item>> {
        return repository.getAllLocalItems()
            .flowOn(Dispatchers.IO)

    }
}