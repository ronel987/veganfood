package dev.jx.resvegetariano.domain.model

import dev.jx.resvegetariano.data.network.model.ProductoModel

data class Producto(
    val id: String,
    val categoria: Categoria?,
    val nombre: String,
    val descripcion: String?,
    val precio: Float,
    val stock: Int,
    val imagenUrl: String,
    val fechaRegistro: String,
    val estado: String
)

fun ProductoModel.toDomain() = Producto(
    id,
    categoria?.toNonPopulatedDomain(),
    nombre,
    descripcion,
    precio,
    stock,
    imagenUrl,
    fechaRegistro,
    estado
)

fun ProductoModel.toNonPopulatedDomain() = Producto(
    id,
    null,
    nombre,
    descripcion,
    precio,
    stock,
    imagenUrl,
    fechaRegistro,
    estado
)