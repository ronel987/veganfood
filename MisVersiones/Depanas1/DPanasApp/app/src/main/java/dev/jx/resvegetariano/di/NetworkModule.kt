package dev.jx.resvegetariano.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.network.service.AuthService
import dev.jx.resvegetariano.data.network.service.PedidoService
import dev.jx.resvegetariano.data.network.service.ProductoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.API_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductoService(retrofit: Retrofit): ProductoService {
        return retrofit.create(ProductoService::class.java)
    }

    @Provides
    @Singleton
    fun providePedidoService(retrofit: Retrofit): PedidoService {
        return retrofit.create(PedidoService::class.java)
    }
}