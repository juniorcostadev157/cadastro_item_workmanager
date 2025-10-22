package com.junior.projetomvvmcleanxml.domain.usecase.sync_data

import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository
import javax.inject.Inject

class ToggleSyncUseCase @Inject constructor(
    private val repository: SyncPreferenceRepository
) {
    operator fun invoke(enabled: Boolean){
        repository.setSyncEnabled(enabled)
    }
}