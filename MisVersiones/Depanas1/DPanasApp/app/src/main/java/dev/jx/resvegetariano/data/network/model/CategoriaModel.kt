package dev.jx.resvegetariano.data.network.model

import com.google.gson.annotations.SerializedName

data class CategoriaModel(
    @SerializedName("_id")
    val id: String,
    val productos: List<ProductoModel>?,
    val nombre: String,
    val fechaRegistro: String
)
