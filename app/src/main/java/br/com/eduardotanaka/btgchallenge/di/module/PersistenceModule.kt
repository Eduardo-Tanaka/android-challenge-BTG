package br.com.eduardotanaka.btgchallenge.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import br.com.eduardotanaka.btgchallenge.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * configurações de banco e SharedPreferences
 */
@Module
class PersistenceModule {

    companion object {
        private const val DATABASE_NAME = "database_name.db"
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(applicationContext: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(applicationContext)

    @Singleton
    @Provides
    fun provideAppDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}