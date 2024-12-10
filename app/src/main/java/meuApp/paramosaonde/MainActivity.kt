package meuApp.paramosaonde

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import meuApp.paramosaonde.databinding.ActivityMainBinding


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

        adapter = ArrayAdapter(this, R.layout.simple_list_item_1, listShows)
        binding.listShows.adapter = adapter
        adapter.notifyDataSetChanged()


        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, CreateEditContent::class.java)
            startActivity(intent)
            finish()
        }

        binding.listShows.setOnItemClickListener { _, _, position, _ ->
            val listShow = listShows[position]
            val intent = Intent(this, ContentActivity::class.java).apply {
                putExtra("title", listShow.title)
                putExtra("ep", listShow.ep)
                putExtra("imgUri", listShow.imgUri)
                putExtra("id", listShow.id)
            }
            startActivity(intent)

            }


    }
}