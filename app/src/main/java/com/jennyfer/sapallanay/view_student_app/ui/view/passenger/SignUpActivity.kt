package com.jennyfer.sapallanay.view_student_app.ui.view.passenger

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jennyfer.sapallanay.view_student_app.BaseActivity
import com.jennyfer.sapallanay.view_student_app.ui.view.passenger.MainActivity
import com.jennyfer.sapallanay.view_student_app.databinding.ActivitySignUpBinding
import com.jennyfer.sapallanay.view_student_app.ui.view.passenger.SignInActivity

class SignUpActivity : BaseActivity() {
    private var binding: ActivitySignUpBinding? = null
    private lateinit var auth: FirebaseAuth
    private val allowedDomains = listOf("@tecsup.edu.pe")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        binding?.tvLoginPage?.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
            finish()

        }
        binding?.btnSignUp?.setOnClickListener { registerUser() }
    }

    private fun registerUser()
    {
        val name = binding?.etSinUpName?.text.toString()
        val lastname = binding?.etSinUpApellidos?.text.toString()
        val email = binding?.etSinUpEmail?.text.toString()
        val password = binding?.etSinUpPassword?.text.toString()
        if (validateForm(name, lastname, email, password))
        {
            showProgressBar()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        showToast(this, "Se creo exitosamente su usuario. ¡Bienvenido!")
                        hideProgressBar()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    } else {
                        showToast(this, "No se puede registrar. Intentelo de nuevo")
                        hideProgressBar()
                    }
        }}
    }

    private fun validateForm(name: String, lastname: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                binding?.tilName?.error = "Ingrese su nombre"
                false
            }
            TextUtils.isEmpty(lastname)->{
                binding?.tilApellidos?.error = "Ingrese sus apellidos"
                false
            }
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error = "Ingrese un correo electronico valido"
                false
            }
            !isEmailInstitutional(email) -> {
                binding?.tilEmail?.error = "Ingrese un correo institucional valido"
                false
            }
            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error = "Ingrese contraseña"
                false
            }
            else -> { true }
        }


    }

    private fun isEmailInstitutional(email: String): Boolean {
        val domain = email.substringAfter("@")
        return allowedDomains.contains("@$domain")
    }


}