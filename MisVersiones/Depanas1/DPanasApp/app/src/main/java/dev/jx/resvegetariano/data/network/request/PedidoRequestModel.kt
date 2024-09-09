package dev.jx.resvegetariano.data.network.request

data class PedidoRequestModel(
    val cliente: String,
    val direccion: String?,
    val detalles: List<Detalle>
) {

    data class Detalle(
        val producto: String,
        val cantidad: Int
    )
}
