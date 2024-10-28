
import android.content.Intent
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.UnderlineSpan
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

        // Configura el título con subrayado y color amarillo si es favorita
        if (novela?.esFavorita == true) {
            val spannableTitle = SpannableString(novela.titulo).apply {
                setSpan(UnderlineSpan(), 0, novela.titulo.length, 0)
            }
            tvTitulo.text = spannableTitle
            tvTitulo.setTextColor(Color.MAGENTA)  // Color magenta para el título de novelas favoritas
        } else {
            tvTitulo.text = novela?.titulo
            tvTitulo.setTextColor(Color.BLACK)  // Color predeterminado para novelas no favoritas
        }

        tvAutor.text = novela?.autor

        view.setOnClickListener {
            // Al hacer click en la novela, abre la actividad de detalles
            val intent = Intent(context, DetallesNovelaActivity::class.java).apply {
                putExtra("titulo", novela?.titulo)
                putExtra("autor", novela?.autor)
                putExtra("anio", novela?.anioPublicacion)
                putExtra("sinopsis", novela?.sinopsis)
                putExtra("esFavorita", novela?.esFavorita)
            }
            context.startActivity(intent)
        }
        return view
    }
}