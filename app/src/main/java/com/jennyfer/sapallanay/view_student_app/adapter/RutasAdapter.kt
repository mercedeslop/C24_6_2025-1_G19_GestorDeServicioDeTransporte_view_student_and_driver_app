package com.jennyfer.sapallanay.view_student_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.data.model.Ruta

class RutasAdapter(
    private var rutaList: List<Ruta>,
    private val onItemClick: (Ruta) -> Unit
) : RecyclerView.Adapter<RutasViewHolder>() {

    fun updateData(newList: List<Ruta>) {
        rutaList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RutasViewHolder(layoutInflater.inflate(R.layout.item_rutas, parent, false))
    }

    override fun onBindViewHolder(holder: RutasViewHolder, position: Int) {
        val item = rutaList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = rutaList.size
}
