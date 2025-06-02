package com.jennyfer.sapallanay.view_student_app.ui.view.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.adapter.RutasAdapter
import com.jennyfer.sapallanay.view_student_app.databinding.FragmentInicioBinding
import com.jennyfer.sapallanay.view_student_app.ui.viewmodel.RutaViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InicioFragment : Fragment() {

    // De esta forma se implementa binding en fragments
    private var _binding: FragmentInicioBinding? = null // variable binding
    private val binding get() = _binding!!
    private val viewModel: RutaViewModel by viewModels()
    private lateinit var adapter: RutasAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeRutas()
        return binding.root

    }

    private fun setupRecyclerView() {
        adapter = RutasAdapter(emptyList()) { ruta ->
            // acción al hacer clic en una ruta, ej: navegar a detalle
            val fragment = DetalleRutaFragment()
            val bundle = Bundle()
            bundle.putSerializable("ruta", ruta)
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerRutas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRutas.adapter = adapter
    }

    private fun observeRutas() {
        lifecycleScope.launch {
            viewModel.rutas.collectLatest { rutas ->
                adapter.updateData(rutas)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}