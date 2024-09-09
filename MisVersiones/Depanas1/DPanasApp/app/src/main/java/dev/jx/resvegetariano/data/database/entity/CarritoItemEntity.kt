package dev.jx.resvegetariano.data.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "CarritoItem", indices = [Index(value = ["productoId"], unique = true)])
data class CarritoItemEntity(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productoId: String,
    var cantidad: Int,
)