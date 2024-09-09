package dev.jx.resvegetariano.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.resvegetariano.R
import dev.jx.resvegetariano.data.network.model.ClienteModel
import dev.jx.resvegetariano.data.network.response.Login
import dev.jx.resvegetariano.data.network.service.AuthService
import dev.jx.resvegetariano.databinding.ActivitySignInBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService
    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private var googleSignInLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInWithGoogleResult(task)
            } else {
                Log.e(TAG, "Sign in with google failed: ${result.resultCode}")
                Toast.makeText(
                    this,
                    "Se cerró la ventana de inicio de sesión de Google",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setupView()
    }

    private fun setupView() {
        binding.loginWithGoogleBtn.setOnClickListener { signInWithGoogle() }
    }

    private fun signInWithGoogle() {
        googleSignInLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun handleSignInWithGoogleResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            Log.d(TAG, "idToken: $idToken")

            CoroutineScope(Dispatchers.IO).launch {
                val res = authService.signInWithGoogle(idToken!!)
                runOnUiThread {
                    handleSignInWithGoogleApiResponse(res)
                }
            }
        } catch (e: ApiException) {
            Log.e(TAG, "Failed to retrieve Google account", e)
            Toast.makeText(
                this,
                "Ocurrió un error al obtener la cuenta de Google",
                Toast.LENGTH_SHORT
            ).show()
            googleSignInClient.signOut()
        }
    }

    private fun handleSignInWithGoogleApiResponse(res: Response<Login<ClienteModel>>) {
        if (res.isSuccessful) {
            val resBody = res.body()!!
            val clienteJson = Gson().toJson(resBody.data)
            getSharedPreferences(getString(R.string.cliente_secret_preferences_name), MODE_PRIVATE)
                .edit()
                .putString(getString(R.string.preferences_access_token_key), resBody.accessToken)
                .putString(getString(R.string.preferences_refresh_token_key), resBody.refreshToken)
                .putString(getString(R.string.preferences_cliente_key), clienteJson)
                .apply()

            Log.d(TAG, "Successfully signed in with Google")
            navigateToMainActivity()
        } else {
            Log.e(TAG, "Invalid Google token")
            Toast.makeText(
                this,
                "Ocurrió un error al iniciar sesión con Google",
                Toast.LENGTH_SHORT
            )
                .show()
            googleSignInClient.signOut()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}