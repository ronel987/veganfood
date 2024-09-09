package dev.jx.resvegetariano.data.network.model

import com.google.gson.annotations.SerializedName

data class PedidoModel(
    @SerializedName("_id")
    val id: String,
    val cliente: ClienteModel,
    val direccion: DireccionModel?,
    val detalles: List<DetalleModel>,
    val total: Float,
    val fechaRegistro: String,
    val estado: String
) {

    data class DetalleModel(
        @SerializedName("_id")
        val id: String,
        val producto: ProductoModel,
        val precioUnitario: Float,
        val cantidad: Int,
        val subtotal: Float
    )
}
