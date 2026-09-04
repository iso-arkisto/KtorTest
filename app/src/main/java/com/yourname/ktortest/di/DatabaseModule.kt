package com.yourname.ktortest.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yourname.ktortest.data.local.LanguageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LanguageDatabase =
        Room.databaseBuilder(
        context = context,
        klass = LanguageDatabase::class.java,
            name = "language_database.db"
    ).build()
}