package com.ud.serpientesescalerasmvvm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val buttonRegistrar = findViewById<Button>(R.id.buttonRegistrar)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val editTextContrasena = findViewById<EditText>(R.id.editTextContrasena)

        buttonRegistrar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val correo = editTextCorreo.text.toString()
            val contrasena = editTextContrasena.text.toString()

            auth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso
                        Toast.makeText(baseContext, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        finish() // Cierra RegistroActivity y regresa a ComienzoActivity
                    } else {
                        // Error en el registro
                        Toast.makeText(baseContext, "Error en el registro: ${task.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}