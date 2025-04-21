package com.jennyfer.sapallanay.view_student_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.jennyfer.sapallanay.view_student_app.R
import com.jennyfer.sapallanay.view_student_app.Ruta

class RutasAdapter (private val rutaList: List<Ruta>) : RecyclerView.Adapter<RutasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RutasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RutasViewHolder(layoutInflater.inflate(R.layout.item_rutas, parent, false))

    }

    override fun onBindViewHolder(
        holder: RutasViewHolder,
        position: Int
    ) {
        val item = rutaList[position]
        holder.render(item)

    }

    override fun getItemCount(): Int = rutaList.size
}
