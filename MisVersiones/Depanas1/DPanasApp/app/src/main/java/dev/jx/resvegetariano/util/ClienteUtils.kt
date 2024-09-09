package dev.jx.resvegetariano.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.network.model.ClienteModel

object ClienteUtils {

    fun getClienteFromSharedPreferences(context: Context): ClienteModel? {
        val clientePreferences = context.getSharedPreferences(
            context.getString(R.string.cliente_secret_preferences_name),
            AppCompatActivity.MODE_PRIVATE
        )
        val clienteJson =
            clientePreferences.getString(context.getString(R.string.preferences_cliente_key), null)
        val cliente = Gson().fromJson(clienteJson, ClienteModel::class.java)

        return cliente
    }
}