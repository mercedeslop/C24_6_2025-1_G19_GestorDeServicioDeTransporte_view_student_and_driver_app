package com.jennyfer.sapallanay.view_student_app.ui.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jennyfer.sapallanay.view_student_app.BaseActivity
import com.jennyfer.sapallanay.view_student_app.GoogleAuthClient
import com.jennyfer.sapallanay.view_student_app.MainActivity
import com.jennyfer.sapallanay.view_student_app.databinding.ActivitySignInBinding
import kotlinx.coroutines.launch

class SignInActivity : BaseActivity() {
    private var binding: ActivitySignInBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var googleAuthClient: GoogleAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth
        googleAuthClient = GoogleAuthClient(this)

        if (auth.currentUser != null) {
            navigateToMain()
        }
        binding?.tvRegister?.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding?.btnSignIn?.setOnClickListener{
            signInUser()
        }

        binding?.btnSignInWithGoogle?.setOnClickListener {
            signInWithGoogle()
        }

        if (auth.currentUser!= null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }


    private fun signInUser() {
        val email = binding?.etSinInEmail?.text.toString()
        val password = binding?.etSinInPassword?.text.toString()
        if (validateForm(email, password))
        {
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task->
                    if (task.isSuccessful)
                    {
                        Toast.makeText(this, "¡Bienvenido de nuevo!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        hideProgressBar()
                    }
                    else
                    {
                        showToast(this, "Correo institucional o contraseña incorrectos")
                        hideProgressBar()
                    }

                }
        }
    }

    private fun signInWithGoogle() {
        lifecycleScope.launch {
            val success = googleAuthClient.signIn()
            if (success) {
                Toast.makeText(this@SignInActivity, "¡Sesión iniciada con Google!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                Toast.makeText(this@SignInActivity, "Inicio cancelado o fallido. Intenta nuevamente.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error = "Ingrese un correo electronico valido"
                false
            }
            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error = "Ingrese contraseña"
                false
            }
            else -> { true }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}