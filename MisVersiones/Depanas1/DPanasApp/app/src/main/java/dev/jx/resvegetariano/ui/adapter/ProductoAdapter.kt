package dev.jx.resvegetariano.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.domain.model.Producto
import dev.jx.resvegetariano.databinding.ItemProductoBinding
import kotlin.math.ceil

class ProductoAdapter(
    private val context: Context,
    private var productos: List<Producto>,
    private val listener: ItemListener,
) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    fun setProductos(productos: List<Producto>) {
        this.productos = productos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductoBinding.bind(itemView)

        fun bind(producto: Producto) {
            binding.root.setOnClickListener {
                listener.onClick(producto)
            }

            Picasso.get().load(producto.imagenUrl).into(binding.productoImagen)
            binding.productoNombreTv.text = producto.nombre
            binding.productoCategoriaTv.text = producto.categoria?.nombre
            binding.productoPrecioTv.text = context.getString(
                R.string.item_producto_precio_template,
                String.format("%.2f", producto.precio)
            )
        }
    }

    class ItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val screenHorizontalSpacing =
            context.resources.getDimensionPixelSize(R.dimen.screen_horizontal_spacing)
        private val gridSpacing =
            context.resources.getDimensionPixelSize(R.dimen.item_producto_grid_spacing)

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            var position = parent.getChildAdapterPosition(view)
            if (position == RecyclerView.NO_POSITION) return

            position += 1

            if (position % 2 != 0) {
                outRect.left = screenHorizontalSpacing
                outRect.right = gridSpacing / 2
            } else {
                outRect.right = screenHorizontalSpacing
                outRect.left = gridSpacing / 2
            }

            val rows = ceil(parent.adapter!!.itemCount.toFloat() / 2).toInt()
            val currentRow = ceil(position.toFloat() / 2).toInt()

            if (currentRow < rows) {
                outRect.bottom = gridSpacing
            }
        }
    }

    interface ItemListener {
        fun onClick(producto: Producto)
    }
}