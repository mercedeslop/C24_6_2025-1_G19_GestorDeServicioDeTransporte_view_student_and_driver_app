package com.jennyfer.sapallanay.view_student_app.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.adapter.ParaderosAdapter
import com.jennyfer.sapallanay.view_student_app.data.model.Ruta
import com.jennyfer.sapallanay.view_student_app.databinding.BottomSheetParaderosBinding

class ParaderosBottomSheet(private val ruta: Ruta) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetParaderosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED

                it.setBackgroundResource(R.drawable.bottom_sheet_background)
            }
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetParaderosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerParaderosOfBsheet.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ParaderosAdapter(ruta.paraderos) { paraderoSeleccionado ->

                //Lógica al hacer clic en un paradero
                val qrFragment = QRFragment.newInstance(ruta.tituloRuta, paraderoSeleccionado)
                dismiss()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, qrFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
