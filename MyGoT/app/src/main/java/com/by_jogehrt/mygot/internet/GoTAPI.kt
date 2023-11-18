package com.by_jogehrt.mygot.internet

import com.by_jogehrt.mygot.info.InfoDetail
import com.by_jogehrt.mygot.info.InfoNode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
interface GoTAPI{
    @GET("api/v2/Characters")
    fun getInfoNodes(@Url url: String?): Call<ArrayList<InfoNode>>
    @GET("api/v2/Characters/{id}")
    fun getInfoDetail(@Query("id") id: String?): Call<InfoDetail>
}