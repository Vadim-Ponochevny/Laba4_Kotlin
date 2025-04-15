package com.example.laba4_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// MainActivity реализует интерфейс CellClickListener
class MainActivity : AppCompatActivity(), CellClickListener {
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.setPadding(0, getStatusBarHeight(), 0, 0)

        val colorList = arrayListOf(
            ColorData("WHITE", "#FFFFFF"),
            ColorData("BLACK", "#000000"),
            ColorData("BLUE", "#0000FF"),
            ColorData("RED", "#FF0000"),
            ColorData("MAGENTA", "#FF00FF")
        )

        val adapter = Adapter(this, colorList, this)
        recyclerView.adapter = adapter
    }

    override fun onCellClick(colorName: String) {
        Toast.makeText(this, "IT’S $colorName", Toast.LENGTH_SHORT).show()
    }


}



data class ColorData(
    val colorName: String,
    val colorHex: String
)


class Adapter(
    private val context: Context,
    private val list: ArrayList<ColorData>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView: View = itemView.findViewById(R.id.picture)
        val colorText: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rview_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.colorView.setBackgroundColor(Color.parseColor(currentItem.colorHex))
        holder.colorText.text = currentItem.colorName

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClick(currentItem.colorName)
        }
    }

    override fun getItemCount(): Int = list.size
}

interface CellClickListener {
    fun onCellClick(colorName: String)
}
