package dev.jx.resvegetariano.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jx.resvegetariano.domain.GetProductoUseCase
import dev.jx.resvegetariano.domain.model.Producto
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject() constructor(private val getProductoUseCase: GetProductoUseCase) :
    ViewModel() {

    private val _producto = MutableLiveData<Producto>()
    private val _isLoading = MutableLiveData<Boolean>()

    val producto get(): LiveData<Producto> = _producto
    val isLoading get(): LiveData<Boolean> = _isLoading

    fun onCreate(id: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val data = getProductoUseCase(id)
            _producto.postValue(data)
            _isLoading.postValue(false)
        }
    }
}