package com.junior.projetomvvmcleanxml.data.datasource.local.sharedpreference

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit
import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository

class SyncPreference @Inject constructor(
    @ApplicationContext private val context: Context
): SyncPreferenceRepository {

    private val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)

    override fun isSyncEnabled(): Boolean {
        return prefs.getBoolean("sync_enabled", true)
    }

    override fun setSyncEnabled(enabled: Boolean) {
        prefs.edit { putBoolean("sync_enabled", enabled) }
    }

}