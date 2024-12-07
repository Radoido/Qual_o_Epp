package meuApp.paramosaonde

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import meuApp.paramosaonde.databinding.ActivityCreateEditContentBinding

class CreateEditContent : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEditContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateEditContentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_edit_content)

    }
}