package com.jennyfer.sapallanay.view_student_app.ui.view.passenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jennyfer.sapallanay.view_student_app.databinding.FragmentRutasBinding

class RutasFragment : Fragment() {
    private var _binding: FragmentRutasBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRutasBinding.inflate(inflater, container, false)
        // Obtener la instancia de FirebaseAuth
        auth = Firebase.auth
        // Enlaza el click listener al boton
        binding.btnLogout.setOnClickListener {
            cerrarSesion()
        }
        return binding.root
    }

    private fun cerrarSesion() {
        // Cerrar sesión con Firebase
        auth.signOut()
        Log.d("RutasFragment", "Cerrando sesión...")
        // Redirigir a la pantalla de Login
        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Cierra la actividad actual
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}