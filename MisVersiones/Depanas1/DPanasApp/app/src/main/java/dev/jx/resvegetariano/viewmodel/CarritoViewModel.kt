package dev.jx.resvegetariano.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jx.resvegetariano.domain.DeleteCarritoUseCase
import dev.jx.resvegetariano.domain.GetCarritoUseCase
import dev.jx.resvegetariano.domain.model.CarritoItem
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarritoViewModel @Inject() constructor(
    private val getCarritoUseCase: GetCarritoUseCase,
    private val deleteCarritoUseCase: DeleteCarritoUseCase
) : ViewModel() {

    private val _carrito = MutableLiveData<List<CarritoItem>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isDeleting = MutableLiveData<Boolean>()

    val carrito: LiveData<List<CarritoItem>> = _carrito
    val isLoading: LiveData<Boolean> = _isLoading
    val isDeleting: LiveData<Boolean> = _isDeleting

    fun onCreate() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val data = getCarritoUseCase()
            _carrito.postValue(data)
            _isLoading.postValue(false)
        }
    }

    fun delete(carrito: Array<CarritoItem>) {
        viewModelScope.launch {
            _isDeleting.value = true
            deleteCarritoUseCase(carrito)
            _isDeleting.value = false
        }
    }
}