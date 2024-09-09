package dev.jx.resvegetariano.domain.model

import dev.jx.resvegetariano.data.network.model.CategoriaModel

data class Categoria(
    val id: String,
    val productos: List<Producto>?,
    val nombre: String,
    val fechaRegistro: String
)

fun CategoriaModel.toDomain() =
    Categoria(id, productos?.map { it.toNonPopulatedDomain() }, nombre, fechaRegistro)

fun CategoriaModel.toNonPopulatedDomain() =
    Categoria(id, null, nombre, fechaRegistro)
