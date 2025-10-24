package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase

import javax.inject.Inject

class ValidationAuthFieldsUseCase @Inject constructor(){

    operator fun invoke(email: String, password: String): ValidationResult {

        if (email.isBlank() || !email.contains("@")) {
            return ValidationResult(false, "Email Invalido")
        }

        if (password.isBlank()) {
            return ValidationResult(false, "Preencha a Senha")
        }

        return ValidationResult(success = true)

    }
}