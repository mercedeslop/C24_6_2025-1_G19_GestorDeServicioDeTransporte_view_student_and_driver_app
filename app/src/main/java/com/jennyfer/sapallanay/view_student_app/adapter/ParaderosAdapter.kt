package com.jennyfer.sapallanay.view_student_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.databinding.ItemParaderoOfBsheetBinding

class ParaderosAdapter(
    private val paraderos: List<String>,
    private val onParaderoSelected: (String) -> Unit
) : RecyclerView.Adapter<ParaderosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParaderosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paradero_of_bsheet, parent, false)
        return ParaderosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParaderosViewHolder, position: Int) {
        val paradero = paraderos[position]
        holder.bind(paradero)
        holder.itemView.setOnClickListener {
            onParaderoSelected(paradero)
        }
    }

    override fun getItemCount(): Int = paraderos.size
}
