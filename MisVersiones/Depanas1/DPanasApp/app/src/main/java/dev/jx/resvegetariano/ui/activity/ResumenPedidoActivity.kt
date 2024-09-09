package dev.jx.resvegetariano.ui.activity

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.network.model.ClienteModel
import dev.jx.resvegetariano.data.network.model.PedidoModel
import dev.jx.resvegetariano.data.network.service.PedidoService
import dev.jx.resvegetariano.databinding.ActivityResumenPedidoBinding
import dev.jx.resvegetariano.domain.model.CarritoItem
import dev.jx.resvegetariano.util.CarritoUtils
import dev.jx.resvegetariano.util.ClienteUtils
import dev.jx.resvegetariano.viewmodel.CarritoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ResumenPedidoActivity : AppCompatActivity() {

    @Inject
    lateinit var pedidoService: PedidoService

    private lateinit var binding: ActivityResumenPedidoBinding

    private val carritoViewModel: CarritoViewModel by viewModels()

    private lateinit var cliente: ClienteModel

    private val pedidoRealizadoLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumenPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cliente = ClienteUtils.getClienteFromSharedPreferences(this)!!

        setupView()
    }

    private fun setupView() {
        carritoViewModel.carrito.observe(this) { carrito ->
            setPedidoDetalles(cliente, carrito)
        }
        carritoViewModel.onCreate()
        binding.realizarPedidoBtn.setOnClickListener { realizarPedido() }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setPedidoDetalles(cliente: ClienteModel, carrito: List<CarritoItem>) {
        val importeTotal = CarritoUtils.getTotal(carrito)
        Picasso.get().load(cliente.imagenUrl).into(binding.clienteImagen)
        binding.clienteNombreTv.text =
            getString(R.string.perfil_nombre_template, cliente.nombre, cliente.apellido)
        binding.clienteCorreoTv.text = cliente.email
        binding.productosTotalTv.text =
            getString(R.string.item_producto_precio_template, String.format("%.2f", importeTotal))
        binding.importeTotalTv.text =
            getString(R.string.item_producto_precio_template, String.format("%.2f", importeTotal))
    }

    private fun realizarPedido() {
        carritoViewModel.carrito.observe(this) { carrito ->
            val pedido = CarritoUtils.getPedido(carrito, cliente, null)
            CoroutineScope(Dispatchers.IO).launch {
                val response = pedidoService.createPedido(pedido);
                val resBody = response.body()!!
                carritoViewModel.delete(carrito.toTypedArray())
                runOnUiThread {
                    navigateToPedidoRealizado(resBody.data)
                }
            }
        }
    }

    private fun navigateToPedidoRealizado(pedido: PedidoModel) {
        val intent = Intent(this, PedidoRealizadoActivity::class.java)
            .putExtra("codigoPedido", pedido.id)
        pedidoRealizadoLauncher.launch(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}