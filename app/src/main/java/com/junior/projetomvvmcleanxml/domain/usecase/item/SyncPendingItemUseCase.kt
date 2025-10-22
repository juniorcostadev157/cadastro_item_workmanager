package com.junior.projetomvvmcleanxml.domain.usecase.item

import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import javax.inject.Inject

class SyncPendingItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(){
        repository.syncPendingItems()
    }
}