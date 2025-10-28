package com.junior.projetomvvmcleanxml.domain.usecase.userpreference

import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SaveUserSessionUseCaseTest {

    private val repository = mockk<UserSessionDataSource>(relaxed = true)

    @Test
    fun `should test save user session and call repository method once`(){
        every {repository.saveUserId("1", "Junior") }returns Unit

        SaveUserSessionUseCase(repository).invoke("1", "Junior")

        verify(exactly = 1) { repository.saveUserId("1", "Junior") }
    }

}