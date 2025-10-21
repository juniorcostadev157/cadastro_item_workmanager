package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListItemUseCase @Inject constructor(private val repository: ItemRepository) {

    operator fun invoke(): Flow<List<Item>>{
        return repository.getAllItem()
    }
}