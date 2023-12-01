package com.by_jogehrt.mygot.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.by_jogehrt.mygot.R
import com.by_jogehrt.mygot.databinding.DetailElementBinding
import com.by_jogehrt.mygot.info.InfoDetail
import com.by_jogehrt.mygot.internet.GoTAPI
import com.by_jogehrt.mygot.internet.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainList : AppCompatActivity() {
    private lateinit var binding: DetailElementBinding
    private lateinit var canons: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailElementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        canons = arrayOf("arryn","baratheon","greyjoy","lannister","martell","mormont","stark","targaryen","tully","tyrell")


        val bundle = intent.extras
        val id = bundle?.getString("id")

        val call = RetrofitService.getRetrofit().create(GoTAPI::class.java).getInfoDetail(id)

        call.enqueue(object: Callback<InfoDetail>{ override fun onResponse(call: Call<InfoDetail>, response: Response<InfoDetail>) {

            with(binding) {
                    pbConexion.visibility = View.INVISIBLE
                    Glide.with(this@MainList).load(response.body()?.image_url).into(ivProfile)
                    tvFullName.text = response.body()?.fl_name
                    tvFamily.text = response.body()?.family
                    tvTitle.text = response.body()?.title
                    when(response.body()?.family?.let { getTagResource(it) }){
                        0->ivHouse.setBackgroundResource(R.drawable.arryn)
                        1->ivHouse.setBackgroundResource(R.drawable.baratheon)
                        2->ivHouse.setBackgroundResource(R.drawable.greyjoy)
                        3->ivHouse.setBackgroundResource(R.drawable.lannister)
                        4->ivHouse.setBackgroundResource(R.drawable.martell)
                        5->ivHouse.setBackgroundResource(R.drawable.mormont)
                        6->ivHouse.setBackgroundResource(R.drawable.stark)
                        7->ivHouse.setBackgroundResource(R.drawable.targaryen)
                        8->ivHouse.setBackgroundResource(R.drawable.tully)
                        9->ivHouse.setBackgroundResource(R.drawable.tyrell)
                        10->ivHouse.isInvisible = true
                    }

                    //tvFamily.text = response.body()?.family?.let{getTagResource(it,true)}
                    //Glide.with(this@MainList).load("R.drawable.arryn").into(ivHouse)
                }

            }
            override fun onFailure(call: Call<InfoDetail>, t: Throwable) {
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MainList, getString(R.string.error_no_online), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getTagResource(name :String) : Int{
        var tagname : String = "unknown"
        var house : String = ""
        var chops : List<String> = name.split(" ")
        if(chops.size > 1)
            house = chops[1].lowercase(Locale.ROOT)
        else
            house = chops[0].lowercase(Locale.ROOT)

        var index : Int = 0
        for(compare in canons) {
            if (house.compareTo(compare) == 0) {
                return index
            }
            index++
        }
        return index
    }
}