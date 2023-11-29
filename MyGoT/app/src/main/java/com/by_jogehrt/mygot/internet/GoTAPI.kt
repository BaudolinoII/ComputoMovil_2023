package com.by_jogehrt.mygot.internet

import com.by_jogehrt.mygot.info.InfoDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GoTAPI{
    @GET("api/v2/Characters")
    fun getInfoNodes(): Call<ArrayList<InfoDetail>>
    @GET("api/v2/Characters/{id}")
    fun getInfoDetail(@Path("id") id: String?): Call<InfoDetail>
}