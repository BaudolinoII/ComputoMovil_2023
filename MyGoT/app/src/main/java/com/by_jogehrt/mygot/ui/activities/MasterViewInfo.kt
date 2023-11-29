package com.by_jogehrt.mygot.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.by_jogehrt.mygot.databinding.ActivityMasterViewInfoBinding
import com.by_jogehrt.mygot.info.InfoDetail
import com.by_jogehrt.mygot.internet.GoTAPI
import com.by_jogehrt.mygot.internet.RetrofitService
import com.by_jogehrt.mygot.ui.adapter.InfoAdapter
import com.by_jogehrt.mygot.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasterViewInfo : AppCompatActivity() { //Main Class
    private lateinit var binding: ActivityMasterViewInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterViewInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitService.getRetrofit().create(GoTAPI::class.java).getInfoNodes()

        call.enqueue(object: Callback<ArrayList<InfoDetail>>{
            override fun onResponse(call: Call<ArrayList<InfoDetail>>, response: Response<ArrayList<InfoDetail>>) {
                binding.pbConexion.visibility = View.INVISIBLE

                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                val infAdapter = InfoAdapter(response.body()!!){ inf ->
                    val bundle = bundleOf("id" to inf.id)
                    val intent = Intent(this@MasterViewInfo, MainList::class.java)

                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                binding.rvMenu.layoutManager = LinearLayoutManager(this@MasterViewInfo, RecyclerView.VERTICAL, false)
                binding.rvMenu.adapter = infAdapter
            }

            override fun onFailure(call: Call<ArrayList<InfoDetail>>, t: Throwable) {
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MasterViewInfo, "No hay conexión disponible", Toast.LENGTH_SHORT).show()
            }

        })

    }
}