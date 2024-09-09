package dev.jx.resvegetariano.domain

import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity
import dev.jx.resvegetariano.data.repository.CarritoItemRepository
import javax.inject.Inject

class AddCarritoItemUseCase @Inject constructor(private val carritoItemRepository: CarritoItemRepository) {
    suspend operator fun invoke(carritoItem: CarritoItemEntity) {
        carritoItemRepository.insertIntoDatabase(carritoItem)
    }
}