package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase

import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import javax.inject.Inject


class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {

    operator fun invoke(){
        repository.logout()
    }

}