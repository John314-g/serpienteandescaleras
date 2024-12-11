package com.ud.serpientesescalerasmvvm.views

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ud.serpientesescalerasmvvm.R
import com.ud.serpientesescalerasmvvm.viewmodel.JuegoViewModel
import android.util.Log

class JuegoActivity : AppCompatActivity() {

    private lateinit var dado1: TextView
    private lateinit var dado2: TextView
    private lateinit var btnJugar: Button
    private lateinit var botones: List<Button>

    private val viewModel: JuegoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        dado1 = findViewById(R.id.dado1)
        dado2 = findViewById(R.id.dado2)
        btnJugar = findViewById(R.id.btnJugar)

        // Inicializar botones del tablero
        botones = listOf(
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
            findViewById(R.id.btn9),
            findViewById(R.id.btn10),
            findViewById(R.id.btn11),
            findViewById(R.id.btn12),
            findViewById(R.id.btn13),
            findViewById(R.id.btn14),
            findViewById(R.id.btn15),
            findViewById(R.id.btn16),
            findViewById(R.id.btn17),
            findViewById(R.id.btn18),
            findViewById(R.id.btn19),
            findViewById(R.id.btn20),
            findViewById(R.id.btn21),
            findViewById(R.id.btn22),
            findViewById(R.id.btn23),
            findViewById(R.id.btn24)
        )

        // Observa los valores de los dados
        viewModel.dado1.observe(this, Observer { dado1.text = it.toString() })
        viewModel.dado2.observe(this, Observer { dado2.text = it.toString() })

        // Observa las posiciones de los jugadores
        viewModel.posicionesJugadores.observe(this, Observer { posiciones ->
            actualizarTablero(posiciones)
        })

        // Observa si hay un ganador
        viewModel.ganador.observe(this, Observer { ganador ->
            if (ganador != null) {
                Toast.makeText(this, "¡$ganador ha ganado!", Toast.LENGTH_LONG).show()
                // Lógica para mostrar la pantalla de ganador
            }
        })

        // Configura el botón para tirar los dados
        btnJugar.setOnClickListener {
            viewModel.tirarDados()
        }
    }

    private fun actualizarTablero(posiciones: Map<Int, Int>) {
        // Limpia todos los botones
        botones.forEach { it.setBackgroundColor(android.graphics.Color.TRANSPARENT) }

        // Actualiza las posiciones de los jugadores
        posiciones.forEach { (jugador, posicion) ->
            // Verificar si la posición está dentro del rango válido
            if (posicion in 1..24) {
                val color = if (jugador == 0) android.graphics.Color.BLUE else android.graphics.Color.RED
                botones[posicion - 1].setBackgroundColor(color)
            } else {
                // Log para depuración
                Log.e("ActualizarTablero", "Posición inválida para jugador $jugador: $posicion")
            }
        }
    }

}

