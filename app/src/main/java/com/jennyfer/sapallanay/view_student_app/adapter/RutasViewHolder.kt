package com.jennyfer.sapallanay.view_student_app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.data.model.Ruta
import com.jennyfer.sapallanay.view_student_app.databinding.ItemRutasBinding


class RutasViewHolder (view: View): RecyclerView.ViewHolder(view){

    private val binding = ItemRutasBinding.bind(view)

    fun render(ruta: Ruta) {
        binding.txtRuta.text = ruta.tituloRuta
        binding.txtTurno.text = ruta.turno
        binding.txtDisponibles.text = ruta.numeroDisponibilidad
    }
}