package dev.jx.resvegetariano.ui.util

import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

class CantidadInputController(defaultCantidad: Int = 1) {

    private var cantidad: Int = defaultCantidad
        set(value) {
            field = value
            listener?.onCantidadChange(value, isCantidadValid)
        }
    private lateinit var cantidadEt: EditText
    private lateinit var aumentarBtn: Button
    private lateinit var disminuirBtn: Button
    private var listener: OnCantidadChangeListener? = null
    private var disableCantidadTextChangedListener = false
    private val isCantidadValid get() = cantidad in minCantidad..maxCantidad

    fun attach(
        cantidadEt: EditText,
        aumentarBtn: Button,
        disminuirBtn: Button
    ): CantidadInputController {
        this.cantidadEt = cantidadEt
        this.aumentarBtn = aumentarBtn
        this.disminuirBtn = disminuirBtn
        this.cantidadEt.setText(cantidad.toString())
        checkButtons()

        aumentarBtn.setOnClickListener { setCantidad(cantidad + 1) }
        disminuirBtn.setOnClickListener { setCantidad(cantidad - 1) }
        cantidadEt.addTextChangedListener {
            if (disableCantidadTextChangedListener) {
                disableCantidadTextChangedListener = false
                return@addTextChangedListener
            }

            val nuevaCantidad = it.toString().toIntOrNull() ?: 0
            setCantidad(nuevaCantidad, true)
        }

        return this
    }

    fun setOnCantidadChangeListener(listener: OnCantidadChangeListener) {
        this.listener = listener
    }

    private fun setCantidad(nuevaCantidad: Int, calledFromInput: Boolean = false) {
        if (nuevaCantidad !in minCantidad..maxCantidad && !calledFromInput) {
            cantidad = minCantidad
            disableCantidadTextChangedListener = true
            cantidadEt.setText(cantidad.toString())
            checkButtons()

            return
        }

        cantidad = nuevaCantidad
        checkButtons()

        if (!calledFromInput) {
            disableCantidadTextChangedListener = true
            cantidadEt.setText(cantidad.toString())
            cantidadEt.setSelection(cantidadEt.length())
        }
    }

    private fun checkButtons() {
        toggleAumentarCantidadButton()
        toggleDisminuirCantidadButton()
    }

    private fun toggleAumentarCantidadButton() {
        if (cantidad < maxCantidad && !aumentarBtn.isEnabled) {
            aumentarBtn.isEnabled = true
        }

        if (cantidad >= maxCantidad && aumentarBtn.isEnabled) {
            aumentarBtn.isEnabled = false
        }
    }

    private fun toggleDisminuirCantidadButton() {
        if (cantidad > minCantidad && !disminuirBtn.isEnabled) {
            disminuirBtn.isEnabled = true
        }

        if (cantidad <= minCantidad && aumentarBtn.isEnabled) {
            disminuirBtn.isEnabled = false
        }
    }

    fun interface OnCantidadChangeListener {
        fun onCantidadChange(cantidad: Int, isValid: Boolean)
    }

    companion object {
        private const val minCantidad = 1
        private const val maxCantidad = 99
    }
}