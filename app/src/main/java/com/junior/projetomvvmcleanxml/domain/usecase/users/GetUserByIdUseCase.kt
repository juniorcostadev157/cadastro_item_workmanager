package com.junior.projetomvvmcleanxml.domain.usecase.users


import com.junior.projetomvvmcleanxml.domain.model.user.Users
import com.junior.projetomvvmcleanxml.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(userId: String): Users?{
        return  repository.getUserById(userId)

    }
}