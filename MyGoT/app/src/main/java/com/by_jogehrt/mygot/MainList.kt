package com.by_jogehrt.mygot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.by_jogehrt.mygot.databinding.ActivityMainListBinding

class MainList : AppCompatActivity() {
    private lateinit var binding: ActivityMainListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val info_list = ArrayList<InfoNode>()
        for(i in 1 .. 10){ //Obtener de alguna forma la longitud de la API
            info_list.add(InfoNode(10293,"Joaquin","Espino","imagen.png"))
        }

        binding.lvList.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.lvList.adapter = InfoAdapter(info_list){inf,position->

        }


    }
}