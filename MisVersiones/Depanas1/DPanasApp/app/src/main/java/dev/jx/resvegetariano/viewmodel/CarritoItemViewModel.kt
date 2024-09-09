package dev.jx.resvegetariano.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jx.resvegetariano.data.database.entity.CarritoItemEntity
import dev.jx.resvegetariano.domain.AddCarritoItemUseCase
import dev.jx.resvegetariano.domain.UpdateCarritoItemCantidadUseCase
import dev.jx.resvegetariano.domain.model.CarritoItem
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarritoItemViewModel @Inject() constructor(
    private val addCarritoItemUseCase: AddCarritoItemUseCase,
    private val updateCarritoItemCantidadUseCase: UpdateCarritoItemCantidadUseCase
) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun add(carritoItem: CarritoItemEntity) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            addCarritoItemUseCase(carritoItem)
            _isLoading.postValue(false)
        }
    }

    fun updateCantidad(carritoItem: CarritoItem, cantidad: Int) {
        viewModelScope.launch {
            updateCarritoItemCantidadUseCase(carritoItem, cantidad)
        }
    }
}