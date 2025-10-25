package com.junior.projetomvvmcleanxml.domain.usecase.sync_data

import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetSyncStatusUseCaseTest {

    private val repository = mockk<SyncPreferenceRepository>(relaxed = true)

    @Test
    fun `should return true when sync is enabled`() = runTest {
        coEvery { repository.isSyncEnabled() } returns true

        val useCase = GetSyncStatusUseCase(repository)
        val result = useCase()

        assert(result)
        coVerify (exactly = 1){ repository.isSyncEnabled() }
    }
}