package com.junior.projetomvvmcleanxml.domain.usecase.users

import com.junior.projetomvvmcleanxml.domain.model.user.Users
import com.junior.projetomvvmcleanxml.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUSerByIdUseCaseTest {
    private val repository = mockk<UserRepository>(relaxed = true)

    @Test
    fun `should call repository method once and should return Users`() = runTest{
        val users = Users(
            id = "1",
            nome = "Junior",
            email = "teste@teste.com"
        )
        val dispatcher = StandardTestDispatcher(testScheduler)
        coEvery { repository.getUserById("1") } returns users
        val result = GetUserByIdUseCase(repository, dispatcher).invoke("1")

        assertEquals(result, users)
        assertEquals(result?.id, users.id)
        assertEquals(result?.nome, users.nome)
        assertEquals(result?.email, users.email)

        coEvery { repository.getUserById("1") }
    }


}