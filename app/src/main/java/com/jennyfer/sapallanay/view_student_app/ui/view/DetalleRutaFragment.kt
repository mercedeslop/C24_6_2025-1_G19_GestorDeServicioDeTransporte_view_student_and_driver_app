package com.jennyfer.sapallanay.view_student_app.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.data.model.Ruta
import com.jennyfer.sapallanay.view_student_app.databinding.FragmentDetalleRutaBinding

class DetalleRutaFragment : Fragment() {

    private var _binding: FragmentDetalleRutaBinding? = null
    private val binding get() = _binding!!
    private lateinit var ruta: Ruta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ruta = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("ruta", Ruta::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                it.getSerializable("ruta") as Ruta
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetalleRutaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Ocultar BottomNavigationView
        val bottomNav = requireActivity().findViewById<View>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE

        // Pintar los datos
        binding.txtNombreRuta.text = "${ruta.tituloRuta}"
        binding.txtAsientos.text = ruta.numeroDisponibilidad
        binding.txtUnidad.text = ruta.unidad
        binding.txtHoraSalida.text = ruta.horaSalida
        binding.txtHoraLlegada.text = ruta.horaLlegada
        binding.txtConductor.text = ruta.conductor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Volver a mostrar el BottomNavigationView
        val bottomNav = requireActivity().findViewById<View>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE
    }

}