package dev.jx.resvegetariano.data.network.service

import dev.jx.resvegetariano.data.network.model.ProductoModel
import dev.jx.resvegetariano.data.network.response.ListResource
import dev.jx.resvegetariano.data.network.response.SingleResource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoService {

    @GET("productos")
    suspend fun getAll(): Response<ListResource<ProductoModel>>

    @GET("productos/{id}")
    suspend fun getById(@Path("id") id: String): Response<SingleResource<ProductoModel>>

    @GET("productos/carrito")
    suspend fun getCarritoProductos(@Query("id") vararg ids: String): Response<ListResource<ProductoModel>>
}