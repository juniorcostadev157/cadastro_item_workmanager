package com.junior.projetomvvmcleanxml.domain.usecase.sync_data

import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository
import javax.inject.Inject

class GetSyncStatusUseCase @Inject constructor(
    private val repository: SyncPreferenceRepository
) {
    operator fun invoke(): Boolean{
        return repository.isSyncEnabled()
    }
}