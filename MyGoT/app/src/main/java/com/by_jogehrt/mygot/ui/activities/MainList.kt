package com.by_jogehrt.mygot.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                    Glide.with(this@MainList).load(response.body()?.image).into(ivProfile)
                    tvFullName.text = response.body()?.f_name
                    tvFamily.text = response.body()?.family
                    tvTitle.text = response.body()?.title
                    Glide.with(this@MainList).load(response.body()?.family?.let{getTagResource(it,false) }).into(ivHouse)
                }

            }
            override fun onFailure(call: Call<InfoDetail>, t: Throwable) {
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MainList, getString(R.string.error_no_online), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getTagResource(name :String, withText :Boolean) : String{
        var tagname : String = "unknown"
        var house : String = ""
        var chops : List<String> = name.split(" ")
        if(chops.size > 1)
            house = chops[1].lowercase(Locale.ROOT)
        else
            house = chops[0].lowercase(Locale.ROOT)
        for(compare in canons)
            if(house.compareTo(compare) == 0){
                if(withText) tagname = "text_" + house;
                else tagname = house
                return tagname
            }
        return tagname
    }
}