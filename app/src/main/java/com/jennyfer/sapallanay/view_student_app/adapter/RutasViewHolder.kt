package com.jennyfer.sapallanay.view_student_app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.Ruta
import com.jennyfer.sapallanay.view_student_app.databinding.ItemRutasBinding


class RutasViewHolder (view: View): RecyclerView.ViewHolder(view){

    val binding = ItemRutasBinding.bind(view)

    fun render(rutaModel: Ruta){
        binding.txtDisponibles.text = rutaModel.numeroDisponibilidad
        binding.txtRuta.text = rutaModel.tituloRuta
        binding.txtTurno.text = rutaModel.publisher


    }

}