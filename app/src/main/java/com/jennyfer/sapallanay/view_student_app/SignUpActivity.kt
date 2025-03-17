package com.jennyfer.sapallanay.view_student_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jennyfer.sapallanay.view_student_app.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    private var binding:ActivitySignUpBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        binding?.tvLoginPage?.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
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
                        showToast(this, "Se creo exitosamente su usuario")
                        hideProgressBar()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    } else {
                        showToast(this, "No se creo su usuario. Intentelo de nuevo")
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
            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error = "Ingrese contraseña"
                false
            }
            else -> { true }
        }
    }


}