package com.junior.projetomvvmcleanxml.domain.usecase.userpreference

import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class GetUserIdSessionUseCaseTest {

    private val repository = mockk<UserSessionDataSource>(relaxed = true)

    @Test
    fun `should call repository method once and returns id`(){
        val id = "1"
        every { repository.getUserId() } returns id
        val result = GetUserIdSessionUseCase(repository).invoke()

        assert(result == id)
        assertEquals(result, id)
        verify(exactly = 1) { repository.getUserId() }

    }

}