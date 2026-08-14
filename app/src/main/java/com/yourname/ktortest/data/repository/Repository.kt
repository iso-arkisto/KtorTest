package com.yourname.ktortest.data.repository

import com.yourname.ktortest.domain.repository.DatastoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(val datastoreOperations: DatastoreOperations) {
    suspend fun saveOnboardingState(isCompleted: Boolean) {
        datastoreOperations.saveOnboardingState(isCompleted)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return datastoreOperations.readOnboardingState()
    }
}