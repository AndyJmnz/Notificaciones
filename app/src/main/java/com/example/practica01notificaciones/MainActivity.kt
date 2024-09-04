package com.example.practica01notificaciones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val usuarios = mapOf(
        "andy" to "1234",
        "ivan" to "4663",
        "jesus" to "6196",
        "josue" to "7891",
        "josh" to "5894"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val etUsername = findViewById<EditText>(R.id.edt_usuario)
        val etPassword = findViewById<EditText>(R.id.edt_Password)
        val btnLogin = findViewById<Button>(R.id.bt_ingresar)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (Validaciones(username, password)) {
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Validaciones(usuario: String, contrasena: String): Boolean {
        if (usuario.isEmpty()) {
            findViewById<EditText>(R.id.edt_usuario).error = "Usuario requerido"
            return false
        } else if (contrasena.isEmpty()) {
            findViewById<EditText>(R.id.edt_Password).error = "Contraseña requerida"
            return false
        } else if (usuarios[usuario] == contrasena) {
            return true
        } else {
            findViewById<EditText>(R.id.bt_ingresar).error = "Usuario o contraseña incorrectos"
            return false
        }
    }
}