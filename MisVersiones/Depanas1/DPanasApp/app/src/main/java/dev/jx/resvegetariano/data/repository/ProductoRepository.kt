package dev.jx.resvegetariano.data.repository

import dev.jx.resvegetariano.data.network.client.ProductoClient
import dev.jx.resvegetariano.domain.model.Producto
import dev.jx.resvegetariano.domain.model.toDomain
import javax.inject.Inject

class ProductoRepository @Inject constructor(private val productoClient: ProductoClient) {

    suspend fun getByIdFromApi(id: String): Producto {
        return productoClient.getById(id).toDomain()
    }

    suspend fun getAllFromApi(): List<Producto> {
        val productos = productoClient.getAll()
        return productos.map { it.toDomain() }
    }

    suspend fun getCarritoProductos(ids: Array<String>): List<Producto> {
        val productos = productoClient.getCarritoProductos(ids)
        return productos.map { it.toDomain() }
    }
}