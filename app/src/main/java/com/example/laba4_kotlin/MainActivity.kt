package com.example.laba4_kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.net.HttpURLConnection
import okhttp3.Request
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val internetButton: Button = findViewById(R.id.btnHTTP)
        val btnOkHTTP: Button = findViewById(R.id.btnOkHTTP)

        internetButton.setOnClickListener{
            val url = URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")

            Thread {
                val connection1 = url.openConnection() as HttpURLConnection
                try {
                    val data = connection1.inputStream.bufferedReader().readText()
                    connection1.disconnect()
                    Log.d("Flickr cats", data)
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

        btnOkHTTP.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val client = OkHttpClient()
                val url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"
                val request = Request.Builder().url(url).build()

                try {
                    val response = client.newCall(request).execute()
                    val text = response.body?.string()
                    Log.i("Flickr OkCats", text ?: "Пустой ответ")
                } catch (e: Exception) {
                    Log.e("Flickr OkCats", "Ошибка: ${e.message}")
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
