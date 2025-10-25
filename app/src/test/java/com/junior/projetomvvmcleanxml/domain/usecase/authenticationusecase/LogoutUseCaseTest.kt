package com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase

import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LogoutUseCaseTest {

    private val repository = mockk<AuthRepository>(relaxed = true)
    @Test
    fun `should call logout method once`() = runTest{

        LogoutUseCase(repository).invoke()

        coVerify(exactly = 1) { repository.logout()}
    }

}