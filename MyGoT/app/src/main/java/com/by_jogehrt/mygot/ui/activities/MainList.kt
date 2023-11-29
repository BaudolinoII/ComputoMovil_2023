package com.by_jogehrt.mygot.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.by_jogehrt.mygot.databinding.DetailElementBinding
import com.by_jogehrt.mygot.info.InfoDetail
import com.by_jogehrt.mygot.internet.GoTAPI
import com.by_jogehrt.mygot.internet.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainList : AppCompatActivity() {
    private lateinit var binding: DetailElementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailElementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val id = bundle?.getString("id")

        val call = RetrofitService.getRetrofit().create(GoTAPI::class.java).getInfoDetail(id)
        call.enqueue(object: Callback<InfoDetail>{ override fun onResponse(call: Call<InfoDetail>, response: Response<InfoDetail>) {
                with(binding) {
                    pbConexion.visibility = View.INVISIBLE
                    Glide.with(this@MainList).load(response.body()?.image).into(ivProfile)
                    tvFullName.text = response.body()?.f_name
                    tvFamily.text = response.body()?.family
                    tvTitle.text = response.body()?.title
                }

            }
            override fun onFailure(call: Call<InfoDetail>, t: Throwable) {
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MainList, "No hay conexión disponible", Toast.LENGTH_SHORT).show()
            }
        })
    }
}