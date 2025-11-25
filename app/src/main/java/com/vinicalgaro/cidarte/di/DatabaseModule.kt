package com.vinicalgaro.cidarte.di

import android.app.Application
import androidx.room.Room
import com.vinicalgaro.cidarte.data.local.CidarteDatabase
import com.vinicalgaro.cidarte.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): CidarteDatabase {
        return Room.databaseBuilder(
            app,
            CidarteDatabase::class.java,
            "cidarte_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(db: CidarteDatabase): MovieDao {
        return db.movieDao
    }
}