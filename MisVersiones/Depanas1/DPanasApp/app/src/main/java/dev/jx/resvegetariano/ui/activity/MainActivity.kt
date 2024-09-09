package dev.jx.resvegetariano.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.network.service.PedidoService
import dev.jx.resvegetariano.databinding.ActivityMainBinding
import dev.jx.resvegetariano.ui.adapter.ScreenSlidePagerAdapter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pedidoService: PedidoService

    private lateinit var binding: ActivityMainBinding
    private lateinit var screenSlidePagerAdapter: FragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        screenSlidePagerAdapter = ScreenSlidePagerAdapter(this, pedidoService)
        binding.viewPager.adapter = screenSlidePagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val menuItem = binding.bottomNavigation.menu.getItem(position)
                if (!menuItem.isChecked) {
                    setCurrentPageWithMenuItemId(menuItem.itemId)
                }
            }
        })
        binding.bottomNavigation.setOnItemSelectedListener(this::handleItemSelected)
    }

    private fun handleItemSelected(item: MenuItem): Boolean {
        if (item.isChecked) {
            Log.d(TAG, "Item is already selected")
            return false
        }

        val pagePosition = when (item.itemId) {
            R.id.home_menu_item -> 0
            R.id.historial_menu_item -> 1
            R.id.perfil_menu_item -> 2
            else -> {
                Log.e(TAG, "Item no encontrado")
                null
            }
        } ?: return false

        binding.viewPager.currentItem = pagePosition
        return true
    }

    fun setCurrentPageWithMenuItemId(menuItemId: Int) {
        binding.bottomNavigation.selectedItemId = menuItemId
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}