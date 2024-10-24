package com.example.feedback3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelaAdapter(
    private val novelaList: List<Novela>,
    private val onClick: (Novela) -> Unit // Callback para manejar clics
) : RecyclerView.Adapter<NovelaAdapter.NovelaViewHolder>() {

    inner class NovelaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val authorTextView: TextView = itemView.findViewById(R.id.tvAuthor)

        fun bind(novela: Novela) {
            titleTextView.text = novela.titulo
            authorTextView.text = novela.autor

            // Establecer un clic en el item
            itemView.setOnClickListener {
                onClick(novela) // Llamar al callback cuando se hace clic
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_novela, parent, false)
        return NovelaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelaViewHolder, position: Int) {
        holder.bind(novelaList[position])
    }

    override fun getItemCount(): Int {
        return novelaList.size
    }
}
