package com.jennyfer.sapallanay.view_student_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jennyfer.sapallanay.view_student_app.databinding.FragmentDetalleRutaBinding


class DetalleRutaFragment : Fragment() {

    private var _binding: FragmentDetalleRutaBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }


}