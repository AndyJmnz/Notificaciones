package com.example.practica01notificaciones

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val context: Context, private val productList: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductViewHolder>() {

        private val canalNombre = "Practica Notificicaciones"
        private val canalId = "canalId"
        private val notificationId = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_producto, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val btnBuy: Button = itemView.findViewById(R.id.btnBuy)

        fun bind(product: Producto) {
            tvProductName.text = product.name
            tvProductPrice.text = product.price
            btnBuy.setOnClickListener {
                createNotificationCanal()
                showNotification(product)
            }
        }

            private fun createNotificationCanal(){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    val canalImportancia = NotificationManager.IMPORTANCE_HIGH
                    val canal = NotificationChannel(canalId, canalNombre, canalImportancia)

                    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    manager.createNotificationChannel(canal)
                }
            }
        private fun showNotification(product: Producto) {

            val notificationBuilder = NotificationCompat.Builder(context, canalId).also{
                it.setContentTitle("Compra realizada")
                it.setContentText("Has comprado ${product.name} por ${product.price}")
                it.setSmallIcon(R.drawable.icon_mensaje)
                it.priority = NotificationCompat.PRIORITY_HIGH
            }.build()

            val notificationManager = NotificationManagerCompat.from(context)

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(notificationId,notificationBuilder)
        }
    }
}