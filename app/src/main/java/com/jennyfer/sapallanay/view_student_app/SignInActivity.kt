package com.jennyfer.sapallanay.view_student_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jennyfer.sapallanay.view_student_app.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() {
    private var binding:ActivitySignInBinding? = null
    private lateinit var auth: FirebaseAuth

   // private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

       /* val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)*/


        binding?.tvRegister?.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        binding?.btnSignIn?.setOnClickListener{
            signInUser()
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
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                        hideProgressBar()
                    }
                    else
                    {
                        showToast(this, "No se puede iniciar sesion. Intente en otro momento ")
                        hideProgressBar()
                    }

                }
        }
    }

 /*   private fun sinInWithGoogle()
    {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result->
        if (result.resultCode == Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful)
        {
            val account: GoogleSignInAccount? = task.result
            if (account!=null) {
                updateUI(account)
            }
        }
        else
        {
            Toast.makeText(this,"Sign In Failed, try again.",Toast.LENGTH_SHORT).show()
        }
    }*/

  /*  private fun updateUI(account: GoogleSignInAccount) {
        showProgressBar()
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful)
            {
                val id = FireStoreClass().getCurrentUserId()
                val name = account.displayName.toString()
                val email = account.email.toString()
                val userInfo = User(id, name, email)
                FireStoreClass().registerUser(userInfo)
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else
            {
                Toast.makeText(this,"Sign In Failed, try again.",Toast.LENGTH_SHORT).show()
            }
            hideProgressBar()
        }
    }*/



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

}