package com.junior.projetomvvmcleanxml.domain.usecase.users

import com.junior.projetomvvmcleanxml.di.IoDispatcher
import com.junior.projetomvvmcleanxml.domain.model.user.Users
import com.junior.projetomvvmcleanxml.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUsersUseCase @Inject constructor(
    private val repository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator  fun invoke(nome: String, email: String, id: String) = withContext(ioDispatcher){

        val user = Users(
            id = id,
            nome  = nome,
            email = email
        )
         repository.createUser(user)
    }
}