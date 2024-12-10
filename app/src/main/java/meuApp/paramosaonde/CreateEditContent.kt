package meuApp.paramosaonde

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.core.view.WindowInsetsCompat
import meuApp.paramosaonde.databinding.ActivityCreateEditContentBinding
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class CreateEditContent : AppCompatActivity() {

    private var imgUri: Uri? = null
    private var onImageSelected: ((Uri?) -> Unit)? = null
    private var p = -1

    private lateinit var binding: ActivityCreateEditContentBinding
    private lateinit var adapter: ArrayAdapter<Show>


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateEditContentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.crudContent)


        val db = DAO(this)
        var listShows = db.getShows()
        binding.btnImg.setImageResource(R.drawable.anime_placeholder)
        binding.edtEp.setText("0")

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listShows)
        binding.listShows.adapter = adapter

        binding.listShows.setOnItemClickListener { _, _, p, _ ->
            this.p = p
            val listShow = listShows[p]
            binding.edtShow.setText(listShow.title)
            binding.edtEp.setText(listShow.ep.toString())
            binding.btnImg.setImageURI(Uri.parse(listShows[p].imgUri)) 
            binding.txtId.text = ("ID:  ${listShow.id}")
            Toast.makeText(this, "${Uri.parse(listShows[p].imgUri)}", Toast.LENGTH_SHORT).show()
        }

        binding.btnSave.setOnClickListener {
            val title = binding.edtShow.text.toString()
            val ep = binding.edtEp.text.toString().toInt()
            val uriImage = binding.btnImg.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "Digite o titulo do show", Toast.LENGTH_SHORT).show()

            }else{
                val result = db.addShow(title, uriImage, ep)
                if (result > 0) {
                    listShows.add(Show(result.toInt(), title, uriImage, ep))
                    adapter.notifyDataSetChanged()
                    binding.edtShow.text.clear()
                    binding.edtEp.text.clear()
                    binding.txtId.text = "ID: "
                    binding.btnImg.setImageResource(R.drawable.anime_placeholder)
                    Toast.makeText(this, "Show adicionado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao adicionar", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.btnEdit.setOnClickListener{
            val title = binding.edtShow.text.toString()
            val ep = binding.edtEp.text.toString()
            val uriImage = imgUri.toString()
            val id = binding.txtId.text.toString().substringAfter("ID: ").trim()
            val idInt = id.toInt()

            if ((id.isEmpty()) || (title.isEmpty()) || (ep.isEmpty())) {

                Toast.makeText(this, "Selecione o show que deseja editar", Toast.LENGTH_SHORT).show()

            }else if (idInt > 0) {
                val result = db.updateShow(title, uriImage, ep.toInt(), idInt)

                if (result > 0) {
                    listShows[p] = Show(idInt, title, uriImage, ep.toInt())

                    adapter.notifyDataSetChanged()
                    binding.edtShow.text.clear()
                    binding.edtEp.text.clear()
                    binding.btnImg.setImageResource(R.drawable.anime_placeholder)
                    binding.txtId.text = "ID: "
                    Toast.makeText(this, "Show atualizado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnDelete.setOnClickListener{
            val id = binding.txtId.text.toString()
            val idInt = id.substringAfter("ID: ").trim().toInt()
            val result = db.deleteShow(idInt)

            if (result > 0){
                listShows.removeAt(p)
                Log.e("teste", "lista de shows: $adapter")
                adapter.notifyDataSetChanged()

                binding.edtShow.text.clear()
                binding.edtEp.text.clear()
                binding.btnImg.setImageResource(R.drawable.anime_placeholder)
                binding.txtId.text = "ID: "

                Toast.makeText(this, "Show deletado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Erro ao deletar", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnImg.setOnClickListener {
            checkMediaPermission { selectedUri ->
                    if (selectedUri != null) {
                        handleImageUri(selectedUri)
                        binding.btnImg.setImageURI(imgUri)
                } else {
                    println("Nenhuma imagem foi selecionada.")

                }
            }
        }

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        enableEdgeToEdge()

    }


    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleImageUri(it)
            onImageSelected?.invoke(it)
        }  // Processa a URI da imagem selecionada
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openImagePicker()  // Permissão concedida, abre a galeria
        } else {
            Toast.makeText(this, "Permissão negada para acessar a galeria", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkMediaPermission(onImageReady: (Uri?) -> Unit)  {
        // Verifica se a permissão necessária foi concedida
        onImageSelected = onImageReady  // Armazena o callback

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            openImagePicker()  // Permissão já concedida, abre a galeria
        } else {
            permissionLauncher.launch(permission)  // Solicita a permissão
        }
    }

    private fun openImagePicker() {
        imagePickerLauncher.launch("image/*")  // Abre a galeria para selecionar imagens
    }

    private fun handleImageUri(uri: Uri) {
        val appContext = applicationContext
        val folder = File(appContext.filesDir, "images")  // Pasta interna para salvar imagens

        if (!folder.exists()) {
            folder.mkdirs()  // Cria a pasta se não existir
        }

        val fileName = "show_image_${System.currentTimeMillis()}.jpg"  // Nome único para o arquivo
        val newFile = File(folder, fileName)

        try {
            // Copia o conteúdo da imagem para a nova localização
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(newFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            Toast.makeText(this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show()
            imgUri = Uri.fromFile(newFile)  // Salva a URI da imagem para referência futura

        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao salvar a imagem: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
