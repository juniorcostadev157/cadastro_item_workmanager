package com.junior.projetomvvmcleanxml.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.junior.projetomvvmcleanxml.core.AnalyticsLogger
import com.junior.projetomvvmcleanxml.core.CrashlyticsLogger
import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository
import com.junior.projetomvvmcleanxml.domain.usecase.item.SyncPendingItemUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.userpreference.GetUserIdSessionUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncItemWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncPendingItemUseCase: SyncPendingItemUseCase,
    private val getUserIdSessionUseCase: GetUserIdSessionUseCase,
    private val syncSettings: SyncPreferenceRepository

): CoroutineWorker(context, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {

        if (!syncSettings.isSyncEnabled()){
            AnalyticsLogger.logEvent("sync_skipped_disabled")
            return Result.success()
        }

        val userId = getUserIdSessionUseCase()

        if (userId.isNullOrBlank()){
            AnalyticsLogger.logEvent("sync_skipped_user_not_logged")
            return Result.success()
        }

        AnalyticsLogger.logEvent("sync_item_started")

        return try {
            syncPendingItemUseCase()
            AnalyticsLogger.logEvent("sync_item_success")
            WorkerNotificationHelper.showNotification(
                applicationContext,
                "Sincronização",
                "Itens sincronizados com sucesso!"
            )
            Result.success()
        } catch (e: Exception) {

            AnalyticsLogger.logEvent(
                eventName = "sync_item_error",
                params = mapOf("message" to (e.message ?: "Erro desconhecido"))
            )

            CrashlyticsLogger.logCrash(e)
            CrashlyticsLogger.setCustomKey("worker_name", "SyncItemWorker")
            CrashlyticsLogger.logMessage("Erro durante a sincronização: ${e.message}")

            WorkerNotificationHelper.showNotification(
                applicationContext,
                "Sincronização",
                "Falha na sincronização: ${e.message}"
            )
            Result.retry()
        }
    }
}