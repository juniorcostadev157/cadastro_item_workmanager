package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase

import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LoginValidationUseCaseTest {

    private val repository = mockk<AuthRepository>(relaxed = true)
    private val email = "test@test.com"
    private val password = "123456"

    @Test
    fun `should return success if the repository is successful`() = runTest{

        coEvery { repository.login(email, password) } returns Result.success("ok")

        val dispatcher = StandardTestDispatcher(testScheduler)
        val useCase = LoginValidationUseCase(repository, dispatcher)
        val result = useCase(email, password)

        assertTrue { result.success }
        assertEquals("ok", result.data)
        coVerify (exactly = 1) { repository.login(email, password) }

    }
    @Test
    fun `should return failure if the repository is returns failure`() = runTest{
        val error = RuntimeException("Erro desconhecido")
        coEvery { repository.login(email, password) } returns Result.failure(error)

        val dispatcher = StandardTestDispatcher(testScheduler)
        val useCase = LoginValidationUseCase(repository, dispatcher)
        val result = useCase(email, password)

        assertFalse{ result.success }
        assertNull(result.errorMessage)
        assertEquals(null, result.errorMessage)
        coVerify ( exactly = 1 ){ repository.login(email, password) }
    }



}