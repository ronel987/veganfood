package dev.jx.resvegetariano.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.databinding.ActivityCarritoComprasBinding
import dev.jx.resvegetariano.domain.model.CarritoItem
import dev.jx.resvegetariano.ui.adapter.CarritoAdapter
import dev.jx.resvegetariano.util.CarritoUtils
import dev.jx.resvegetariano.viewmodel.CarritoItemViewModel
import dev.jx.resvegetariano.viewmodel.CarritoViewModel

@AndroidEntryPoint
class CarritoComprasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarritoComprasBinding

    private val carritoViewModel: CarritoViewModel by viewModels()
    private val carritoItemViewModel: CarritoItemViewModel by viewModels()

    private lateinit var carritoAdapter: CarritoAdapter
    private var actionMode: ActionMode? = null

    private val resumenPedidoLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoComprasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupView() {
        initProductosRecyclerView()
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.procederPagoPedidoBtn.setOnClickListener { navigateToResumenPedido() }
        carritoViewModel.isDeleting.observe(this) { isDeleting ->
            if (isDeleting) return@observe

            carritoAdapter.deleteSelectedItems()
            setImporteTotalFromCarrito(carritoAdapter.carrito)
            finishActionMode()
        }
    }

    private fun initProductosRecyclerView() {
        carritoAdapter = CarritoAdapter(this, mutableListOf(), CarritoItemListener())
        binding.carritoRv.adapter = carritoAdapter
        binding.carritoRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.carritoRv.addItemDecoration(CarritoAdapter.ItemDecoration(this))
        carritoViewModel.carrito.observe(this) { carrito ->
            carritoAdapter.setCarrito(carrito.toMutableList())
            setImporteTotalFromCarrito(carrito)
        }
        carritoViewModel.onCreate()
    }

    private fun navigateToResumenPedido() {
        val intent = Intent(this, ResumenPedidoActivity::class.java)
        resumenPedidoLauncher.launch(intent)
    }

    private fun deleteSelectedCarritoItems() {
        carritoViewModel.delete(carritoAdapter.selectedItems.toTypedArray())
    }

    private fun setImporteTotalFromCarrito(carrito: List<CarritoItem>) {
        val importeTotal = CarritoUtils.getTotal(carrito)
        binding.procederPagoPedidoBtn.isEnabled = importeTotal.toFloat() > 0
        binding.importeTotalTv.text =
            getString(R.string.item_producto_precio_template, String.format("%.2f", importeTotal))
    }

    private inner class ActionModeCallback : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.carrito_contextual_action_bar_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean =
            when (item?.itemId) {
                R.id.delete_menu_item -> {
                    MaterialAlertDialogBuilder(this@CarritoComprasActivity)
                        .setTitle(R.string.delete_items_dialog_title)
                        .setMessage(R.string.delete_items_dialog_message)
                        .setPositiveButton("Sí, eliminar") { dialog, which ->
                            deleteSelectedCarritoItems()
                        }.setNegativeButton("Cancelar") { dialog, _ ->
                            dialog.cancel()
                        }.show()
                    true
                }
                R.id.select_all_menu_item -> {
                    carritoAdapter.selectAllItems()
                    true
                }
                else -> false
            }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (carritoAdapter.selectedItems.isNotEmpty()) {
                carritoAdapter.unselectAllItems()
            }
        }
    }

    private fun finishActionMode() {
        actionMode?.finish()
        actionMode = null
    }

    private inner class CarritoItemListener : CarritoAdapter.ItemListener {
        override fun onCheckedChange(
            carritoItem: CarritoItem,
            isChecked: Boolean,
            inSelectionMode: Boolean
        ) {
            if (actionMode != null && !inSelectionMode) {
                finishActionMode()
                return
            }

            if (actionMode == null && inSelectionMode) {
                actionMode = startSupportActionMode(ActionModeCallback())
            }

            actionMode?.title = getString(
                R.string.selected_items_template,
                carritoAdapter.selectedItems.size.toString()
            )
        }

        override fun onCantidadChange(
            carritoItem: CarritoItem,
            cantidad: Int,
            isValid: Boolean
        ) {
            carritoItemViewModel.updateCantidad(carritoItem, cantidad)
            setImporteTotalFromCarrito(carritoAdapter.carrito)
        }
    }
}