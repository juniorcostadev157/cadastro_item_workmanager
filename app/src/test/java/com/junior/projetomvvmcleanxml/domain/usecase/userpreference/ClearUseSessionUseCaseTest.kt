package com.junior.projetomvvmcleanxml.domain.usecase.userpreference

import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class ClearUseSessionUseCaseTest {
    private val repository = mockk<UserSessionDataSource>()

    @Test
    fun `should call repository method once`(){
        every { repository.clear() } returns Unit
        ClearUseSessionUseCase(repository).invoke()

        verify(exactly = 1) { repository.clear() }


    }
}