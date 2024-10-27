package com.example.feedback3.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.feedback3.R
import com.example.feedback3.dataClasses.Novela

class NovelaAdapter(context: Context, novelas: List<Novela>) : ArrayAdapter<Novela>(context, 0, novelas) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val novela = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false)

        val textViewTitulo = view.findViewById<TextView>(R.id.textViewTitulo)
        val textViewAutor = view.findViewById<TextView>(R.id.textViewAutor)

        textViewTitulo.text = novela?.titulo
        textViewAutor.text = novela?.autor

        return view
    }
}
