package dev.jx.resvegetariano.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.jx.resvegetariano.data.network.model.ClienteModel
import dev.jx.resvegetariano.data.network.service.PedidoService
import dev.jx.resvegetariano.databinding.FragmentHistorialPedidosBinding
import dev.jx.resvegetariano.ui.adapter.PedidoAdapter
import dev.jx.resvegetariano.util.ClienteUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistorialPedidosFragment(private val pedidoService: PedidoService) : Fragment() {

    private var _binding: FragmentHistorialPedidosBinding? = null
    private val binding get() = _binding!!

    private lateinit var pedidoAdapter: PedidoAdapter
    private lateinit var cliente: ClienteModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorialPedidosBinding.inflate(inflater, container, false)
        cliente = ClienteUtils.getClienteFromSharedPreferences(requireContext())!!

        setupView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val response = pedidoService.getClientePedidos(cliente.id)
            activity?.runOnUiThread {
                pedidoAdapter.setPedidos(response.body()?.data ?: emptyList())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initPedidosRecyclerView() {
        pedidoAdapter = PedidoAdapter(requireContext(), emptyList())
        binding.pedidosRv.adapter = pedidoAdapter
        binding.pedidosRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.pedidosRv.addItemDecoration(PedidoAdapter.ItemDecoration(requireContext()))
        CoroutineScope(Dispatchers.IO).launch {
            val response = pedidoService.getClientePedidos(cliente.id)
            activity?.runOnUiThread {
                pedidoAdapter.setPedidos(response.body()?.data ?: emptyList())
            }
        }
    }

    private fun setupView() {
        initPedidosRecyclerView()
    }

    companion object {
        const val TAG = "HistorialComprasFragment"
    }
}