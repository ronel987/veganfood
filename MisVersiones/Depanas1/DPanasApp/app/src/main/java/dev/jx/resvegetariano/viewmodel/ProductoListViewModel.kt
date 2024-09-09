package dev.jx.resvegetariano.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jx.resvegetariano.domain.GetProductoListUseCase
import dev.jx.resvegetariano.domain.model.Producto
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoListViewModel @Inject constructor(private val getProductoListUseCase: GetProductoListUseCase) :
    ViewModel() {

    private val _productos = MutableLiveData<List<Producto>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val productos: LiveData<List<Producto>> get() = _productos
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun onCreate() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val data = getProductoListUseCase()
            _productos.postValue(data)
            _isLoading.postValue(false)
        }
    }
}