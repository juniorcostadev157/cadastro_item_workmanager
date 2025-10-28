package com.junior.projetomvvmcleanxml.presentation.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.CreateRegisterValidationUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationAuthFieldsUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationResult
import com.junior.projetomvvmcleanxml.domain.usecase.users.CreateUsersUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.SaveUserSessionUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.users.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CadastroViewModel @Inject constructor(
    private val createUsers: CreateUsersUseCase,
    private val createRegister: CreateRegisterValidationUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val validationFieldsUseCase: ValidationAuthFieldsUseCase

): ViewModel() {

    private val _cadastroState = MutableLiveData<CadastroUiState>(CadastroUiState.Empty)
    val cadastroState: LiveData<CadastroUiState> = _cadastroState

    fun cadastro(nome: String, email: String, password: String) {
        _cadastroState.value = CadastroUiState.Loading
        viewModelScope.launch {
            if (!validateFields(email, password)) return@launch

            val result = registerUser(email, password)
            if (result.success) {
                handleSuccessfulRegister(nome, email, result.data)
            } else {
                handleError(result.errorMessage)
            }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        val validation = validationFieldsUseCase(email, password)
        return if (!validation.success) {
            _cadastroState.value = CadastroUiState.Error(validation.errorMessage ?: "Erro desconhecido")
            false
        } else true
    }

    private suspend fun registerUser(email: String, password: String): ValidationResult {
        return createRegister(email, password)
    }

    private suspend fun handleSuccessfulRegister(nome: String, email: String, userId: String?) {
        userId ?: return handleError("ID do usuário nulo")

        createUsers(nome, email, userId)
        val infoUser = getUserByIdUseCase(userId)
        infoUser?.let {
            saveUserSessionUseCase(it.id, it.nome)
        }
        _cadastroState.value = CadastroUiState.Success
    }

    private fun handleError(message: String?) {
        _cadastroState.value = CadastroUiState.Error(message ?: "Erro desconhecido")
    }




}