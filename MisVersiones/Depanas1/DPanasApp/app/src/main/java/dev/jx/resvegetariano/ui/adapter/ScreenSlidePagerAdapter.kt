package dev.jx.resvegetariano.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.jx.resvegetariano.data.network.service.PedidoService
import dev.jx.resvegetariano.ui.fragment.HistorialPedidosFragment
import dev.jx.resvegetariano.ui.fragment.HomeFragment
import dev.jx.resvegetariano.ui.fragment.PerfilFragment

class ScreenSlidePagerAdapter(
    fa: FragmentActivity,
    pedidoService: PedidoService
) : FragmentStateAdapter(fa) {

    private val pages = arrayOf(
        HomeFragment(),
        HistorialPedidosFragment(pedidoService),
        PerfilFragment()
    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}