package dev.jx.resvegetariano.domain

import dev.jx.resvegetariano.data.repository.ProductoRepository
import dev.jx.resvegetariano.domain.model.Producto
import javax.inject.Inject

class GetProductoListUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend operator fun invoke(): List<Producto> {
        return productoRepository.getAllFromApi()
    }
}