package com.chat.nasim.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chat.nasim.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val name: EditText = findViewById(R.id.name)
        val host: EditText = findViewById(R.id.host)
        val port: EditText = findViewById(R.id.port)
        val finish: Button = findViewById(R.id.finish)

        finish.setOnClickListener {
            if (name.text.toString().isNotEmpty() && host.text.toString().isNotEmpty() && port.text.toString().isNotEmpty()) {
                val intent = Intent(this, ChatRoom::class.java)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("server","${host.text.toString()}:${port.text.toString()}")
                startActivity(intent)
                //finish()
            } else {
                Toast.makeText(this, "Field can not be empty!", Toast.LENGTH_LONG).show()
            }
        }

    }
}