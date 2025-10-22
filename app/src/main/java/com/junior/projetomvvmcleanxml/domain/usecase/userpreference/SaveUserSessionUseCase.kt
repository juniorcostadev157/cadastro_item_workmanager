package com.junior.projetomvvmcleanxml.domain.usecase.userpreference

import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(private val repository: UserSessionDataSource) {

    operator fun invoke(userId: String, name: String) {
        repository.saveUserId(userId, name)
    }
}