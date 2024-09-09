package dev.jx.resvegetariano.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.network.model.PedidoModel
import dev.jx.resvegetariano.databinding.ItemPedidoBinding

class PedidoAdapter(private val context: Context, private var pedidos: List<PedidoModel>) :
    RecyclerView.Adapter<PedidoAdapter.ViewHolder>() {

    fun setPedidos(pedidos: List<PedidoModel>) {
        this.pedidos = pedidos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)
    }

    override fun getItemCount(): Int = pedidos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPedidoBinding.bind(itemView)

        fun bind(pedido: PedidoModel) {
            binding.pedidoCodigo.text =
                context.getString(R.string.pedido_realizado_codigo_template, pedido.id)
            binding.pedidoEstadoTv.text = pedido.estado
            binding.pedidoTotalTv.text = context.getString(
                R.string.item_producto_precio_template,
                String.format("%.2f", pedido.total)
            )
        }
    }

    class ItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val screenHorizontalSpacing =
            context.resources.getDimensionPixelSize(R.dimen.screen_horizontal_spacing)
        private val spaceBetweenItems =
            context.resources.getDimensionPixelSize(R.dimen.item_pedido_spacing)

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = screenHorizontalSpacing
            outRect.right = screenHorizontalSpacing
            outRect.bottom = spaceBetweenItems

            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = spaceBetweenItems
            }
        }
    }
}