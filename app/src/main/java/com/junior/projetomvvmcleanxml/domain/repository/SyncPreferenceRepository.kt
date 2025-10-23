package com.junior.projetomvvmcleanxml.domain.repository

interface SyncPreferenceRepository {

    fun isSyncEnabled(): Boolean

    fun setSyncEnabled(enabled: Boolean)

}