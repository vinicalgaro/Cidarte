package com.vinicalgaro.cidarte.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinicalgaro.cidarte.data.local.dao.MovieDao
import com.vinicalgaro.cidarte.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class CidarteDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}