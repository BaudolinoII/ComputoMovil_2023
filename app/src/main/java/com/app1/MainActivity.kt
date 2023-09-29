package com.app1

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.app1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //val tvSaludo = findViewById<TextView>(R.id.tvSaludo)
        //tvSaludo.text = "Cambio de texto desde actividad"
        binding.btn1.setOnClickListener{
            binding.tvSaludo.text = "Usando Bindings"
        }

    }
}