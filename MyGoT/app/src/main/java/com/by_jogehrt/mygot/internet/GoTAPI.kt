package com.by_jogehrt.mygot.internet

import com.by_jogehrt.mygot.info.InfoDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GoTAPI{
    @GET("https://thronesapi.com/api/v2/Characters")
    fun getInfoNodes(@Url url: String?): Call<ArrayList<InfoDetail>>
    @GET("https://thronesapi.com/api/v2/Characters/{id}")
    fun getInfoDetail(@Path("id") id: String?): Call<InfoDetail>
}