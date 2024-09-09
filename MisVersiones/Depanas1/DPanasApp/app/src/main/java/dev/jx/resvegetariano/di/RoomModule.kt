package dev.jx.resvegetariano.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.database.AppDatabase
import dev.jx.resvegetariano.data.database.dao.CarritoItemDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.DATABASE_NAME)
        ).build()
    }

    @Provides
    @Singleton
    fun provideCarritoItemDao(database: AppDatabase): CarritoItemDao {
        return database.carritoItemDao()
    }
}