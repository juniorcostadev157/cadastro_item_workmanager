package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase

import com.junior.projetomvvmcleanxml.data.repository.authrepository.AuthError
import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CreateRegisterValidationUseCaseTest {

    private val repository = mockk<AuthRepository>(relaxed = true)

    private val validEmail = "teste@teste.com"
    private val validPassword = "123456"

    @Test
    fun `should return invalid when password is too short`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val useCase = CreateRegisterValidationUseCase(repository, dispatcher)
        val result  = useCase(validEmail, "123")

        assertFalse(result.success)
        assertEquals("Senha tem que ter pelo menos 6 caracteres", result.errorMessage)
        coVerify(exactly = 0) {repository.register(any(), any())  }
    }

    @Test
    fun `should return success when repository returns success`() = runTest{
        coEvery { repository.register(validEmail, validPassword) } returns Result.success("ok")
        val dispatcher = StandardTestDispatcher(testScheduler)
        val useCase = CreateRegisterValidationUseCase(repository, dispatcher)
        val result = useCase(validEmail, validPassword)

        assertTrue { result.success }
        assertEquals("ok", result.data)
        coVerify (exactly = 1){ repository.register(validEmail,validPassword) }
    }

    @Test
    fun `should return invalid  when repository returns AuthError`() = runTest {
        val error = AuthError.EmailAlreadyInUse

        coEvery { repository.register(validEmail, validPassword) } returns Result.failure(error)

        val dispatcher = StandardTestDispatcher(testScheduler)
        val useCase = CreateRegisterValidationUseCase(repository, dispatcher)
        val result = useCase(validEmail, validPassword)

        assertFalse { result.success }
        assertEquals(error.messageError, result.errorMessage)
        coVerify (exactly = 1) { repository.register(validEmail , validPassword) }

    }
    @Test
    fun `should handle unknown error gracefully`() = runTest {
        val error = RuntimeException("falha desconhecida")
        coEvery { repository.register(validEmail, validPassword) } returns Result.failure(error)

        val dispatcher = StandardTestDispatcher(testScheduler)
        val useCase = CreateRegisterValidationUseCase(repository, dispatcher)
        val result = useCase(validEmail, validPassword)

        assertFalse { result.success }
        assertNull(result.errorMessage)
        coVerify (exactly = 1) { repository.register(validEmail, validPassword) }



    }


}