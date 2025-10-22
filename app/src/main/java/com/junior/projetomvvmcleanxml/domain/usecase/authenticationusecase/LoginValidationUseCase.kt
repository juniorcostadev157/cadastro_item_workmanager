package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase


import com.junior.projetomvvmcleanxml.data.repository.authrepository.AuthError
import com.junior.projetomvvmcleanxml.di.IoDispatcher
import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginValidationUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String , password: String): ValidationResult{

        if (email.isBlank() || !email.contains("@")){
            return ValidationResult(false, "Email Invalido")
        }

        if (password.isBlank()){
            return ValidationResult(false, "Preencha o Senha")
        }

        val result = withContext(ioDispatcher) {
            repository.login(email, password)
        }
        return if (result.isSuccess){
            val useId = result.getOrNull()
            ValidationResult(true, data = useId)

        }else{

            ValidationResult(false, (result.exceptionOrNull() as? AuthError)?.messageError)
        }
        }


    }
