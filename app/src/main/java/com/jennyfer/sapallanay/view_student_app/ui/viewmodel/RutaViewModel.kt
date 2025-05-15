package com.jennyfer.sapallanay.view_student_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jennyfer.sapallanay.view_student_app.data.model.Ruta
import com.jennyfer.sapallanay.view_student_app.data.repository.RutaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RutaViewModel : ViewModel() {

    private val repository = RutaRepository()

    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> get() = _rutas
    private var turnoActual = getTurnoActual()

    init {
        observeTurnoEnTiempoReal()
    }

    private fun observeTurnoEnTiempoReal() {
        viewModelScope.launch {
            while (true) {
                val nuevoTurno = getTurnoActual()
                if (nuevoTurno != turnoActual) {
                    turnoActual = nuevoTurno
                    fetchRutas()
                }
                delay(60_000L) // Revisa cada 1 minuto (60000 ms)
            }
        }

        fetchRutas() // carga inicial
    }

    private fun getTurnoActual(): String {
        val hora = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when (hora) {
            in 6..11 -> "Mañana"
            in 12..21 -> "Tarde"
            else -> "Noche"
        }
    }

    private fun fetchRutas() {
        viewModelScope.launch {
            try {
                // Ejecución en hilo IO para la petición
                val data = withContext(Dispatchers.IO) {
                    repository.getRutas()
                }

                // Regreso al hilo Main para actualizar UI
                val rutasFiltradas = data.filter { it.turno == turnoActual }
                _rutas.value = rutasFiltradas

            } catch (e: Exception) {
                e.printStackTrace()
                _rutas.value = emptyList()
            }
        }
    }
}