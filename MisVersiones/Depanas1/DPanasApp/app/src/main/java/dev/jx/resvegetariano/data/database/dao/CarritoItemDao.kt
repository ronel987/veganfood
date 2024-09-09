package dev.jx.resvegetariano.data.database.dao

import androidx.room.*
import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity

@Dao
interface CarritoItemDao {

    @Query("SELECT * FROM CarritoItem")
    suspend fun getAll(): List<CarritoItemEntity>

    @Query("SELECT * FROM carritoitem WHERE cantidad BETWEEN 1 AND 99")
    suspend fun getAllValidItems(): List<CarritoItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carritoItem: CarritoItemEntity)

    @Query("UPDATE CarritoItem SET cantidad = :cantidad WHERE id = :id")
    suspend fun updateCantidad(id: Int, cantidad: Int)

    @Delete
    suspend fun delete(vararg carrito: CarritoItemEntity)
}