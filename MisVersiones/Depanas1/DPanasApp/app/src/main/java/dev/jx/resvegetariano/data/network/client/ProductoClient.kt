package dev.jx.resvegetariano.data.network.client

import dev.jx.resvegetariano.data.network.model.ProductoModel
import dev.jx.resvegetariano.data.network.service.ProductoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductoClient @Inject constructor(private val productoService: ProductoService) {

    suspend fun getAll(): List<ProductoModel> {
        return withContext(Dispatchers.IO) {
            val response = productoService.getAll()
            val productos = response.body()?.data

            productos ?: emptyList()
        }
    }

    suspend fun getById(id: String): ProductoModel {
        return withContext(Dispatchers.IO) {
            val response = productoService.getById(id)
            val producto = response.body()?.data

            producto ?: throw Exception("Producto no encontrado")
        }
    }

    suspend fun getCarritoProductos(ids: Array<String>): List<ProductoModel> {
        return withContext(Dispatchers.IO) {
            val response = productoService.getCarritoProductos(*ids)
            val productos = response.body()?.data

            productos ?: emptyList()
        }
    }
}