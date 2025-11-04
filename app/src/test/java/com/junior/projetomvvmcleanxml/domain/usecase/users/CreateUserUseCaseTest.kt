package com.junior.projetomvvmcleanxml.domain.usecase.users

import com.junior.projetomvvmcleanxml.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CreateUserUseCaseTest {

    private val repository = mockk<UserRepository>(relaxed = true)

    @Test
    fun `should call repository method once`() = runTest{

        val dispatcher = StandardTestDispatcher(testScheduler)
        coEvery { repository.createUser(any()) } returns Unit

        CreateUsersUseCase(repository, dispatcher).invoke(
            "Junior", "teste@teste.com", "1"
        )

        coVerify (exactly = 1){ repository.createUser(any()) }
    }
}