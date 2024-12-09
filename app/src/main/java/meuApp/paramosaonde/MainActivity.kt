package meuApp.paramosaonde

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import meuApp.paramosaonde.databinding.ActivityMainBinding
import kotlin.math.log


/*
v1.0
O sistema deve permitir ao usuário cadastrar um show(mídia no geral)
O sistema deve permitir ao usuário incrementar e decrementar a contagem de episódios (funcionalidade separada das anotações)
O sistema deverá permitir ao usuário fazer upload de uma foto para cada show
O sistema deverá ser escrito em flutter
O sistema deverá rodar em um sistema operacional android
O sistema deverá ser disponibilizado para teste alfa no formato .apk
--------------------------
O sistema deve permitir ao usuário fazer anotações sobre o show
O sistema deverá mostrar no formato de lista/grid o nome dos show e o contador de episódios como informações de resumo
Barra de progresso

 */

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<Show>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)


        val db = DAO(this)
        val listShows = db.getShows()
        binding.txtTitle.text = listShows[1].ep.toString()
        adapter = ArrayAdapter(this, R.layout.simple_list_item_1, listShows)
        binding.listShows.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, CreateEditContent::class.java)
            startActivity(intent)

        }

        binding.listShows.setOnItemClickListener { _, _, position, _ ->
            val listShow = listShows[position]
            val intent = Intent(this, ContentActivity::class.java).apply {
                putExtra("title", listShow.title)
                putExtra("ep", "Episódio: ${listShow.ep}")
                putExtra("imgUri", listShow.imgUri)
                putExtra("id", listShow.id)
            }
            startActivity(intent)

            }


    }
}