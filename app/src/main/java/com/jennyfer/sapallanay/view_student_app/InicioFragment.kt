package com.jennyfer.sapallanay.view_student_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.adapter.RutasAdapter
import com.jennyfer.sapallanay.view_student_app.databinding.FragmentInicioBinding


class InicioFragment : Fragment() {

    // De esta forma se implementa binding en fragments
    private var _binding: FragmentInicioBinding? = null // variable binding
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root


    }

    private fun initRecyclerView() {
        // Configurar el RecyclerView "requireContext()" es mas adecuado en fragments
        binding.recyclerRutas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRutas.adapter = RutasAdapter(RutasProvider.rutasList)
    }



}