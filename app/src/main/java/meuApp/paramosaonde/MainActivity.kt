package meuApp.paramosaonde

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import meuApp.paramosaonde.databinding.ActivityMainBinding


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
        val listShows = ArrayList<Show>()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listShows)
        binding.listShows.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, CreateEditContent::class.java)
            startActivity(intent)

        }

        binding.listShows.setOnItemClickListener { _, _, position, _ ->
            val listShows = listShows[position]
            val intent = Intent(this, CreateEditContent::class.java).apply {
                putExtra("title", listShows.title)
                putExtra("imgUri", listShows.imgUri)
                putExtra("ep", listShows.ep)
                putExtra("id", listShows.id)
            }
            startActivity(intent)

            }


    }
}