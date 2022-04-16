package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.EditPostActivityBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = EditPostActivityBinding.inflate(layoutInflater)
        binding.edit.text = intent.extras?.get("Edit").toString().toEditable()
        setContentView(binding.root)
        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            val intent = Intent(this@EditPostActivity, MainActivity::class.java)
            if (binding.edit.text.isNullOrBlank()) {
                setResult(RESULT_CANCELED, intent)
            } else {
                val content: String?
                content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }


    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}