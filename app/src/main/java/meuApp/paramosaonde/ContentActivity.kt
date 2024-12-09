package meuApp.paramosaonde

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import meuApp.paramosaonde.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityContentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.content)


        val db = DAO(this)
        val title = intent.getStringExtra("title")
        val imgUri = intent.getStringExtra("imgUri")
        val ep = intent.getIntExtra("ep",0)
        Toast.makeText(this, "episodio: $ep", Toast.LENGTH_SHORT).show()
        val id = intent.getIntExtra("id",0 )


        binding.txtShow.text = title
        binding.txtEp.text = ep.toString()
        binding.imgShow.setImageURI(Uri.parse(imgUri))

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.btnIncrement.setOnClickListener{
            var ep = binding.txtEp.text.toString()
            ep = ep.substringAfter("Episódio: ").trim()
            var epInt = ep.toInt()
            epInt++

            // Utiliza also, ver como funciona
            epInt.toString().also { binding.txtEp.text = it }
            val result = db.updateEp(epInt, id)


            if (result == 1){
                Toast.makeText(this, "Episodio incrementado", Toast.LENGTH_SHORT).show()
                binding.txtEp.text = ("Episódio: $epInt")
            }else{
                Toast.makeText(this, "Erro ao incrementar", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnDecrement.setOnClickListener {
            var ep = binding.txtEp.text.toString()
            ep = ep.substringAfter("Episódio: ").trim()
            var epInt = ep.toInt()
            epInt--
            binding.txtEp.text = epInt.toString()
            val result = db.updateEp(epInt, id)

            if (result == 1){
                Toast.makeText(this, "Episodio decrementado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Erro ao decrementar", Toast.LENGTH_SHORT).show()
            }
        }
    }


}