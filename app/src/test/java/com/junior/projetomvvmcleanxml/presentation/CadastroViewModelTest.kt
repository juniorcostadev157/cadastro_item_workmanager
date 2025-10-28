package com.junior.projetomvvmcleanxml.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.CreateRegisterValidationUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationAuthFieldsUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationResult
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.SaveUserSessionUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.users.CreateUsersUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.users.GetUserByIdUseCase
import com.junior.projetomvvmcleanxml.presentation.cadastro.CadastroUiState
import com.junior.projetomvvmcleanxml.presentation.cadastro.CadastroViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

class CadastroViewModelTest {
    @get: Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val createUsers = mockk<CreateUsersUseCase>(relaxed = true)
    private val createRegister = mockk<CreateRegisterValidationUseCase>(relaxed = true)
    private val saveUserSession = mockk<SaveUserSessionUseCase>(relaxed = true)
    private val getUserById = mockk<GetUserByIdUseCase>(relaxed = true)
    private val validateFields = mockk<ValidationAuthFieldsUseCase>(relaxed = true)


    private lateinit var viewModel: CadastroViewModel
    val userID = "test123"
    val email = "test@test.com"
    val password = "123456"
    val errorMessage = "Error"

    @Before
    fun setup(){
        viewModel = CadastroViewModel(
            createUsers,
            createRegister,
            saveUserSession,
            getUserById,
            validateFields
        )
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should set loading the Success when registration is valid`() = runTest{
       //cenario

        every { validateFields(any(), any()) } returns ValidationResult(true)
        coEvery { createRegister(any(), any()) } returns ValidationResult(true, data = userID)
        coEvery { getUserById(userID) } returns mockk(relaxed = true)

        val observer = mockk<Observer<CadastroUiState>>(relaxed = true)
        viewModel.cadastroState.observeForever ( observer )

        //ação
        viewModel.cadastro("test", email, "123456")
        advanceUntilIdle()


        //verificaçao
        verifyOrder {
            observer.onChanged(CadastroUiState.Loading)
            observer.onChanged(CadastroUiState.Success)
        }


        coVerify { createRegister(email, "123456") }
        coVerify { createUsers("test", email, userID) }
        coVerify { saveUserSession(any(), any()) }


    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun`should emit Error when validation fields fails`() = runTest{
      //Given / Cenario
        every { validateFields(any(), any()) } returns ValidationResult(false, errorMessage)

        val observer = mockk<Observer<CadastroUiState>>(relaxed = true)
        viewModel.cadastroState.observeForever (observer)

        //When / Ação
        viewModel.cadastro("test", email, password)
        advanceUntilIdle()

        //Then / Verificação
        verifyOrder {
            observer.onChanged(CadastroUiState.Loading)
            observer.onChanged(CadastroUiState.Error(errorMessage))
        }

        coVerify (exactly = 0) { createRegister(any(),any()) }
        coVerify (exactly = 0){ createUsers(any(), any(), any() ) }



    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should emit Error when register fails`() = runTest{
        //Given / Cenario
        every { validateFields(any(), any()) } returns ValidationResult(true)
        coEvery { createRegister(any(), any()) } returns ValidationResult(false, errorMessage)

        val observer = mockk<Observer<CadastroUiState>>(relaxed = true)
        viewModel.cadastroState.observeForever ( observer )

        //When / Ação
        viewModel.cadastro("test", email, password)
        advanceUntilIdle()

        //Then / Verificação
        verifyOrder {
            observer.onChanged(CadastroUiState.Loading)
            observer.onChanged(CadastroUiState.Error(errorMessage))
        }

        coVerify (exactly = 0) { createUsers (any(), any(), any()) }
    }


}
