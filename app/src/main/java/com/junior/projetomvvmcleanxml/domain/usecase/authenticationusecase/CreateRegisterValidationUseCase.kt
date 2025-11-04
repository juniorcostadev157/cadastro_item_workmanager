package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase

import com.junior.projetomvvmcleanxml.data.repository.authrepository.AuthError
import com.junior.projetomvvmcleanxml.di.IoDispatcher
import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateRegisterValidationUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String): ValidationResult {


        if (password.length < 6){
            return ValidationResult(false, "Senha tem que ter pelo menos 6 caracteres")
        }

        val result  = withContext(ioDispatcher){
            repository.register(email, password)
        }


        return if (result.isSuccess){

            ValidationResult(true, data =result.getOrNull())
        }else{
            ValidationResult(false, (result.exceptionOrNull() as? AuthError)?.messageError)
        }

    }
}