package com.example.feedback3.adaptadores

import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.feedback3.R
import com.example.feedback3.actividades.DetallesNovelaActivity
import com.example.feedback3.dataClasses.Novela

class NovelaAdapter(
    private val context: Context,
    private val novelas: List<Novela>
) : ArrayAdapter<Novela>(context, 0, novelas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val novela = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false)

        val tvTitulo = view.findViewById<TextView>(R.id.tvTitulo)
        val tvAutor = view.findViewById<TextView>(R.id.tvAutor)

        tvTitulo.text = novela?.titulo
        tvAutor.text = novela?.autor

        // Cambiar el color de fondo o agregar un ícono si es favorita
        if (novela?.esFavorita == true) {
            // Por ejemplo, color de fondo amarillo para las novelas favoritas
            view.setBackgroundColor(context.getColor(R.color.colorFavorito)) // Debes definir este color en colors.xml
        } else {
            // Color de fondo por defecto
            view.setBackgroundColor(context.getColor(android.R.color.transparent))
        }

        view.setOnClickListener {
            // Al hacer click en la novela, abre la actividad de detalles
            val intent = Intent(context, DetallesNovelaActivity::class.java)
            intent.putExtra("titulo", novela?.titulo)
            intent.putExtra("autor", novela?.autor)
            intent.putExtra("anio", novela?.anioPublicacion)
            intent.putExtra("sinopsis", novela?.sinopsis)
            intent.putExtra("esFavorita", novela?.esFavorita)

            context.startActivity(intent)
        }
        return view
    }
}
