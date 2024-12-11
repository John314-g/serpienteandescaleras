package com.ud.serpientesescalerasmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log


class JuegoViewModel : ViewModel() {

    private val _posicionesJugadores = MutableLiveData<Map<Int, Int>>() // Posiciones actuales de los jugadores
    val posicionesJugadores: LiveData<Map<Int, Int>> = _posicionesJugadores

    private val _turno = MutableLiveData<Int>().apply { value = 0 } // 0 = Jugador 1, 1 = Jugador 2
    val turno: LiveData<Int> = _turno

    private val _dado1 = MutableLiveData<Int>()
    val dado1: LiveData<Int> = _dado1

    private val _dado2 = MutableLiveData<Int>()
    val dado2: LiveData<Int> = _dado2

    private val _ganador = MutableLiveData<String?>()
    val ganador: LiveData<String?> = _ganador

    init {
        _posicionesJugadores.value = mapOf(0 to 0, 1 to 0) // Ambos jugadores empiezan en la posición 0
    }

    fun tirarDados() {
        val dado1Value = (1..4).random()
        val dado2Value = (1..4).random()

        val suma = dado1Value + dado2Value
        _dado1.value = dado1Value
        _dado2.value = dado2Value

        val jugadorActual = _turno.value ?: 0
        moverJugador(jugadorActual, suma)
        verificarEscaleraOSerpiente(jugadorActual)
        verificarGanador()

        if (suma != 6) { // Cambiar turno si no sacó 6
            _turno.value = if (jugadorActual == 0) 1 else 0
        }
    }
    private fun moverJugador(jugador: Int, avance: Int) {
        val posiciones = _posicionesJugadores.value?.toMutableMap() ?: return
        val posicionActual = posiciones[jugador] ?: 0
        val nuevaPosicion = posicionActual + avance

        // Verificar límites del tablero
        if (nuevaPosicion > 24) {
            Log.e("MoverJugador", "Posición excede el límite del tablero: $nuevaPosicion")
        } else if (nuevaPosicion < 1) {
            Log.e("MoverJugador", "Posición es menor que el límite: $nuevaPosicion")
        }

        // Ajustar posición al rango permitido
        posiciones[jugador] = nuevaPosicion.coerceIn(1, 24)
        _posicionesJugadores.value = posiciones

        // Log para depuración
        Log.d("MoverJugador", "Jugador $jugador movido a posición ${posiciones[jugador]}")
    }



    private fun verificarEscaleraOSerpiente(jugador: Int) {
        val posiciones = _posicionesJugadores.value?.toMutableMap() ?: return
        val posicionActual = posiciones[jugador] ?: return

        when (posicionActual) {
            3 -> posiciones[jugador] = 11 // Escalera
            10 -> posiciones[jugador] = 15 // Escalera
            16 -> posiciones[jugador] = 17 // Escalera
            7 -> posiciones[jugador] = 2 // Serpiente
            20 -> posiciones[jugador] = 12 // Serpiente
            23 -> posiciones[jugador] = 18 // Serpiente
        }

        _posicionesJugadores.value = posiciones
    }

    private fun verificarGanador() {
        val posiciones = _posicionesJugadores.value ?: return
        posiciones.forEach { (jugador, posicion) ->
            if (posicion == 24) {
                _ganador.value = "Jugador ${jugador + 1}"
            }
        }
    }
}
