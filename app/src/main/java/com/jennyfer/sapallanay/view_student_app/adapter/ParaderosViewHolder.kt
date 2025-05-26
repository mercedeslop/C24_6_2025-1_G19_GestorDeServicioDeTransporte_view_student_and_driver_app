package com.jennyfer.sapallanay.view_student_app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.databinding.ItemParaderoOfBsheetBinding

class ParaderosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemParaderoOfBsheetBinding.bind(view)

    fun bind(paradero: String) {
        binding.txtNameParadero.text = paradero
    }
}