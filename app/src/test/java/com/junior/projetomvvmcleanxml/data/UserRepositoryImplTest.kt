package com.junior.projetomvvmcleanxml.data

import com.junior.projetomvvmcleanxml.data.datasource.remote.FirebaseUserDataSource
import com.junior.projetomvvmcleanxml.data.repository.UserRepositoryImpl
import com.junior.projetomvvmcleanxml.domain.model.user.Users
import com.junior.projetomvvmcleanxml.domain.model.user.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryImplTest {

    private val remote = mockk<FirebaseUserDataSource>(relaxed = true)
    private val repository = UserRepositoryImpl(remote)

    val user = Users(
        id = "1",
        email = "user@teste.com",
        nome = "teste"
    )



    @Test
    fun `must create a user`() = runTest{

        repository.createUser(user)

        coVerify{ remote.createUser(user.toEntity())  }
    }

    @Test
    fun `return user by id search`()  = runTest {

        //Given
        coEvery { remote.getUserById(user.id) } returns user.toEntity()

        //When
        val result =  repository.getUserById("1")

        //Then
        coVerify (exactly = 1){ remote.getUserById("1") }

        assertEquals("1", result?.id)
        assertEquals("teste", result?.nome)
        assertEquals("user@teste.com", result?.email)
    }
}