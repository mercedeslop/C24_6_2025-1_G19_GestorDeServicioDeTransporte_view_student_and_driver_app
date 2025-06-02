package com.jennyfer.sapallanay.view_student_app.ui.view.driver

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jennyfer.sapallanay.view_student_app.databinding.ActivitySignInDriverBinding

class SignInDriverActivity : AppCompatActivity() {

    private var binding: ActivitySignInDriverBinding? = null
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInDriverBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = FirebaseAuth.getInstance()

        binding?.btnSignInDriver?.setOnClickListener {
            val username = binding?.etSinInUsername?.text.toString().trim()
            val password = binding?.etSinInPassword?.text.toString().trim()

            if (validateForm(username, password)) {
                buscarEmailYUidYLoguear(username, password)
            }
        }
    }

    private fun buscarEmailYUidYLoguear(username: String, password: String) {
        firestore.collection("conductores")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val doc = result.documents[0]
                    val email = doc.getString("email") ?: ""
                    val uid = doc.id // el ID del documento es el UID
                    loginDriver(email, password, uid)
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al buscar usuario", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loginDriver(email: String, password: String, uid: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainDriverActivity::class.java)
                    intent.putExtra("driverUid", uid)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateForm(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding?.etSinInUsername?.error = "Ingrese nombre de usuario"
            return false
        }
        if (password.isEmpty()) {
            binding?.etSinInPassword?.error = "Ingrese contraseña"
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}