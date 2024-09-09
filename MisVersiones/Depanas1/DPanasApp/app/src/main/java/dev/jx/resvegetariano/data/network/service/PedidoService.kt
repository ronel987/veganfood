package dev.jx.resvegetariano.data.network.service

import dev.jx.resvegetariano.data.network.request.PedidoRequestModel
import dev.jx.resvegetariano.data.network.model.PedidoModel
import dev.jx.resvegetariano.data.network.response.ListResource
import dev.jx.resvegetariano.data.network.response.SingleResource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PedidoService {

    @GET("pedidos")
    suspend fun getClientePedidos(@Query("cliente") clienteId: String): Response<ListResource<PedidoModel>>

    @POST("pedidos")
    suspend fun createPedido(@Body pedido: PedidoRequestModel): Response<SingleResource<PedidoModel>>
}