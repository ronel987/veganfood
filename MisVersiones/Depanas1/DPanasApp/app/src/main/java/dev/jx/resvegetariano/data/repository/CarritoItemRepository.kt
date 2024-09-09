package dev.jx.resvegetariano.data.repository

import dev.jx.resvegetariano.data.database.dao.CarritoItemDao
import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity
import dev.jx.resvegetariano.domain.model.CarritoItem
import dev.jx.resvegetariano.domain.model.toDomain
import javax.inject.Inject

class CarritoItemRepository @Inject() constructor(
    private val carritoItemDao: CarritoItemDao,
    private val productoRepository: ProductoRepository
) {

    suspend fun getCarrito(): List<CarritoItem> {
        val carritoMap = carritoItemDao.getAll().associateBy { it.productoId }
        val productoIds = carritoMap.keys.toTypedArray()
        val productos = productoRepository.getCarritoProductos(productoIds)
        val carrito = productos.map { producto ->
            val item = carritoMap[producto.id]!!
            item.toDomain(producto)
        }

        return carrito
    }

    suspend fun insertIntoDatabase(carritoItem: CarritoItemEntity) {
        carritoItemDao.insert(carritoItem)
    }

    suspend fun updateCantidad(carritoItem: CarritoItem, cantidad: Int) {
        carritoItemDao.updateCantidad(carritoItem.id, cantidad)
    }

    suspend fun deleteFromDatabase(vararg carrito: CarritoItem) {
        carritoItemDao.delete(*carrito.map { it.toEntity() }.toTypedArray())
    }
}