package com.junior.projetomvvmcleanxml.domain.usecase.sync_data

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.junior.projetomvvmcleanxml.worker.SyncItemWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StartSyncWorkUseCase @Inject constructor(
    @ApplicationContext private val context: Context
){
    fun startPeriodicSync(){

        //restricoes
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // so roda se tiver internet
            .setRequiresCharging(false) // so pode rodar mesmo sem carregar
            .build()

        //criando requisicao periodica
        val workRequest = PeriodicWorkRequestBuilder<SyncItemWorker>(
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints) //aplicando as restricoes
            .addTag("sync_tag") //adiciona uma tg(pra controle)
            .build()

        //Agenda de forma unica (evita duplicatas)
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_item_worker", //nome unico
            ExistingPeriodicWorkPolicy.KEEP, // mantem o agendamento atual
            workRequest
        )


    }
    // Cancela o agendamento de sincronização.
    fun cancelSync(){
        WorkManager.getInstance(context).cancelUniqueWork("sync_item_worker")
    }

    //observar o status do worker
    fun observeSyncStatus(lifecyleOwner: LifecycleOwner,onStatusChange:(WorkInfo.State) -> Unit){
        WorkManager.getInstance(context)
            .getWorkInfosByTagLiveData("sync_tag")
            .observe(lifecyleOwner){works->
                works.firstOrNull()?.let {
                    onStatusChange(it.state)
                }
            }
    }
}