package com.jennyfer.sapallanay.view_student_app.ui.view.driver
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jennyfer.sapallanay.view_student_app.databinding.ActivityMainDriverBinding
import com.jennyfer.sapallanay.view_student_app.R

class MainDriverActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainDriverBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set toolbar
        setSupportActionBar(binding.toolbarDriver)

        // Set drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbarDriver,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set NavigationView listener
        binding.navView.setNavigationItemSelectedListener(this)

        // Load default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.driver_fragment_container, DriverRoutesFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.nav_routes)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }
        })


        //Para iniciales del conductor
        val uid = intent.getStringExtra("driverUid") ?: FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            mostrarInfoConductor(uid)
        } else {
            Toast.makeText(this, "UID de conductor no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarInfoConductor(uid: String) {
        val headerView = binding.navView.getHeaderView(0)
        val avatarCircle = headerView.findViewById<TextView>(R.id.avatarCircle)
        val tvDriverName = headerView.findViewById<TextView>(R.id.tvDriverName)

        firestore.collection("conductores").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val nombreCompleto = document.getString("nombre") ?: ""
                    tvDriverName.text = nombreCompleto

                    val iniciales = nombreCompleto
                        .split(" ")
                        .mapNotNull { it.firstOrNull()?.toString() }
                        .take(2)
                        .joinToString("")
                        .uppercase()

                    avatarCircle.text = iniciales
                    Log.d("NAV_HEADER", "Nombre:$nombreCompleto, Iniciales:$iniciales")
                } else {
                    Log.e("NAV_HEADER", "Documento no existe para UID: $uid")
                }
            }
            .addOnFailureListener { e ->
                Log.e("NAV_HEADER", "Error al obtener datos: ${e.message}")
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_routes -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.driver_fragment_container, DriverRoutesFragment())
                    .commit()
            }
            R.id.nav_notifications -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.driver_fragment_container, DriverNotificationsFragment())
                    .commit()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, SignInDriverActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
