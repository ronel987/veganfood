package dev.jx.resvegetariano.ui.activity

import androidx.appcompat.app.AppCompatActivity
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.databinding.ActivityPedidoRealizadoBinding

class PedidoRealizadoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoRealizadoBinding
    private var codigoPedido: String? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoRealizadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codigoPedido = intent.getStringExtra("codigoPedido")

        setupView()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun finish() {
        setResult(RESULT_OK)
        super.finish()
    }

    private fun setupView() {
        binding.pedidoRealizadoNumeroTv.text =
            getString(R.string.pedido_realizado_codigo_template, codigoPedido)
        binding.pedidoRealizadoContinuarBtn.setOnClickListener { finish() }
    }
}