package com.jennyfer.sapallanay.view_student_app.ui.view.passenger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        //Mostrar fragmentos
        replaceFragment(InicioFragment())

        binding?.bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(InicioFragment())
                    true
                }
                R.id.bottom_rutas -> {
                    replaceFragment(RutasFragment())
                    true
                }
                else -> false
            }
        }

        // Cerrar sesion
     /*   binding?.btnSignOut?.setOnClickListener{
            if (auth.currentUser!=null) {
                auth.signOut()
                startActivity(Intent(this,SignInActivity::class.java))
                finish()
            }

        }*/

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.frame_container)
            val hideBottomNav = fragment is QRFragment || fragment is DetalleRutaFragment

            findViewById<View>(R.id.bottom_navigation).visibility =
                if (hideBottomNav) View.GONE else View.VISIBLE
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }
}