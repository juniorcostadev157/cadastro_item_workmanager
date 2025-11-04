package com.junior.projetomvvmcleanxml.domain.usecase.sync_data

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlin.test.Test

class StartSyncWorkUseCaseTest {

    @Test
    fun `should call enqueueUniquePeriodicWork when starting sync`() {
        val mockContext = mockk<Context>(relaxed = true)
        val mockWorkManager = mockk<WorkManager>(relaxed = true)

        mockkStatic(WorkManager::class)
        every { WorkManager.getInstance(mockContext) } returns mockWorkManager

        val useCase = StartSyncWorkUseCase(mockContext)
        useCase.startPeriodicSync()

        verify {
            mockWorkManager.enqueueUniquePeriodicWork(
                "sync_item_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                any()
            )
        }
    }

}