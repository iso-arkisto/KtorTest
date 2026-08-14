package com.yourname.ktortest.di

import com.yourname.ktortest.data.repository.DatastoreOperationsImpl
import com.yourname.ktortest.domain.repository.DatastoreOperations
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDatastoreOperations(datastoreOperationsImpl: DatastoreOperationsImpl): DatastoreOperations
}