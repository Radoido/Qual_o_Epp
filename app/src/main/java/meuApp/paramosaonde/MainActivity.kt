package meuApp.paramosaonde

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

// Para salvar dados:
        val sharedPreferences = getSharedPreferences("animes", 0)
        val editor = sharedPreferences.edit()
        editor.putString("CHAVE", "valor")
        editor.apply()

// Para recuperar dados:
        val valor = sharedPreferences.getString("CHAVE", "valor padrão")


 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)






    }
}