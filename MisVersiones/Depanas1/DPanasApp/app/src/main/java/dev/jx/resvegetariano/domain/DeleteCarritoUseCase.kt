package dev.jx.resvegetariano.domain

import dev.jx.resvegetariano.data.repository.CarritoItemRepository
import dev.jx.resvegetariano.domain.model.CarritoItem
import javax.inject.Inject

class DeleteCarritoUseCase @Inject() constructor(
    private val carritoItemRepository: CarritoItemRepository
) {
    suspend operator fun invoke(carrito: Array<CarritoItem>) =
        carritoItemRepository.deleteFromDatabase(*carrito)
}