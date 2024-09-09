package dev.jx.resvegetariano.data.network.model

import com.google.gson.annotations.SerializedName

data class ProductoModel(
    @SerializedName("_id")
    val id: String,
    val categoria: CategoriaModel?,
    val nombre: String,
    val descripcion: String?,
    val precio: Float,
    val stock: Int,
    val imagenUrl: String,
    val fechaRegistro: String,
    val estado: String
)
