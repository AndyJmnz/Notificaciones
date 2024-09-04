package com.example.practica01notificaciones

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnRegistrar = findViewById<Button>(R.id.btn_registrar)
        val etProductName = findViewById<EditText>(R.id.et_product_name)
        val etProductPrice = findViewById<EditText>(R.id.et_product_price)

        btnRegistrar.setOnClickListener {
            val productName = etProductName.text.toString()
            val productPrice = etProductPrice.text.toString()

            if (productName.isNotBlank() && productPrice.isNotBlank()) {
                val intent = Intent()
                intent.putExtra("productName", productName)
                intent.putExtra("productPrice", productPrice)
                setResult(RESULT_OK, intent) // Envía el resultado de vuelta

                Toast.makeText(this, "Producto registrado: $productName", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Por favor, ingresa todos los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}