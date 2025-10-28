package com.junior.projetomvvmcleanxml.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.junior.projetomvvmcleanxml.domain.model.user.Users
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.LoginValidationUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationAuthFieldsUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationResult
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.SaveUserSessionUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.users.GetUserByIdUseCase
import com.junior.projetomvvmcleanxml.presentation.login.LoginUiState
import com.junior.projetomvvmcleanxml.presentation.login.LoginViewModel
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
import org.junit.Test


class LoginViewModelTest {
    @get: Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginUseCase = mockk<LoginValidationUseCase>(relaxed = true)
    private val saveUserSession = mockk<SaveUserSessionUseCase>(relaxed = true)
    private val getUserByIdUseCase = mockk<GetUserByIdUseCase>(relaxed = true)
    private val validationFields = mockk<ValidationAuthFieldsUseCase>(relaxed = true)

    private lateinit var viewModel: LoginViewModel

    val userID = "test123"
    val email = "test@test.com"
    val password = "123456"
    val errorMessage = "Error"
    val name = "test"

    val user = Users(
        id = userID,
        nome = name,
        email = email
    )

    @Before
    fun setup(){
        viewModel = LoginViewModel(
            loginUseCase,
            saveUserSession,
            getUserByIdUseCase,
            validationFields
        )
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should set loading the Success when login is valid`()= runTest {
        //cenario  - given
        every { validationFields(email, password) } returns ValidationResult (true)
        coEvery { loginUseCase(email, password) } returns ValidationResult (true, data = userID)
        coEvery { getUserByIdUseCase(userID) } returns user

        val observer = mockk<Observer<LoginUiState>>(relaxed = true)
        viewModel.loginStage.observeForever (observer)

        //ação - when
        viewModel.login(email, password)
        advanceUntilIdle()

        //verificação  - then
        verifyOrder {
            observer.onChanged(LoginUiState.Loading)
            observer.onChanged(LoginUiState.Success)
        }

        coVerify { loginUseCase(email, password) }
        coVerify { saveUserSession(userID, name) }

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should emit Error when validation fields fails`() = runTest {
        //cenario  - given
        every { validationFields(email, password) } returns ValidationResult (false,errorMessage )

        val observer = mockk<Observer<LoginUiState>>(relaxed = true)
        viewModel.loginStage.observeForever (observer)

        //Ação - When
        viewModel.login(email, password)
        advanceUntilIdle()

        //verificação - then
        verifyOrder {
            observer.onChanged(LoginUiState.Loading)
            observer.onChanged(LoginUiState.Error(errorMessage))
        }


        coVerify(exactly = 0) { loginUseCase(email, password) }
    }

}