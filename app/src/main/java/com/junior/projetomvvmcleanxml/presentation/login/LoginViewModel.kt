package com.junior.projetomvvmcleanxml.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.LoginValidationUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.authenticationusecase.ValidationAuthFieldsUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.SaveUserSessionUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.users.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginValidationUseCase,
    private val saveUserSession: SaveUserSessionUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val validationFieldsUseCase: ValidationAuthFieldsUseCase
): ViewModel() {

    private val _loginStage = MutableLiveData<LoginUiState>(LoginUiState.Empty)
    val loginStage: LiveData<LoginUiState> = _loginStage


    fun login(email: String, senha: String){
        _loginStage.value = LoginUiState.Loading

        viewModelScope.launch {
            val  validation = loginUseCase(email, senha)
            val result = validationFieldsUseCase(email, senha)

            if (validation.success){
                val userId = validation.data
                val infoUser = getUserByIdUseCase(userId ?:"")
                if (infoUser != null){
                    saveUserSession(userId ?:"", infoUser.nome)
                }

                _loginStage.value = LoginUiState.Success
            }else{
                _loginStage.value = LoginUiState.Error(result.errorMessage ?: "")
                _loginStage.value = LoginUiState.Error(validation.errorMessage ?: "Erro desconhecido")

            }


        }
    }

}