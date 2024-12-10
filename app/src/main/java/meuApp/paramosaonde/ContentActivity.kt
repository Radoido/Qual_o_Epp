package meuApp.paramosaonde

import android.content.Intent
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
        var ep = intent.getIntExtra("ep",0)
        val id = intent.getIntExtra("id",0 )


        binding.txtShow.text = title
        binding.imgShow.setImageURI(Uri.parse(imgUri))
        binding.txtEp.text = "$ep"

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnIncrement.setOnClickListener{
            ep = binding.txtEp.text.toString().toInt()
            ep++
            val result = db.updateEp(ep, id)

            if (result == 1){
                binding.txtEp.text = "$ep"
            }else{
                Toast.makeText(this, "Erro ao incrementar", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnDecrement.setOnClickListener {
            ep = binding.txtEp.text.toString().toInt()
            ep--
            if (ep < 0){
                Toast.makeText(this, "Episódio não pode ser negativo", Toast.LENGTH_SHORT).show()
            }else {
                val result = db.updateEp(ep, id)

                if (result == 1) {
                    binding.txtEp.text = "$ep"
                } else {
                    Toast.makeText(this, "Erro ao decrementar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}