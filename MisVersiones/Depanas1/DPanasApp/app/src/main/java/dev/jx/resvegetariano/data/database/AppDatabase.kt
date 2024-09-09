package dev.jx.resvegetariano.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jx.resvegetariano.data.database.dao.CarritoItemDao
import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity

@Database(entities = [CarritoItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carritoItemDao(): CarritoItemDao
}