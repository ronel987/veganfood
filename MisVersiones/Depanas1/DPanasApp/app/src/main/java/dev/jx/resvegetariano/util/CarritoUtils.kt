package dev.jx.resvegetariano.util

import dev.jx.resvegetariano.data.network.model.ClienteModel
import dev.jx.resvegetariano.data.network.model.DireccionModel
import dev.jx.resvegetariano.data.network.request.PedidoRequestModel
import dev.jx.resvegetariano.domain.model.CarritoItem
import java.math.BigDecimal

object CarritoUtils {

    /**
     * Calcula el total de un carrito
     * @param carrito Lista de items del carrito
     * @return Total del carrito redondeado hacia arriba a 1 decimal
     */
    fun getTotal(carrito: List<CarritoItem>): BigDecimal {
        return carrito.sumOf { item ->
            val precio = item.producto.precio.toBigDecimal()
            val cantidad = item.cantidad.toBigDecimal()
            precio * cantidad
        }.setScale(1, BigDecimal.ROUND_UP)
    }

    /**
     * Crea un pedido a partir de un carrito
     * @param carrito Lista de items del carrito
     * @param cliente Cliente que realiza el pedido
     * @param direccion Dirección de entrega
     * @return Un pedido con los datos del carrito
     */
    fun getPedido(
        carrito: List<CarritoItem>,
        cliente: ClienteModel,
        direccion: DireccionModel?
    ): PedidoRequestModel {
        val detalles =
            carrito.map { item -> PedidoRequestModel.Detalle(item.producto.id, item.cantidad) }
        val pedido = PedidoRequestModel(cliente.id, direccion?.id, detalles)

        return pedido
    }
}