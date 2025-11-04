package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ValidationAuthFieldsUseCaseTest {

    private val validEmail = "test@test.com"
    private val validPassword = "123456"
    private val invalidEmail = "test.com"

    private val useCase = ValidationAuthFieldsUseCase()

    @Test
    fun `should return invalid when email is invalid`(){

        val result = useCase(invalidEmail, validPassword)


        assertFalse { result.success }
        assertEquals("Email Invalido", result.errorMessage)

    }
    @Test
    fun `should return invalid when password is blank`(){
        val result = useCase(validEmail, "")

        assertFalse { result.success }
        assertEquals("Preencha a Senha", result.errorMessage)
    }
    @Test
    fun `should return valid when email and password are correct`(){
        val result = useCase(validEmail, validPassword)

        assertTrue { result.success }
        assertEquals(null, result.errorMessage)
    }


}