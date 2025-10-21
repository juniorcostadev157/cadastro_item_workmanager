package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.model.item.Item
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import javax.inject.Inject

class CreateItemUseCase @Inject constructor( private val repository: ItemRepository) {

    suspend operator fun invoke(name: String, idUser: String?){
        val item = Item(
            id = "",
            name = name,
            createdBy = idUser
        )
       repository.createItem(item)
    }
}