package com.yourname.ktortest.di

import com.yourname.ktortest.data.local.LanguageDatabase
import com.yourname.ktortest.data.remote.KtorApi
import com.yourname.ktortest.data.repository.DatastoreOperationsImpl
import com.yourname.ktortest.data.repository.RemoteDataSourceImpl
import com.yourname.ktortest.data.repository.Repository
import com.yourname.ktortest.domain.repository.DatastoreOperations
import com.yourname.ktortest.domain.repository.RemoteDataSource
import com.yourname.ktortest.domain.usecase.UseCases
import com.yourname.ktortest.domain.usecase.get_all_languages.GetAllLanguagesUseCase
import com.yourname.ktortest.domain.usecase.read_onboarding.ReadOnboardingUseCase
import com.yourname.ktortest.domain.usecase.save_onboarding.SaveOnboardingUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDatastoreOperations(datastoreOperationsImpl: DatastoreOperationsImpl): DatastoreOperations

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        ktorApi: KtorApi,
        database: LanguageDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(ktorApi, database)
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repository: Repository
    ): UseCases {
        return UseCases(
            readOnboardingUseCase = ReadOnboardingUseCase(repository),
            saveOnboardingUseCase = SaveOnboardingUseCase(repository),
            getAllLanguagesUseCase = GetAllLanguagesUseCase(repository)
        )
    }
}