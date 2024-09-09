package dev.jx.resvegetariano.data.network.model

import com.google.gson.annotations.SerializedName

data class ClienteModel(
    @SerializedName("_id")
    val id: String,
    val direcciones: List<DireccionModel>?,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String?,
    val imagenUrl: String?,
    val fechaRegistro: String,
    val estado: String
)
