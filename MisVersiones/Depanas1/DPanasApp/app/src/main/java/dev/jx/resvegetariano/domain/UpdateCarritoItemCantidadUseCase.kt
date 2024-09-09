package dev.jx.resvegetariano.domain

import dev.jx.resvegetariano.data.repository.CarritoItemRepository
import dev.jx.resvegetariano.domain.model.CarritoItem
import javax.inject.Inject

class UpdateCarritoItemCantidadUseCase @Inject() constructor(private val carritoItemRepository: CarritoItemRepository) {
    suspend operator fun invoke(carritoItem: CarritoItem, cantidad: Int) {
        carritoItemRepository.updateCantidad(carritoItem, cantidad)
    }
}