package com.by_jogehrt.mygot.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.by_jogehrt.mygot.databinding.ActivityLogginBinding

class Loggin : AppCompatActivity() {

    private lateinit var binding: ActivityLogginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}