package com.junior.projetomvvmcleanxml.domain.usecase.sync_data

import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ToggleSyncUseCaseTest {
    private val repository = mockk<SyncPreferenceRepository>(relaxed = true)

    @Test
    fun`should call repository method once`() = runTest {

        every { repository.setSyncEnabled(true) } returns Unit

        ToggleSyncUseCase(repository).invoke(true)

        verify { repository.setSyncEnabled(true) }
    }
}