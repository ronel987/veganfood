package dev.jx.resvegetariano.domain.model

import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity

data class CarritoItem(
    val id: Int,
    val producto: Producto,
    var cantidad: Int,
) {

    fun toEntity() = CarritoItemEntity(id, producto.id, cantidad)
}

fun CarritoItemEntity.toDomain(producto: Producto) = CarritoItem(id, producto, cantidad)
