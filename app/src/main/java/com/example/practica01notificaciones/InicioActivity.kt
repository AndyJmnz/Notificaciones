package com.example.practica01notificaciones

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class InicioActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private lateinit var adapter: ProductoAdapter
    private val productos = mutableListOf<Producto>() // Lista para almacenar productos

    private val registroLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val productName = data?.getStringExtra("productName") ?: ""
            val productPrice = data?.getStringExtra("productPrice") ?: ""
            val producto = Producto(productName, productPrice)
            productos.add(producto) // Agrega el producto a la lista
            adapter.notifyItemInserted(productos.size - 1) // Notifica al adaptador sobre el nuevo elemento
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val btnBack = findViewById<ImageButton>(R.id.btn_back1)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        val btnAgregar = findViewById<ImageButton>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            registroLauncher.launch(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductoAdapter(this, productos) // Inicializa el adaptador con la lista de productos
        recyclerView.adapter = adapter

        val btnAlarma = findViewById<Button>(R.id.btnAlarma)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        btnAlarma.setOnClickListener {
            // iniciar RegistroActivity desde la alarma
            val intent = Intent(this, InformacionActivity::class.java)
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val tiempoAlarma = System.currentTimeMillis() + 30000 // Configura la alarma para 30seg después

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempoAlarma, pendingIntent)
            Toast.makeText(this, "Alarma configurada", Toast.LENGTH_SHORT).show()
        }

        btnCancelar.setOnClickListener {
            if (::pendingIntent.isInitialized) {
                alarmManager.cancel(pendingIntent)
                Toast.makeText(this, "Alarma cancelada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}