package com.junior.projetomvvmcleanxml.domain.usecase.userpreference

import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import javax.inject.Inject

class ClearUseSessionUseCase @Inject constructor(private val repository: UserSessionDataSource) {
    operator fun invoke(){
        repository.clear()
    }
}