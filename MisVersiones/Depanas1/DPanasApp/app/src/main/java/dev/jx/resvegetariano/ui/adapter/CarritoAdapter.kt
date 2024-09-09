package dev.jx.resvegetariano.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.databinding.ItemCarritoBinding
import dev.jx.resvegetariano.domain.model.CarritoItem
import dev.jx.resvegetariano.ui.util.CantidadInputController

class CarritoAdapter(
    private val context: Context,
    private var _carrito: MutableList<CarritoItem>,
    private val listener: ItemListener
) :
    RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    private val inSelectionMode get() = _selectedItems.isNotEmpty()
    private val _selectedItems: MutableMap<Int, CarritoItem> = mutableMapOf()
    val carrito get() = _carrito.toList()
    val selectedItems get() = _selectedItems.values.toList()

    fun setCarrito(carrito: MutableList<CarritoItem>) {
        this._carrito = carrito
        notifyDataSetChanged()
    }

    fun selectAllItems() {
        _carrito.mapIndexedNotNull { index, carritoItem ->
            if (_selectedItems.containsKey(carritoItem.id)) return@mapIndexedNotNull null

            return@mapIndexedNotNull index to carritoItem
        }.forEach { (index, carritoItem) ->
            _selectedItems[carritoItem.id] = carritoItem
            notifyItemChanged(index, true)
        }
    }

    fun unselectAllItems() {
        _carrito.mapIndexedNotNull { index, carritoItem ->
            if (!_selectedItems.containsKey(carritoItem.id)) return@mapIndexedNotNull null

            return@mapIndexedNotNull index to carritoItem
        }.forEach { (index, carritoItem) ->
            _selectedItems.remove(carritoItem.id)
            notifyItemChanged(index, false)
        }
    }

    fun deleteSelectedItems() {
        _carrito.filter { _selectedItems.containsKey(it.id) }
            .forEach { carritoItem ->
                val index = _carrito.indexOf(carritoItem)
                if (index > -1) {
                    _selectedItems.remove(carritoItem.id)
                    _carrito.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carritoItem = _carrito[position]
        holder.bind(carritoItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        val isChecked = payloads.first() as? Boolean
        if (isChecked != null) {
            holder.setChecked(isChecked)
        }
    }

    override fun getItemCount(): Int = _carrito.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCarritoBinding.bind(itemView)

        fun bind(carritoItem: CarritoItem) {
            val producto = carritoItem.producto
            val cantidad = carritoItem.cantidad

            Picasso.get().load(producto.imagenUrl).into(binding.productoImagen)
            binding.productoNombreTv.text = producto.nombre
            binding.productoCategoriaTv.text = producto.categoria?.nombre
            binding.productoPrecioTv.text =
                context.getString(
                    R.string.item_producto_precio_template,
                    String.format("%.2f", producto.precio)
                )
            CantidadInputController(cantidad).attach(
                binding.productoCantidadEt,
                binding.aumentarCantidadBtn,
                binding.disminuirCantidadBtn
            ).setOnCantidadChangeListener { cantidad, isValid ->
                carritoItem.cantidad = cantidad
                listener.onCantidadChange(carritoItem, cantidad, isValid)
            }

            binding.root.apply {
                setOnClickListener {
                    if (inSelectionMode) {
                        val itemCard = (it as MaterialCardView)
                        itemCard.isChecked = !itemCard.isChecked
                    }
                }
                setOnLongClickListener {
                    val itemCard = (it as MaterialCardView)
                    itemCard.isChecked = !itemCard.isChecked

                    true
                }
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        _selectedItems[carritoItem.id] = carritoItem
                    } else {
                        _selectedItems.remove(carritoItem.id)
                    }

                    listener.onCheckedChange(carritoItem, isChecked, inSelectionMode)
                }
            }
        }

        fun setChecked(isChecked: Boolean) {
            binding.root.isChecked = isChecked
        }
    }

    class ItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val screenHorizontalSpacing =
            context.resources.getDimensionPixelSize(R.dimen.screen_horizontal_spacing)
        private val spaceBetweenItems =
            context.resources.getDimensionPixelSize(R.dimen.item_carrito_spacing)

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.set(
                screenHorizontalSpacing,
                spaceBetweenItems,
                screenHorizontalSpacing,
                spaceBetweenItems
            )
        }
    }

    interface ItemListener {
        fun onCheckedChange(
            carritoItem: CarritoItem,
            isChecked: Boolean,
            inSelectionMode: Boolean
        )

        fun onCantidadChange(carritoItem: CarritoItem, cantidad: Int, isValid: Boolean)
    }
}