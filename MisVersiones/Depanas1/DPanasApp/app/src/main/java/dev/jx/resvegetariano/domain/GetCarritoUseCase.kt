package dev.jx.resvegetariano.domain

import dev.jx.resvegetariano.data.repository.CarritoItemRepository
import javax.inject.Inject

class GetCarritoUseCase @Inject() constructor(private val carritoItemRepository: CarritoItemRepository) {
    suspend operator fun invoke() = carritoItemRepository.getCarrito()
}