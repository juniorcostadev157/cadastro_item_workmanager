package com.junior.projetomvvmcleanxml.data


import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.junior.projetomvvmcleanxml.data.datasource.remote.FirebaseAuthDataSource
import com.junior.projetomvvmcleanxml.data.repository.authrepository.AuthError
import com.junior.projetomvvmcleanxml.data.repository.authrepository.AuthRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthRepositoryImpl {

    private val auth = mockk<FirebaseAuthDataSource>(relaxed = true)
    private val repository = AuthRepositoryImpl(auth)

    val email = "teste@teste.com"
    val senha = "123456"

    val fakeCredentialsExceptionException = mockk<FirebaseAuthInvalidCredentialsException>()
    val fakeNetWorkExpectation = mockk<FirebaseNetworkException>()

    @Test
    fun `register should return success`() = runTest {
        coEvery { auth.register(email, senha) } returns Result.success("ok")
        val result = repository.register(email, senha)

        assertTrue { result.isSuccess }
        assertEquals("ok", result.getOrNull())
        coVerify(exactly = 1) { auth.register(email, senha) }
    }

    @Test
    fun `register should return InvalidCredentials when FirebaseAuthInvalidCredentialsException is thrown`() = runTest {
        coEvery { auth.register(any(), any()) } returns Result.failure(
            fakeCredentialsExceptionException
        )
        val result = repository.register(email, "wrong")

        assertTrue { result.isFailure }
        assertTrue { result.exceptionOrNull() is AuthError.InvalidCredentials }
    }
    @Test
    fun `register should return NoInternetConnection when FirebaseNetworkException is thrown`() = runTest{
        coEvery { auth.register(any(), any()) } returns Result.failure(
            fakeNetWorkExpectation
        )

        val result = repository.register(email, senha)

        assertTrue { result.isFailure }
        assertTrue { result.exceptionOrNull() is AuthError.NoInternetConnection }
    }

    @Test
    fun `login should return success`() = runTest {
        //given
        coEvery {  auth.login(email, senha)} returns Result.success("ok")
        //when
        val result = repository.login(email, senha)
        //then
        assertTrue { result.isSuccess }
        assertEquals("ok", result.getOrNull())
        coVerify (exactly =  1){ auth.login(email, senha) }
    }

    @Test
    fun `should check if it is called at least once`(){

        every { auth.logout() } returns Unit

        repository.logout()

        verify (exactly = 1){ auth.logout() }
    }
}