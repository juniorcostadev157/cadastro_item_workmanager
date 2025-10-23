package com.junior.projetomvvmcleanxml.presentation.principal.list_item_room_fragment

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.junior.projetomvvmcleanxml.domain.usecase.item.ListLocalItemsUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.sync_data.GetSyncStatusUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.sync_data.StartSyncWorkUseCase
import com.junior.projetomvvmcleanxml.domain.usecase.sync_data.ToggleSyncUseCase
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_firebase_fragment.ListItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListRoomViewModel @Inject constructor(
    private val listLocal: ListLocalItemsUseCase,
    private val toggleSyncUseCase: ToggleSyncUseCase,
    getSyncStatus: GetSyncStatusUseCase,
    private val startSync: StartSyncWorkUseCase
) : ViewModel(){

    private val _uiState = MutableLiveData<ListItemUiState>()
    val uiState: LiveData<ListItemUiState> = _uiState

    private val _syncEnabled = MutableLiveData<Boolean>()
    val syncEnabled :LiveData<Boolean> = _syncEnabled

    private val _workState = MutableLiveData<WorkInfo.State>()
    val workState: LiveData<WorkInfo.State> = _workState

    init {
        _syncEnabled.value = getSyncStatus()
        loadItems()
    }

    fun toggleSync(enabled: Boolean){
        toggleSyncUseCase(enabled)
        _syncEnabled.value = enabled
        if (enabled){
            startSync.startPeriodicSync()

        }else{
            startSync.cancelSync()
        }
    }

    fun loadItems(){
        viewModelScope.launch {
            _uiState.value = ListItemUiState.Loading

            try {
               listLocal().collect {items->
                   if (items.isEmpty()){
                       _uiState.value = ListItemUiState.Empty
                   }else{
                       _uiState.value = ListItemUiState.Success(items)
                   }

               }


            }catch (e: Exception){
                _uiState.value = ListItemUiState.Error(e.message ?: "Erro desconhecido")

            }
        }
    }

    fun observeSyncWork(lifecycleOwner: LifecycleOwner){
        startSync.observeSyncStatus(lifecycleOwner){state->
            _workState.value = state
        }
    }
}