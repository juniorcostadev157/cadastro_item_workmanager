package com.junior.projetomvvmcleanxml.domain.usecase.userpreference

import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class GetNameSessionUseCaseTest {

    private val repository = mockk<UserSessionDataSource>(relaxed = true)

    @Test
    fun `should call repository method once`(){
        val userName = "Junior"
        every { repository.getUserName() } returns userName

        val result = GetNameSessionUseCase(repository).invoke()

        assert(result == userName)
        assert(result != null)
        verify(exactly = 1) { repository.getUserName() }

    }
}