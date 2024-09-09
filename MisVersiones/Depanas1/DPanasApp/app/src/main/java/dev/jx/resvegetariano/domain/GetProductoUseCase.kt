package dev.jx.resvegetariano.domain

import dev.jx.resvegetariano.data.repository.ProductoRepository
import dev.jx.resvegetariano.domain.model.Producto
import javax.inject.Inject

class GetProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend operator fun invoke(id: String): Producto {
        return productoRepository.getByIdFromApi(id)
    }
}