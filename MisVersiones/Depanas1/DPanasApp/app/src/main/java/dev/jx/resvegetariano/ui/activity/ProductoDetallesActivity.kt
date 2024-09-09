package dev.jx.resvegetariano.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.databinding.ActivityProductoDetallesBinding
import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity
import dev.jx.resvegetariano.domain.model.Producto
import dev.jx.resvegetariano.ui.util.CantidadInputController
import dev.jx.resvegetariano.viewmodel.CarritoItemViewModel
import dev.jx.resvegetariano.viewmodel.ProductoViewModel

@AndroidEntryPoint
class ProductoDetallesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoDetallesBinding

    private val productoViewModel: ProductoViewModel by viewModels()
    private val carritoItemViewModel: CarritoItemViewModel by viewModels()
    private var producto: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupView() {
        val productoId = intent.extras?.getString("producto_id")
        if (productoId != null) {
            productoViewModel.producto.observe(this, ::setProducto)
            productoViewModel.onCreate(productoId)
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setProducto(producto: Producto) {
        this.producto = producto
        Picasso.get().load(producto.imagenUrl).into(binding.productoImagen)
        binding.toolbar.title = producto.nombre
        binding.productoPrecioTv.text =
            getString(
                R.string.item_producto_precio_template,
                String.format("%.2f", producto.precio)
            )
        binding.productoCategoriaTv.text = producto.categoria?.nombre
        binding.productoDescripcionTv.text =
            producto.descripcion ?: getString(R.string.item_producto_sin_descripcion)
        CantidadInputController().attach(
            binding.productoCantidadEt,
            binding.aumentarCantidadBtn,
            binding.disminuirCantidadBtn
        ).setOnCantidadChangeListener { _, isValid ->
            binding.agregarAlCarritoBtn.isEnabled = isValid
        }
        binding.agregarAlCarritoBtn.setOnClickListener { agregarProductoAlCarrito() }
    }

    private fun agregarProductoAlCarrito() {
        val cantidad = binding.productoCantidadEt.text.toString().toInt()
        val carritoItem = CarritoItemEntity(productoId = producto!!.id, cantidad = cantidad)
        carritoItemViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                return@observe
            }

            Toast.makeText(
                this@ProductoDetallesActivity,
                "Se agrego el producto al carrito",
                Toast.LENGTH_SHORT
            ).show()
        }
        carritoItemViewModel.add(carritoItem)
    }
}