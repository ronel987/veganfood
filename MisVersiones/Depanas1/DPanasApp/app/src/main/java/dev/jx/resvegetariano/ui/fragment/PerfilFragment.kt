package dev.jx.resvegetariano.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.ui.activity.SignInActivity
import dev.jx.resvegetariano.databinding.FragmentPerfilBinding
import dev.jx.resvegetariano.util.ClienteUtils

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    private fun setupView() {
        val cliente = ClienteUtils.getClienteFromSharedPreferences(requireContext())!!
        Picasso.get().load(cliente.imagenUrl).into(binding.perfilImagen)
        binding.perfilNombre.text =
            getString(R.string.perfil_nombre_template, cliente.nombre, cliente.apellido)
        binding.perfilCorreo.text = cliente.email

        binding.logoutBtn.setOnClickListener { logout() }
        binding.logoutBtn.setOnLongClickListener { true }
    }

    private fun logout() {
        activity?.apply {
            val clientePreferences = getSharedPreferences(
                getString(R.string.cliente_secret_preferences_name),
                AppCompatActivity.MODE_PRIVATE
            )
            clientePreferences.edit().clear().apply()
            GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
            navigateToSignInActivity()
        }
    }

    private fun navigateToSignInActivity() {
        activity?.apply {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "PerfilFragment"
    }
}