package dev.jx.resvegetariano.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.ui.activity.CarritoComprasActivity
import dev.jx.resvegetariano.ui.activity.MainActivity
import dev.jx.resvegetariano.ui.activity.ProductoDetallesActivity
import dev.jx.resvegetariano.domain.model.Producto
import dev.jx.resvegetariano.databinding.FragmentHomeBinding
import dev.jx.resvegetariano.ui.adapter.ProductoAdapter
import dev.jx.resvegetariano.viewmodel.ProductoListViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val productoListViewModel: ProductoListViewModel by viewModels()

    private lateinit var productoAdapter: ProductoAdapter

    private val carritoLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            (activity as MainActivity).setCurrentPageWithMenuItemId(R.id.historial_menu_item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    private fun setupView() {
        initProductosRecyclerView()
        binding.toolbar.setOnMenuItemClickListener(this::handleMenuItemClick)
    }

    private fun initProductosRecyclerView() {
        productoAdapter = ProductoAdapter(requireContext(), emptyList(), ProductoItemListener())
        binding.productosRv.adapter = productoAdapter
        binding.productosRv.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.productosRv.addItemDecoration(ProductoAdapter.ItemDecoration(requireContext()))
        productoListViewModel.productos.observe(viewLifecycleOwner) { productos ->
            productoAdapter.setProductos(productos)
            binding.productosRv.setHasFixedSize(true)
        }
        productoListViewModel.onCreate()
    }

    private fun handleMenuItemClick(item: MenuItem): Boolean = when (item.itemId) {
        R.id.carrito_menu_item -> {
            navigateToCarritoCompras()
            true
        }
        else -> false
    }

    private fun navigateToCarritoCompras() {
        val intent = Intent(context, CarritoComprasActivity::class.java)
        carritoLauncher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private inner class ProductoItemListener : ProductoAdapter.ItemListener {
        override fun onClick(producto: Producto) {
            val intent = Intent(context, ProductoDetallesActivity::class.java)
                .putExtra("producto_id", producto.id)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}