package com.yourname.ktortest.domain.repository

import kotlinx.coroutines.flow.Flow

interface DatastoreOperations {
    suspend fun saveOnboardingState(isCompleted: Boolean)
    fun readOnboardingState(): Flow<Boolean>
}