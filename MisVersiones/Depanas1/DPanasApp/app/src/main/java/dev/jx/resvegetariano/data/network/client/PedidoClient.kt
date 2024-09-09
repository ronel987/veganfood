package dev.jx.resvegetariano.data.network.client

import dev.jx.resvegetariano.data.network.model.PedidoModel
import dev.jx.resvegetariano.data.network.request.PedidoRequestModel
import dev.jx.resvegetariano.data.network.service.PedidoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PedidoClient @Inject constructor(private val pedidoService: PedidoService) {

    suspend fun getClientePedidos(clienteId: String): List<PedidoModel> {
        return withContext(Dispatchers.IO) {
            val response = pedidoService.getClientePedidos(clienteId)
            val pedidos = response.body()?.data

            pedidos ?: emptyList()
        }
    }

    suspend fun createPedido(pedido: PedidoRequestModel): PedidoModel {
        return withContext(Dispatchers.IO) {
            val response = pedidoService.createPedido(pedido)
            if (response.isSuccessful) {
                response.body()!!.data
            } else {
                throw Exception("Error al crear el pedido")
            }
        }
    }
}